package com.vn.phone_ecommerce.dto.request;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhoneUpdateRequest {

    @NotBlank(message = "Code is required")
    @Size(max = 50, message = "Code must not exceed 50 characters")
    private String code;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Image URL is required")
    private String imageUrls;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private int price;

    @NotBlank(message = "Brand is required")
    @Size(max = 100, message = "Brand must not exceed 100 characters")
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(max = 100, message = "Model must not exceed 100 characters")
    private String model;

    @Min(value = 0, message = "Stock quantity must be greater than or equal to 0")
    private int stockQuantity;

    @NotNull(message = "Category IDs cannot be null")
    @Size(min = 1, message = "At least one category ID is required")
    private List<@NotBlank(message = "Category ID cannot be blank") String> categoryIds;

}
