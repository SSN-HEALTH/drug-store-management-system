package com.ssnhealthcare.drugstore.drug.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "drugs")
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drug_id")
    private Long drugId;

    @NotBlank
    @Column(name = "drug_name", nullable = false, length = 100)
    private String drugName;

    @Column(name = "composition", length = 200)
    private String composition;

    @Column(name = "dosage", length = 50)
    private String dosage;

    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;



}
