package com.ssnhealthcare.drugstore.sale.controller;

import com.ssnhealthcare.drugstore.sale.Dto.DtoResponse.SaleResponseDto;
import com.ssnhealthcare.drugstore.sale.service.SaleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@AllArgsConstructor
public class SaleController
{

    private final SaleService saleService;

    //Create Sale from Order (POS Billing)
    @PostMapping("/order/{orderId}")
    public ResponseEntity<SaleResponseDto> createSaleFromOrder(@PathVariable Long orderId)
    {
        SaleResponseDto response = saleService.createSaleFromOrder(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Get Sale by ID (Invoice)
    @GetMapping("/{saleId}")
    public ResponseEntity<SaleResponseDto> getSaleById(@PathVariable Long saleId)
    {
        return ResponseEntity.ok(saleService.getSaleById(saleId));
    }

    //Get All Sales (Admin / Dashboard)
    @GetMapping
    public ResponseEntity<List<SaleResponseDto>> getAllSales()
    {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    //Sales by Processed User (Cashier Report)
    @GetMapping("/processed-by/{userId}")
    public ResponseEntity<List<SaleResponseDto>> getSalesByUser(@PathVariable Long userId)
    {
        return ResponseEntity.ok(saleService.getSalesByUser(userId));
    }

    //Sales by Date Range (Daily / Monthly Report)
    @GetMapping("/date-range")
    public ResponseEntity<List<SaleResponseDto>> getSalesByDateRange(@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate)
    {
        return ResponseEntity.ok(saleService.getSalesByDateRange(fromDate, toDate));
    }

    //Cancel / Refund Sale
    @PutMapping("/{saleId}/cancel")
    public ResponseEntity<SaleResponseDto> cancelSale(@PathVariable Long saleId)
    {
        return ResponseEntity.ok(saleService.cancelSale(saleId));
    }
}
