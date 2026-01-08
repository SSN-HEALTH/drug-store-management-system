    package com.ssnhealthcare.drugstore.report.Dto;

    import lombok.AllArgsConstructor;
    import lombok.Data;

    import java.math.BigDecimal;
    @Data
    @AllArgsConstructor
    public class FinancialReportDto {
        private Long totalOrders;
        private Long totalItemsSold;
        private BigDecimal totalRevenue;

    }
