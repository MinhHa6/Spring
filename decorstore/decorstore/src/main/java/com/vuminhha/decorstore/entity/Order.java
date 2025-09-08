package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

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
    // Một Order có nhiều OrderDetail
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(String idOrders) {
        this.idOrders = idOrders;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNameReciver() {
        return nameReciver;
    }

    public void setNameReciver(String nameReciver) {
        this.nameReciver = nameReciver;
    }
}
