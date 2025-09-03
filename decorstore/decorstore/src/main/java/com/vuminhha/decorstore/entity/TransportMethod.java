package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transport_method")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250, nullable = false)
    private String name;

    @Lob
    private String notes;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Column(nullable = false)
    private Boolean isDelete = false;

    @Column(nullable = false)
    private Boolean isActive = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
