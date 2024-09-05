package com.vn.oder_service.controller;

import com.vn.oder_service.dto.request.OrderRequestDTO;
import com.vn.oder_service.dto.request.OrderUpdateDTO;
import com.vn.oder_service.dto.request.StatusUpdateDTO;
import com.vn.oder_service.dto.response.OrderResponseDTO;
import com.vn.oder_service.service.IOrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        orderService.createOrder(orderRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable String id) {
        OrderResponseDTO order = orderService.getOrder(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status) {
        Page<OrderResponseDTO> orders = orderService.getOrders(page, size, status);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable String id,
            @RequestBody OrderUpdateDTO orderUpdate) {
        OrderResponseDTO updatedOrder = orderService.updateOrder(id, orderUpdate);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable String id) {
        OrderResponseDTO cancelledOrder = orderService.cancelOrder(id);
        return ResponseEntity.ok(cancelledOrder);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> getOrderStatus(@PathVariable String id) {
        String status = orderService.getOrderStatus(id);
        return ResponseEntity.ok(status);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable String id,
            @RequestBody StatusUpdateDTO statusUpdate) {
        OrderResponseDTO updatedOrder = orderService.updateOrderStatus(id, statusUpdate.getStatus());
        return ResponseEntity.ok(updatedOrder);
    }

}
