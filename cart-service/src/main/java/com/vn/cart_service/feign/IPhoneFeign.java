package com.vn.cart_service.feign;

import com.vn.cart_service.dto.PhoneDTO;
import com.vn.cart_service.dto.response.ApiResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "phone-service", url = "${phone.service.url}")
public interface IPhoneFeign {
    @GetMapping("/{id}")
    ApiResponse<PhoneDTO> getPhoneById(@PathVariable String id);

    @GetMapping()
    List<PhoneDTO> getAllPhones();
}
