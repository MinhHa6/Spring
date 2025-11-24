package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // Sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    // Liên kết giỏ
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    Cart cart;

    Integer quantity;

    @Column(precision = 10, scale = 2)
    BigDecimal price;

    // ham tinh tien cho tung san pham trong gio
    public BigDecimal getTotal() {
        if (product == null || product.getPrice() == null) {
            return BigDecimal.ZERO;
        }
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
