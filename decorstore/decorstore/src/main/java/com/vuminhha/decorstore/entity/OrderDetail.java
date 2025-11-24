package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {
    // bang chi tiet don hang

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // Liên kết về Order
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    // Liên kết về Product
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    // Giá sản phẩm tại thời điểm mua
    @Column(name = "price", precision = 12, scale = 2, nullable = false)
    BigDecimal price;

    // Số lượng
    @Column(nullable = false)
    Integer qty = 1;

    // Tổng tiền của dòng sản phẩm
    @Column(name = "total", precision = 12, scale = 2, nullable = false)
    BigDecimal total;

    // Số lượng trả (nếu có chức năng trả hàng)
    @Column(name = "return_qty")
    Integer returnQty = 0;

    @PrePersist
    @PreUpdate
    private void calculateTotal() {
        if (price != null && qty != null) {
            this.total = price.multiply(BigDecimal.valueOf(qty));
        }
    }
}
