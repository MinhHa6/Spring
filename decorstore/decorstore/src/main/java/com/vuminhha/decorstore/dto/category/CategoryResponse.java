package com.vuminhha.decorstore.dto.category;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    Long id;
    String name;
    String slug;
    String description;
    String icon;

    Long parentId;
    String parentName;

    Boolean isActive;

     LocalDateTime createdDate;
     LocalDateTime updatedDate;
}
