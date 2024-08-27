package com.vn.load_balancer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LoadBalancerConfiguration {

    @LoadBalanced // Gắn annotation để kích hoạt cân bằng tải
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
