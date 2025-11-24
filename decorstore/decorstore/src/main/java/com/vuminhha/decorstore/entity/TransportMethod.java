package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "transport_methods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransportMethod {
    // phuong  thuc van chuyen
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
