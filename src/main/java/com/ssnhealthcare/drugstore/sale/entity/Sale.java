package com.ssnhealthcare.drugstore.sale.entity;


import com.ssnhealthcare.drugstore.common.enums.PaymentMode;
import com.ssnhealthcare.drugstore.user.entity.User;
import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long saleId;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;


    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", nullable = false)
    private PaymentMode paymentMode;

    @ManyToOne
    @JoinColumn(name = "processed_by", nullable = false)
    private User processedBy;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SaleItem> items;


}
