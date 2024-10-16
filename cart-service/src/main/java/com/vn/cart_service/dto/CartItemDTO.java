package com.vn.cart_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CartItemDTO {
    private String id;
    private PhoneDTO phone;
    private int quantity;
}
