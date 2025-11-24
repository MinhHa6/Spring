package com.vuminhha.decorstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "configurations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // Tên cấu hình (VD: Màu sắc, Kích thước, Chất liệu)
    @Column(length = 255, nullable = false)
    String name;

    // Ghi chú thêm
    @Column(columnDefinition = "TEXT")
    String notes;

    // Loại cấu hình (tùy chọn: VD: "color", "size")
    @Column(length = 100)
    String type;

    @Column(nullable = false)
    Boolean isDelete = false;

    @Column(nullable = false)
    Boolean isActive = true;

    // Liên kết với ProductConfig
    @OneToMany(mappedBy = "configurations", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductConfig> productConfigs;
}
