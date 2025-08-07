package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product_Config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product_Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idProduct;
    private Long idConfig;
    @Column(columnDefinition = "TEXT")
    private String value;
}
