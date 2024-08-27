package com.vn.oder_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OrderRequestDTO {
    private Long userId;
    private String shippingAddress;
    private List<OrderItemRequestDTO> items = new ArrayList<>();
}
