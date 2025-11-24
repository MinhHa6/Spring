package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(
        name = "product_config",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "config_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;
    // Giá trị cấu hình (VD: "Đỏ", "XL", "Gỗ sồi")
    @Column(length = 500, nullable = false)
    private String value;

    // Tham chiếu về Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
     Product product;

    // Tham chiếu về Configurations (VD: Màu sắc, Kích thước, Chất liệu)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "config_id", nullable = false)
     Configuration configurations;

}

