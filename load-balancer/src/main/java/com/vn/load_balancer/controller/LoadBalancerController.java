package com.vn.load_balancer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.vn.load_balancer.dto.OrderRequestDTO;

@RestController
@RequestMapping("/consumer-order")
public class LoadBalancerController {
    private final RestTemplate restTemplate;

    public LoadBalancerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String consumeOrders() {
        String response = restTemplate.getForObject("http://order-service/api/v1/orders", String.class);
        return response;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {

        String response = restTemplate.postForObject("http://order-service/api/v1/orders", orderRequestDTO,
                String.class);
        return response;
    }
}
