package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(length = 500)
    private  String name;

    @Column(columnDefinition = "Text")
    private String notes;

    @Column(length = 250)
    private String icon;

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

}
