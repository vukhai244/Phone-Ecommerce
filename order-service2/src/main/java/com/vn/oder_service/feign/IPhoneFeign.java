package com.vn.oder_service.feign;

import com.vn.oder_service.dto.PhoneDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "phone-service", url = "${phone.service.url}")
public interface IPhoneFeign {
    @GetMapping("/{id}")
    PhoneDTO getPhoneById(@PathVariable("id") Long id);

    @GetMapping()
    List<PhoneDTO> getAllPhones();

    @PutMapping("/phones/{id}/update-stock")
    void updatePhoneStock(@PathVariable Long id, @RequestParam int stockQuantity);
}
