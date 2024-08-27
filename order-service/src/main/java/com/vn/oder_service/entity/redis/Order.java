package com.vn.oder_service.entity.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@RedisHash("Order")
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    private String id;

    private Long userId;
    private Date orderDate;
    private int totalAmount;
    private String status;
    private String shippingAddress;
}