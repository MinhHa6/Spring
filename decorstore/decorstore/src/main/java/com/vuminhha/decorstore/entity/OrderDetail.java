package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orders_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idOrd")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Product product;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private Integer qty;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    private Integer returnQty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
