package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    // San pham
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 255, nullable = false)
    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(length = 500)
    String mainImage; // ảnh chính

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

    @Column(precision = 10, scale = 2, nullable = false)
    BigDecimal price;

    @Column(nullable = false)
    Integer quantity;

    @Column(length = 160, unique = true)
    String slug;

    // SEO (optional)
    @Column(length = 160)
    String metaTitle;

    @Column(length = 300)
    String metaDescription;

    Boolean isActive = true;
    @Column(nullable = false)
    Boolean isFeatured = false; // San pham noi bat
   // Cấu hình sản phẩm (VD: màu sắc, kích thước...)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductConfig> productConfigs = new ArrayList<>();

    LocalDateTime createdDate;
    LocalDateTime updatedDate;

    // ton kho
    Integer stock;
    // Ảnh phụ
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductImage> images = new ArrayList<>();

    // Chi tiết đơn hàng
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

}
