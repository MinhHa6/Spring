package com.vuminhha.decorstore.mapper;

import com.vuminhha.decorstore.dto.request.CategoryRequest;
import com.vuminhha.decorstore.dto.response.CategoryResponse;
import com.vuminhha.decorstore.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .icon(category.getIcon())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .parentName(category.getParent() != null ? category.getParent().getName() : null)
                .isActive(category.getIsActive())
                .createdDate(category.getCreatedDate())
                .updatedDate(category.getUpdatedDate())
                .build();
    }

    public void updateEntity(Category entity, CategoryRequest dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setMetaTitle(dto.getMetaTitle());
        entity.setMetaDescription(dto.getMetaDescription());
        entity.setIsActive(dto.getIsActive());
    }
}
