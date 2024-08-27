package com.vn.phone_ecommerce.service;

import com.vn.phone_ecommerce.dto.PhoneDTO;
import com.vn.phone_ecommerce.entity.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPhoneService {
    Page<PhoneDTO> getAllPhone(Pageable pageable);

    void addPhone(Phone phone);

    void updatePhone(Phone phone);

    void deletePhone(Long id);

    Phone getPhoneById(Long id);

    List<Phone> getAllPhoneByName(String name);

    void updatePhoneStock(Long id, int stockQuantity);
}
