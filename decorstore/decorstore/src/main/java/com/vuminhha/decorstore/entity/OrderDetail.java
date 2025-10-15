package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {
    // bang chi tiet don hang

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết về Order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Liên kết về Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Giá sản phẩm tại thời điểm mua
    @Column(name = "price", precision = 12, scale = 2, nullable = false)
    private BigDecimal price;

    // Số lượng
    @Column(nullable = false)
    private Integer qty = 1;

    // Tổng tiền của dòng sản phẩm
    @Column(name = "total", precision = 12, scale = 2, nullable = false)
    private BigDecimal total;

    // Số lượng trả (nếu có chức năng trả hàng)
    @Column(name = "return_qty")
    private Integer returnQty = 0;

    @PrePersist
    @PreUpdate
    private void calculateTotal() {
        if (price != null && qty != null) {
            this.total = price.multiply(BigDecimal.valueOf(qty));
        }
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(Integer returnQty) {
        this.returnQty = returnQty;
    }
}
