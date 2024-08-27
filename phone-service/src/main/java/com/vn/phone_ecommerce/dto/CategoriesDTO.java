package com.vn.phone_ecommerce.dto;

import com.vn.phone_ecommerce.entity.Categories;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CategoriesDTO {
    private String name;
    private String description;
    private Categories parentCategory;
    private List<Categories> subCategories;
}
