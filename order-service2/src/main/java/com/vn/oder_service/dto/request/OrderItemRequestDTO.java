package com.vn.oder_service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderItemRequestDTO {
    private String phoneId;
    private int quantity;
}