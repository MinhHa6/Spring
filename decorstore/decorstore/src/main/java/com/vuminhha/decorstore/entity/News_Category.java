package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "News_Category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News_Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
    @Column(length = 500)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String notes;
    private Long idParent;
    @Column(columnDefinition = "TINYINT")
    private Integer type;

    @Column(length = 160)
    private String slug;

    @Column(length = 100)
    private String metaTitle;

    @Column(length = 300)
    private String metaKeyword;

    @Column(length = 300)
    private String metaDescription;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Long createdBy;

    private Long updatedBy;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Boolean isDelete = false;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Boolean isActive = true;
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
