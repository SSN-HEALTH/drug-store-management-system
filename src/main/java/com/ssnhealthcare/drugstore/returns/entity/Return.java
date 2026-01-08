package com.ssnhealthcare.drugstore.returns.entity;

import com.ssnhealthcare.drugstore.common.enums.ReturnReason;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "returns")
@Data
public class Return {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_id")
    private Long returnId;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false)
    private ReturnReason reason;

    @Min(1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;
}
