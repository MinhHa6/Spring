package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "blog_post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 250)
    String title;

    @Column(unique = true, length = 250)
    String slug; // link SEO

    @Column(columnDefinition = "TEXT")
    String content;

    @Column(length = 500)
    String thumbnail;

    @ManyToOne
    @JoinColumn(name = "category_id")
    BlogCategory category;

    LocalDateTime createdDate;
    LocalDateTime updatedDate;

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

}

