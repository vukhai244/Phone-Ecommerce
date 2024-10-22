package com.vn.cart_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddItemRequest {
    @NotBlank(message = "UserId is required")
    @Size(min = 36, max = 36, message = "UserId must be 36 characters (UUID)")
    private String userId;

    @NotBlank(message = "PhoneId is required")
    @Size(min = 36, max = 36, message = "PhoneId must be 36 characters (UUID)")
    private String phoneId;

    @NotBlank(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;

}
