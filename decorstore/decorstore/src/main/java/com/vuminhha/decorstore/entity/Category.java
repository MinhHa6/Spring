package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Category {
    // Danh muc san pham
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    @Column(length = 255, nullable = false, unique = true)
     String name;

    @Column(length = 160, unique = true)
     String slug;   // URL-friendly name

    @Column(length = 300)
     String description; // thay cho notes

    @Column(length = 250)
     String icon;

    // Self reference cho category cha (thay cho idParent)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
     Category parent;

    @Column(length = 160)
     String metaTitle;

    @Column(length = 300)
     String metaDescription;

     Boolean isActive = true;

     LocalDateTime createdDate;
     LocalDateTime updatedDate;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @ToString.Exclude // Tránh lỗi StackOverflow
    @EqualsAndHashCode.Exclude    private List<Product> products = new ArrayList<>();

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
