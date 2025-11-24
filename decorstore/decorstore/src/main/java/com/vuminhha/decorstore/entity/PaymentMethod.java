package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Entity
@Table(name = "payment_methods")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 250, nullable = false, unique = true)
    String name;

    @Column(length = 500)
    String notes;

    @CreationTimestamp
    LocalDateTime createdDate;

    @UpdateTimestamp
    LocalDateTime updatedDate;

    @Column(nullable = false)
    Boolean isDelete = false;

    @Column(nullable = false)
    Boolean isActive = true;
}
