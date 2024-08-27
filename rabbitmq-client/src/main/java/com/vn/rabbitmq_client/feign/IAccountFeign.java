package com.vn.rabbitmq_client.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "account-service", url = "${account.service.url}")
public interface IAccountFeign {
    @GetMapping("/emails")
    List<String> getAllEmails();
}
