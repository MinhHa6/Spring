package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product_Config")

public class Product_Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idProduct;
    private Long idConfig;
    @Column(columnDefinition = "TEXT")
    private String value;
    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Product product;
    // map voi bang configurations
    @ManyToOne
    @JoinColumn(name = "idConfig")
    private Configurations configurations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(Long idConfig) {
        this.idConfig = idConfig;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Configurations getConfigurations() {
        return configurations;
    }

    public void setConfigurations(Configurations configurations) {
        this.configurations = configurations;
    }
}
