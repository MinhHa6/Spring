package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Category {
    // Danh muc san pham
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false, unique = true)
    private String name;

    @Column(length = 160, unique = true)
    private String slug;   // URL-friendly name

    @Column(length = 300)
    private String description; // thay cho notes

    @Column(length = 250)
    private String icon;

    // Self reference cho category cha (thay cho idParent)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(length = 160)
    private String metaTitle;

    @Column(length = 300)
    private String metaDescription;

    private Boolean isActive = true;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // ✅ QUAN TRỌNG: Không dùng CASCADE.ALL
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
