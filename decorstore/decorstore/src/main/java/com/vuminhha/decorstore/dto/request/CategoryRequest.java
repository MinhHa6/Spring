package com.vuminhha.decorstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(max = 255,message = "Ten toi da 255 ky tu ")
    private String name;

    private String description;

    private Long parentId;  // Không truyền Entity xuống UI

    private String metaTitle;

    private String metaDescription;

    private Boolean isActive;
}
