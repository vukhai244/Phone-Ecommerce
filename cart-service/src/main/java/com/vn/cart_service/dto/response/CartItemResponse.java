package com.vn.cart_service.dto.response;

import com.vn.cart_service.dto.PhoneDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {
    private String id;
    private PhoneDTO phone;
    private int quantity;
}
