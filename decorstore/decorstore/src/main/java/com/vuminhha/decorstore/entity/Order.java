package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    // bang don hang

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // Mã đơn hàng unique
    @Column(name = "order_code", length = 50, unique = true, nullable = false, updatable = false)
    String orderCode;

    @Column(name = "order_date", nullable = false, updatable = false)
    LocalDateTime orderDate;

    // Khách hàng đặt
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;


    // Thanh toán
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    PaymentMethod paymentMethod;

    // Vận chuyển
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id", nullable = false)
    TransportMethod transportMethod;

    @Column(name = "total_money", precision = 12, scale = 2, nullable = false)
    BigDecimal totalMoney;

    @Column(length = 1000)
    String notes;

    @Column(name = "receiver_name", length = 250, nullable = false)
    String receiverName;

    @Column(length = 500, nullable = false)
    String address;

    @Column(length = 150, nullable = false)
    String email;

    @Column(length = 50, nullable = false)
    String phone;

    @Column(name = "is_deleted", nullable = false)
    Boolean isDeleted = false;

    @Transient
    public String getStatus() {
        if (!Boolean.TRUE.equals(this.isActive)) {
            return "Đã hủy";
        }
        return "Hoạt động";
    }
    @Column(name = "is_active", nullable = false)
    Boolean isActive = true;

    @Column(name = "created_date", updatable = false)
    LocalDateTime createdDate;

    @Column(name = "updated_date")
    LocalDateTime updatedDate;

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
    List<OrderDetail> orderDetails;
}
