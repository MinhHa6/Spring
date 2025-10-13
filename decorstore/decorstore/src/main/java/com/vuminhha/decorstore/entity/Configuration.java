package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "configurations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Configuration {
    // cac thuoc tinh cau hinh san pham

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên cấu hình (VD: Màu sắc, Kích thước, Chất liệu)
    @Column(length = 255, nullable = false)
    private String name;

    // Ghi chú thêm
    @Column(columnDefinition = "TEXT")
    private String notes;

    // Loại cấu hình (tùy chọn: VD: "color", "size")
    @Column(length = 100)
    private String type;

    @Column(nullable = false)
    private Boolean isDelete = false;

    @Column(nullable = false)
    private Boolean isActive = true;

    // Liên kết với ProductConfig
    @OneToMany(mappedBy = "configurations", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductConfig> productConfigs;
}
