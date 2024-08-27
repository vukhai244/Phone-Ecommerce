package com.vn.oder_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//OrderItemResponseDTO
@NoArgsConstructor
@Getter
@Setter
public class OrderItemResponseDTO {
    private Long id;
    private Long phoneId;
    private int quantity;
    private int price;
}
