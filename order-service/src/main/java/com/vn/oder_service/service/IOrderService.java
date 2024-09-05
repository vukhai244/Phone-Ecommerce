package com.vn.oder_service.service;

import com.vn.oder_service.dto.request.OrderRequestDTO;
import com.vn.oder_service.dto.request.OrderUpdateDTO;
import com.vn.oder_service.dto.response.OrderResponseDTO;

import org.springframework.data.domain.Page;

public interface IOrderService {

    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO getOrder(String id);

    Page<OrderResponseDTO> getOrders(int page, int size, String status);

    OrderResponseDTO updateOrder(String id, OrderUpdateDTO orderUpdate);

    OrderResponseDTO cancelOrder(String id);

    String getOrderStatus(String id);

    OrderResponseDTO updateOrderStatus(String id, String status);
}
