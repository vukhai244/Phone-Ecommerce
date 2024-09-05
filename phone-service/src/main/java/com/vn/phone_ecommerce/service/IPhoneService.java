package com.vn.phone_ecommerce.service;

import com.vn.phone_ecommerce.dto.request.PhoneCreationRequest;
import com.vn.phone_ecommerce.dto.request.PhoneUpdateRequest;
import com.vn.phone_ecommerce.dto.response.PhoneResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPhoneService {
    Page<PhoneResponseDTO> getAllPhone(Pageable pageable);

    void addPhone(PhoneCreationRequest phoneCreationRequest);

    void updatePhone(String id, PhoneUpdateRequest phoneUpdateRequest);

    void deletePhone(String id);

    PhoneResponseDTO getPhoneById(String id);

    List<PhoneResponseDTO> getAllPhoneByName(String name);

    void updatePhoneStock(String id, int stockQuantity);
}
