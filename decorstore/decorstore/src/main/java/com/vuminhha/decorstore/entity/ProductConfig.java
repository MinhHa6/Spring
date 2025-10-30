package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_config",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "config_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductConfig {
    // Gia tri cu the theo tung product

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Giá trị cấu hình (VD: "Đỏ", "XL", "Gỗ sồi")
    @Column(length = 500, nullable = false)
    private String value;

    // Tham chiếu về Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Tham chiếu về Configurations (VD: Màu sắc, Kích thước, Chất liệu)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "config_id", nullable = false)
    private Configuration configurations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Configuration getConfigurations() {
        return configurations;
    }

    public void setConfigurations(Configuration configurations) {
        this.configurations = configurations;
    }
}

