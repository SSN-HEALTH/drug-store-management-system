package com.ssnhealthcare.drugstore.sale.service;

import com.ssnhealthcare.drugstore.sale.Dto.DtoRequest.SaleCreateRequestDto;
import com.ssnhealthcare.drugstore.sale.Dto.DtoResponse.SaleResponseDto;


import java.time.LocalDate;
import java.util.List;

public interface SaleService {

 SaleResponseDto createSaleFromOrder(SaleCreateRequestDto dto);

     SaleResponseDto getSaleById(Long saleId);

     List<SaleResponseDto> getAllSales();

     List<SaleResponseDto> getSalesByUser(Long userId);

     List<SaleResponseDto> getSalesByDateRange(LocalDate fromDate, LocalDate toDate);

     SaleResponseDto cancelSale(Long saleId);
}
