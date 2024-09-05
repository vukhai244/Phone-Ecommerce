package com.vn.cart_service.feign;

import com.vn.cart_service.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service", url = "${account.service.url}")
public interface IAccountFeign {
    @GetMapping("/{id}")
    AccountDTO getUserById(@PathVariable("id") String id);
}
