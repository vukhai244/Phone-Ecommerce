package com.vn.phone_ecommerce.service;

import com.vn.phone_ecommerce.dto.CategoriesDTO;
import com.vn.phone_ecommerce.dto.PhoneDTO;

import java.util.List;

public interface ICategoriesService {
    List<PhoneDTO> getPhonesByCategoryId(int id);

    List<CategoriesDTO> getAllCategories();
}
