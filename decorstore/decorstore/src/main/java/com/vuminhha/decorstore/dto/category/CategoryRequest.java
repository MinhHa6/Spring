package com.vuminhha.decorstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;

    private String description;

    private Long parentId;  // Không truyền Entity xuống UI

    private String metaTitle;

    private String metaDescription;

    private Boolean isActive;
}
