package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 100, unique = true, nullable = false)
    String username;

    @Column(length = 255, nullable = false)
    String password;

    @Column(length = 150)
    String email;


    LocalDateTime createdDate;
    LocalDateTime updatedDate;

    Boolean isActive = true;

    // Liên kết Role (N-N)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles;
    //  Thêm liên kết tới Customer (One-to-One, optional)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Customer customer;


    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

    // Thêm các field mới cho chức năng reset password
    @Column(name = "reset_token")
    String resetToken;

    @Column(name = "reset_token_expiry")
    LocalDateTime resetTokenExpiry;

}
