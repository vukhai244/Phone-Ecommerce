package com.vn.oder_service.service;

import com.vn.oder_service.dto.OrderRequestDTO;
import com.vn.oder_service.dto.OrderResponseDTO;
import com.vn.oder_service.dto.OrderUpdateDTO;
import org.springframework.data.domain.Page;

public interface IOrderService {

    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO getOrder(Long id);

    Page<OrderResponseDTO> getOrders(int page, int size, String status);

    OrderResponseDTO updateOrder(Long id, OrderUpdateDTO orderUpdate);

    OrderResponseDTO cancelOrder(Long id);

    String getOrderStatus(Long id);

    OrderResponseDTO updateOrderStatus(Long id, String status);
}
