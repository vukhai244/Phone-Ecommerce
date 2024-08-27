package com.vn.oder_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderItemRequestDTO {
    private Long phoneId;
    private int quantity;
}