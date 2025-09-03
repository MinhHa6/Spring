package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product_Image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product_Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 250)
    private String name;
    @Column(length = 250)
    private String urlImg;


    // tham chieu nguoc ve product
    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Product product;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
