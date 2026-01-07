package com.ssnhealthcare.drugstore.sale.service.implementation;

import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import com.ssnhealthcare.drugstore.drug.entity.Drug;
import com.ssnhealthcare.drugstore.drug.repository.DrugRepository;
import com.ssnhealthcare.drugstore.exception.BusinessException;
import com.ssnhealthcare.drugstore.exception.ResourceNotFoundException;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import com.ssnhealthcare.drugstore.inventory.repository.InventoryRepository;
import com.ssnhealthcare.drugstore.order.entity.Order;
import com.ssnhealthcare.drugstore.order.repository.OrderRepository;
import com.ssnhealthcare.drugstore.sale.Dto.DtoResponse.SaleResponseDto;
import com.ssnhealthcare.drugstore.sale.entity.Sale;
import com.ssnhealthcare.drugstore.sale.entity.SaleItem;
import com.ssnhealthcare.drugstore.sale.repository.SaleRepository;
import com.ssnhealthcare.drugstore.sale.service.SaleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
@AllArgsConstructor
@Transactional
public class SaleServiceImpl implements SaleService
{
    private final SaleRepository saleRepository;
    private final OrderRepository orderRepository;
    private final DrugRepository drugRepository;
    private final InventoryRepository inventoryRepository;
    @Override
    public SaleResponseDto createSaleFromOrder(Long orderId)
    {


        // Fetch the order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() != OrderStatus.COMPLETED) {
            throw new BusinessException("Order must be completed before creating a sale");
        }

        // Create the sale entity
        Sale sale = new Sale();
        sale.setSaleDate(LocalDateTime.now());
        sale.setProcessedBy(order.getProcessedBy());
        sale.setStatus(OrderStatus.COMPLETED);
        sale.setTotalAmount(order.getTotalAmount()); // BigDecimal-safe

        // Map OrderItems to SaleItems
        List<SaleItem> saleItems = order.getItems().stream().map(orderItem -> {

            Drug drug = drugRepository.findById(orderItem.getDrug().getDrugId())
                    .orElseThrow(() -> new ResourceNotFoundException("Drug not found"));

            // Get inventory for this drug
            Inventory inventory = inventoryRepository.findByDrug_DrugId(drug.getDrugId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Inventory not found for drug: " + drug.getDrugName()));

            // Expiry validation
            if (inventory.getExpiryDate().isBefore(LocalDate.now())) {
                throw new BusinessException("Drug expired: " + drug.getDrugName());
            }

            // Stock validation
            if (inventory.getQuantity() < orderItem.getQuantity()) {
                throw new BusinessException("Insufficient stock for drug: " + drug.getDrugName());
            }

            // Deduct inventory
            inventory.setQuantity(inventory.getQuantity() - orderItem.getQuantity());
            inventoryRepository.save(inventory);

            // Create SaleItem
            SaleItem item = new SaleItem();
            item.setSale(sale);
            item.setDrug(drug);
            item.setQuantity(orderItem.getQuantity());
            item.setPrice(orderItem.getPrice()); // BigDecimal-safe

            return item;
        }).toList();

        sale.setItems(saleItems);

        // Save sale
        Sale savedSale = saleRepository.save(sale);

        // Convert to DTO
        return new SaleResponseDto(savedSale);
    }

    

    @Override
    public SaleResponseDto getSaleById(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
        return new SaleResponseDto(sale);

    }

    @Override
    public List<SaleResponseDto> getAllSales() {
          return saleRepository.findAll()
                .stream()
                .map(SaleResponseDto::new)
                .toList();
    }

    @Override
    public List<SaleResponseDto> getSalesByUser(Long userId) {
        return saleRepository
                .findByProcessedBy_UserId(userId, PageRequest.of(0, 20))
                .getContent()
                .stream()
                .map(SaleResponseDto::new)
                .toList();
    }

    @Override
    public List<SaleResponseDto> getSalesByDateRange(LocalDate fromDate, LocalDate toDate) {
        return saleRepository.findBySaleDateBetween(
                        fromDate.atStartOfDay(),
                        toDate.atTime(23, 59, 59),
                        PageRequest.of(0, 50))
                .getContent()
                .stream()
                .map(SaleResponseDto::new)
                .toList();
    }

    @Override
    public SaleResponseDto cancelSale(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        if (sale.getStatus() == OrderStatus.CANCELLED)
        {
            throw new BusinessException("Sale already cancelled");
        }

        sale.setStatus(OrderStatus.CANCELLED);

        return new SaleResponseDto(saleRepository.save(sale));
    }
    }
