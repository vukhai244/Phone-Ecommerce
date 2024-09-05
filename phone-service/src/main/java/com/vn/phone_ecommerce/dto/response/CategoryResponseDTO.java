package com.vn.phone_ecommerce.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponseDTO {
    private String name;
    private String description;
    private String parentCategoryId;
    private List<String> subCategoryId;

}
