package com.vn.oder_service.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//OrderItemResponseDTO
@NoArgsConstructor
@Getter
@Setter
public class OrderItemResponseDTO {
    private String id;
    private String phoneId;
    private int quantity;
    private int price;
}
