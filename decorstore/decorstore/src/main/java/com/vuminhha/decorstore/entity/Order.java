package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, unique = true)
    private String idOrders;

    private LocalDateTime ordersDate;

    @ManyToOne
    @JoinColumn(name = "idCustomer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "idPayment")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "idTransport")
    private TransportMethod transportMethod;

    private BigDecimal totalMoney;

    @Lob
    private String notes;

    @Column(length = 250)
    private String nameReciver;

    @Column(length = 500)
    private String address;

    @Column(length = 150)
    private String email;

    @Column(length = 50)
    private String phone;

    @Column(nullable = false)
    private Boolean isDelete = false;

    @Column(nullable = false)
    private Boolean isActive = true;
}
