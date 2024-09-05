package com.vn.oder_service.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

//OrderResponseDTO
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDTO {
    private String id;
    private String userId;
    private Date orderDate;
    private int totalAmount;
    private String status;
    private String shippingAddress;
    private List<OrderItemResponseDTO> orderItems;
}
