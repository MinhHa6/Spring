package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    // bang don hang

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mã đơn hàng unique
    @Column(name = "order_code", length = 50, unique = true, nullable = false, updatable = false)
    private String orderCode;

    @Column(name = "order_date", nullable = false, updatable = false)
    private LocalDateTime orderDate;

    // Khách hàng đặt
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Thanh toán
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentMethod paymentMethod;

    // Vận chuyển
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id", nullable = false)
    private TransportMethod transportMethod;

    @Column(name = "total_money", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalMoney;

    @Column(length = 1000)
    private String notes;

    @Column(name = "receiver_name", length = 250, nullable = false)
    private String receiverName;

    @Column(length = 500, nullable = false)
    private String address;

    @Column(length = 150, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String phone;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
        this.orderDate = LocalDateTime.now();
        if (this.orderCode == null) {
            this.orderCode = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

    // Một đơn hàng có nhiều chi tiết
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;
}
