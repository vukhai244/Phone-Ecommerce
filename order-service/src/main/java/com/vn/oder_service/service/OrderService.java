package com.vn.oder_service.service;

import com.vn.oder_service.dto.*;
import com.vn.oder_service.dto.request.OrderItemRequestDTO;
import com.vn.oder_service.dto.request.OrderRequestDTO;
import com.vn.oder_service.dto.request.OrderUpdateDTO;
import com.vn.oder_service.dto.response.OrderItemResponseDTO;
import com.vn.oder_service.dto.response.OrderResponseDTO;
import com.vn.oder_service.entity.Order;
import com.vn.oder_service.entity.OrderItem;
import com.vn.oder_service.feign.IAccountFeign;
import com.vn.oder_service.feign.IPhoneFeign;
import com.vn.oder_service.repository.IOrderRedisRepository;
import com.vn.oder_service.repository.IOrderRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IOrderRedisRepository orderRedisRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public OrderService(IOrderRepository orderRepository, IOrderRedisRepository orderRedisRepository,
            RedisTemplate<String, Object> redisTemplate) {
        this.orderRepository = orderRepository;
        this.orderRedisRepository = orderRedisRepository;
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    private IAccountFeign accountFeign;

    @Autowired
    private IPhoneFeign phoneFeign;

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {

        // 1. Kiểm tra xem user có tồn tại không
        AccountDTO account = accountFeign.getUserById(orderRequest.getUserId());
        if (account == null) {
            throw new RuntimeException("User not found");
        }

        // 2. Tạo một đơn hàng mới
        Order order = new Order();
        order.setUserId(account.getId());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setStatus("PENDING");

        int totalAmount = 0;

        // 3. Xử lý từng mặt hàng trong đơn hàng
        for (OrderItemRequestDTO itemDTO : orderRequest.getItems()) {
            // Kiểm tra xem điện thoại có tồn tại không
            PhoneDTO phone = phoneFeign.getPhoneById(itemDTO.getPhoneId());
            if (phone == null) {
                throw new RuntimeException("Phone not found");
            }

            // Lấy số lượng tồn kho từ PhoneDTO
            int stockQuantity = phone.getStockQuantity(); // Sử dụng kiểu dữ liệu int

            // Kiểm tra xem số lượng tồn kho có đủ không
            if (stockQuantity < itemDTO.getQuantity()) {
                throw new RuntimeException("Insufficient inventory for Phone: " + itemDTO.getPhoneId());
            }

            // Tạo một OrderItem mới
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPhoneId(phone.getId());
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(phone.getPrice());

            // Tính tổng số tiền
            totalAmount += phone.getPrice() * itemDTO.getQuantity();

            order.getOrderItems().add(orderItem);

            // Cập nhật hàng tồn kho trong Redis
            updateInventoryInRedis(phone.getId(), itemDTO.getQuantity());

            // Cập nhật tồn kho trên phone-service
            phoneFeign.updatePhoneStock(phone.getId(), stockQuantity - itemDTO.getQuantity());

        }

        // Đặt tổng số tiền cho đơn hàng
        order.setTotalAmount(totalAmount);

        // 4. Lưu đơn hàng vào database
        Order savedOrder = orderRepository.save(order);

        // 5. Lưu đơn hàng vào Redis
        com.vn.oder_service.entity.redis.Order orderRedis = new com.vn.oder_service.entity.redis.Order();
        orderRedis.setId(savedOrder.getId().toString()); // Redis thường dùng String cho ID
        orderRedis.setUserId(savedOrder.getUserId());
        orderRedis.setOrderDate(savedOrder.getOrderDate());
        orderRedis.setTotalAmount(savedOrder.getTotalAmount());
        orderRedis.setStatus(savedOrder.getStatus());
        orderRedis.setShippingAddress(savedOrder.getShippingAddress());

        orderRedisRepository.save(orderRedis);

        // 6. Chuyển đổi đơn hàng đã lưu thành DTO để trả về
        return convertToResponseDTO(savedOrder);

    }

    private void updateInventoryInRedis(String phoneId, int quantityOrdered) {
        // Lấy thông tin tồn kho từ Redis
        System.out.println("Order quantity ***************************" + quantityOrdered);

        String inventoryKey = "Phone:" + phoneId + ":inventory";

        // Lấy giá trị tồn kho từ Redis dưới dạng String
        String currentInventoryStr = (String) redisTemplate.opsForValue().get(inventoryKey);

        Integer currentInventory = null;
        if (currentInventoryStr != null) {
            try {
                currentInventory = Integer.parseInt(currentInventoryStr);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid inventory data for Phone: " + phoneId);
            }
        } else {
            // Nếu tồn kho không có trong Redis, lấy từ phone-service và lưu vào Redis
            PhoneDTO phone = phoneFeign.getPhoneById(phoneId);
            if (phone == null) {
                throw new RuntimeException("Phone not found");
            }
            currentInventory = phone.getStockQuantity();
            redisTemplate.opsForValue().set(inventoryKey, currentInventory.toString());
            // System.out.println("Stored inventory in Redis: " +
            // redisTemplate.opsForValue().get(inventoryKey));
        }

        // Kiểm tra xem số lượng tồn kho có đủ không
        if (currentInventory < quantityOrdered) {
            throw new RuntimeException("Insufficient inventory for Phone: " + phoneId);
        }
        Long updatedInventory = redisTemplate.opsForValue().increment(inventoryKey, -quantityOrdered);
        System.out.println("Quantity stock ***************************" + updatedInventory);
        // Kiểm tra nếu hàng tồn kho sau khi cập nhật nhỏ hơn 0
        if (updatedInventory == null || updatedInventory < 0) {
            // Hoàn tác việc giảm hàng tồn kho nếu không đủ
            System.out.println("Out of stock ***************************");
            redisTemplate.opsForValue().increment(inventoryKey, quantityOrdered);
            throw new RuntimeException("Insufficient inventory for Phone: " + phoneId);
        }
    }

    private OrderResponseDTO convertToResponseDTO(Order order) {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        // Đặt các thuộc tính cơ bản của đơn hàng
        responseDTO.setId(order.getId());
        responseDTO.setUserId(order.getUserId());
        responseDTO.setOrderDate(order.getOrderDate());
        responseDTO.setTotalAmount(order.getTotalAmount());
        responseDTO.setStatus(order.getStatus());
        responseDTO.setShippingAddress(order.getShippingAddress());

        // Chuyển đổi các mặt hàng trong đơn hàng thành DTO
        List<OrderItemResponseDTO> itemDTOs = order.getOrderItems().stream()
                .map(item -> {
                    OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
                    itemDTO.setId(item.getId());
                    itemDTO.setPhoneId(item.getPhoneId());
                    // Lấy thông tin chi tiết về điện thoại
                    PhoneDTO phone = phoneFeign.getPhoneById(item.getPhoneId());
                    // itemDTO.setPhoneName(phone != null ? phone.getName() : "Unknown");
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setPrice(item.getPrice());
                    return itemDTO;
                })
                .collect(Collectors.toList());

        responseDTO.setOrderItems(itemDTOs);

        return responseDTO;
    }

    public OrderResponseDTO getOrder(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToResponseDTO(order);
    }

    public Page<OrderResponseDTO> getOrders(int page, int size, String status) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders;

        if (status != null && !status.isEmpty()) {
            orders = orderRepository.findByStatus(status, pageable);
        } else {
            orders = orderRepository.findAll(pageable);
        }
        return orders.map(this::convertToResponseDTO);
    }

    @Transactional
    public OrderResponseDTO updateOrder(String id, OrderUpdateDTO orderUpdate) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setShippingAddress(orderUpdate.getShippingAddress());
        Order updatedOrder = orderRepository.save(order);
        return convertToResponseDTO(updatedOrder);
    }

    @Transactional
    public OrderResponseDTO cancelOrder(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("CANCELLED");
        Order cancelledOrder = orderRepository.save(order);
        return convertToResponseDTO(cancelledOrder);
    }

    public String getOrderStatus(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return order.getStatus();
    }

    @Transactional
    public OrderResponseDTO updateOrderStatus(String id, String newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);
        return convertToResponseDTO(updatedOrder);
    }

}
