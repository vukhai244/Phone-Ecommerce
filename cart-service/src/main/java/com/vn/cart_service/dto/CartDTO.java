package com.vn.cart_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CartDTO {
    private Long id;
    private AccountDTO userId;
    private List<CartItemDTO> items;
}
