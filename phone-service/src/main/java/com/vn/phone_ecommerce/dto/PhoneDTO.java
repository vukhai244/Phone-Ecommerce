package com.vn.phone_ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PhoneDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private int price;
    private String brand;
    private String model;
    private int stockQuantity;
    private Set<CategoriesDTO> categories;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class CategoriesDTO {
        private String name;

    }
}
