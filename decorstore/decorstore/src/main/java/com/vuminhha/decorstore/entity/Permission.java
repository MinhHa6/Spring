package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String name; // ex: "PRODUCT_READ", "PRODUCT_CREATE", "ORDER_MANAGE"

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
