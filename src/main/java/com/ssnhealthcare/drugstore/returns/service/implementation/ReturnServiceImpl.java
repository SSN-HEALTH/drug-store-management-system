package com.ssnhealthcare.drugstore.returns.service.implementation;

import com.ssnhealthcare.drugstore.common.mapper.ReturnMapper;
import com.ssnhealthcare.drugstore.exception.BusinessException;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import com.ssnhealthcare.drugstore.inventory.repository.InventoryRepository;
import com.ssnhealthcare.drugstore.returns.dto.requestdto.CreateReturnRequestDTO;
import com.ssnhealthcare.drugstore.returns.dto.requestdto.ReturnPageRequestDTO;
import com.ssnhealthcare.drugstore.returns.dto.responsedto.ReturnResponseDTO;
import com.ssnhealthcare.drugstore.returns.entity.Return;
import com.ssnhealthcare.drugstore.returns.repository.ReturnRepository;
import com.ssnhealthcare.drugstore.returns.service.ReturnService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReturnServiceImpl implements ReturnService {

    private final InventoryRepository inventoryRepository;
    private final ReturnRepository returnRepository;
    private final ReturnMapper returnMapper;

    @Override
    @Transactional
    public ReturnResponseDTO createReturn(CreateReturnRequestDTO dto) {

        Inventory inventory = inventoryRepository.findById(dto.getInventoryId())
                .orElseThrow(() ->
                        new BusinessException("Inventory not found with id: " + dto.getInventoryId()));

        int availableQty = inventory.getQuantity();
        if (availableQty < dto.getQuantity()) {
            throw new BusinessException("Return quantity exceeds available stock");
        }

        inventory.setQuantity(availableQty - dto.getQuantity());
        inventoryRepository.save(inventory);

        Return ret = new Return();
        ret.setInventory(inventory);
        ret.setReason(dto.getReason());
        ret.setQuantity(dto.getQuantity());
        ret.setReturnDate(LocalDate.now());

        return returnMapper.toResponse(
                returnRepository.save(ret)
        );
    }

    @Override
    public Page<ReturnResponseDTO> getAllReturns(ReturnPageRequestDTO dto) {

        Pageable pageable = PageRequest.of(
                dto.getPage(),
                dto.getSize(),
                Sort.by("returnDate").descending()
        );

        return returnRepository.findAll(pageable)
                .map(returnMapper::toResponse);
    }

    @Override
    public ReturnResponseDTO getReturnById(Long id) {

        Return ret = returnRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Return not found with id: " + id));

        return returnMapper.toResponse(ret);
    }
}


