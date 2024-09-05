package com.vn.phone_ecommerce.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PhoneResponseDTO {
    private String name;
    private String description;
    private int price;
    private String brand;
    private String model;
    private int stockQuantity;
    private List<String> categoryIds;
}
