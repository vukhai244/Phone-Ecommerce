package com.vn.cart_service.feign;

import com.vn.cart_service.config.FeignClientConfig;
import com.vn.cart_service.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@FeignClient(name = "account-service", url = "${account.service.url}", configuration = FeignClientConfig.class)
public interface IAccountFeign {
    @GetMapping("/{id}")
    Optional<AccountDTO> getUserById(@PathVariable("id") String id);
}
