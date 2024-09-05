package com.vn.phone_ecommerce.service;

import com.vn.phone_ecommerce.dto.request.CategoryCreationRequest;
import com.vn.phone_ecommerce.dto.request.CategoryUpdateRequest;
import com.vn.phone_ecommerce.dto.response.CategoryResponseDTO;
import com.vn.phone_ecommerce.dto.response.PhoneResponseDTO;

import java.util.List;

public interface ICategoryService {
    List<PhoneResponseDTO> getPhonesByCategoryId(String id);

    List<CategoryResponseDTO> getAllCategory();

    void createCategory(CategoryCreationRequest categoryCreationRequest);

    void deleteCategory(String id);

    void updateCategory(String id, CategoryUpdateRequest categoryUpdateRequest);
}
