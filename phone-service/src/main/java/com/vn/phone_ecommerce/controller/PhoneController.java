package com.vn.phone_ecommerce.controller;

import com.vn.phone_ecommerce.dto.PhoneDTO;
import com.vn.phone_ecommerce.entity.Phone;
import com.vn.phone_ecommerce.service.IPhoneService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/phone")
public class PhoneController {
    private IPhoneService phoneService;

    public PhoneController(IPhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public Page<PhoneDTO> getAllPhone(Pageable pageable) {
        return phoneService.getAllPhone(pageable);
    }

    @GetMapping("/{id}")
    public PhoneDTO getPhoneById(@PathVariable(name = "id") Long id) {
        Phone phone = phoneService.getPhoneById(id);
        return modelMapper.map(phone, PhoneDTO.class);
    }

    @GetMapping("/search/{name}")
    public List<PhoneDTO> getAllPhoneByName(@PathVariable(name = "name") String name) {
        List<Phone> phones = phoneService.getAllPhoneByName(name);
        List<PhoneDTO> phoneDTOs = modelMapper.map(phones, new TypeToken<List<PhoneDTO>>() {
        }.getType());
        return phoneDTOs;
    }

    @PostMapping
    public ResponseEntity<String> addPhone(@RequestBody Phone phone) {
        phoneService.addPhone(phone);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> addPhone(@PathVariable Long id, @RequestBody Phone phone) {
        phone.setId(id);
        phoneService.updatePhone(phone);
        return new ResponseEntity<>("Update", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePhone(@PathVariable Long id) {
        phoneService.deletePhone(id);
        return new ResponseEntity<>("Delete", HttpStatus.OK);
    }

    @PutMapping("/phones/{id}/update-stock")
    public ResponseEntity<String> updatePhoneStock(@PathVariable Long id, @RequestParam int stockQuantity) {
        phoneService.updatePhoneStock(id, stockQuantity);
        return new ResponseEntity<>("Update", HttpStatus.OK);
    }
}
