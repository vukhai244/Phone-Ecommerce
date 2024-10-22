package com.vn.phone_ecommerce.controller;

import com.vn.phone_ecommerce.dto.request.PhoneCreationRequest;
import com.vn.phone_ecommerce.dto.request.PhoneUpdateRequest;
import com.vn.phone_ecommerce.dto.response.ApiResponse;
import com.vn.phone_ecommerce.dto.response.PhoneResponseDTO;
import com.vn.phone_ecommerce.service.IPhoneService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/phones")
public class PhoneController {

    private IPhoneService phoneService;

    public PhoneController(IPhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping
    public ApiResponse<Page<PhoneResponseDTO>> getAllPhone(Pageable pageable) {
        ApiResponse<Page<PhoneResponseDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(phoneService.getAllPhone(pageable));
        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<PhoneResponseDTO> getPhoneById(@PathVariable String id) {
        ApiResponse<PhoneResponseDTO> apiResponse = new ApiResponse<>();
        apiResponse.setResult(phoneService.getPhoneById(id));
        return apiResponse;

    }

    @GetMapping("/search/{name}")
    public ApiResponse<List<PhoneResponseDTO>> getAllPhoneByName(@PathVariable(name = "name") String name) {
        ApiResponse<List<PhoneResponseDTO>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(phoneService.getAllPhoneByName(name));
        return apiResponse;
    }

    @PostMapping
    public ResponseEntity<String> addPhone(@Valid @RequestBody PhoneCreationRequest phoneCreationRequest) {
        phoneService.addPhone(phoneCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Phone created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> addPhone(@Valid @PathVariable String id,
            @RequestBody PhoneUpdateRequest phoneUpdateRequest) {
        phoneService.updatePhone(id, phoneUpdateRequest);
        return ResponseEntity.ok("Phone update successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePhone(@PathVariable String id) {
        phoneService.deletePhone(id);
        return new ResponseEntity<>("Delete", HttpStatus.OK);
    }

    @PutMapping("/phones/{id}/update-stock")
    public ResponseEntity<String> updatePhoneStock(@PathVariable String id, @RequestParam int stockQuantity) {
        phoneService.updatePhoneStock(id, stockQuantity);
        return new ResponseEntity<>("Update", HttpStatus.OK);
    }
}
