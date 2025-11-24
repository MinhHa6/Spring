package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {// thong tin khach hang

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 250, nullable = false)
    String name;

    @Column(length = 500)
    String address;


    @Column(length = 50, unique = true)
    String phone;

    @Column(length = 250)
    String avatar;

    LocalDateTime createdDate;

    LocalDateTime updatedDate;

    Long createdBy;

    Long updatedBy;

    @Column(nullable = false)
    Boolean isDelete = false;

    @Column(nullable = false)
    Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
    // Quan hệ One-to-One với User (User quản lý login, Customer quản lý profile)
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    User user;
}
