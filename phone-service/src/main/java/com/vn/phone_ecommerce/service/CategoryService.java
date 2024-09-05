package com.vn.phone_ecommerce.service;

import com.vn.phone_ecommerce.dto.request.CategoryCreationRequest;
import com.vn.phone_ecommerce.dto.request.CategoryUpdateRequest;
import com.vn.phone_ecommerce.dto.response.CategoryResponseDTO;
import com.vn.phone_ecommerce.dto.response.PhoneResponseDTO;
import com.vn.phone_ecommerce.entity.Category;
import com.vn.phone_ecommerce.entity.Phone;
import com.vn.phone_ecommerce.entity.PhoneCategory;
import com.vn.phone_ecommerce.exception.CategoryNotFoundException;
import com.vn.phone_ecommerce.exception.DatabaseException;
import com.vn.phone_ecommerce.repository.ICategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PhoneResponseDTO> getPhonesByCategoryId(String categoryId) {
        try {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));

            List<Phone> phones = category.getPhones().stream()
                    .map(PhoneCategory::getPhone)
                    .collect(Collectors.toList());

            return phones.stream()
                    .map(phone -> modelMapper.map(phone, PhoneResponseDTO.class))
                    .collect(Collectors.toList());
        } catch (CategoryNotFoundException e) {
            throw e;

        } catch (Exception e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }

    @Override
    public List<CategoryResponseDTO> getAllCategory() {
        try {
            List<Category> categories = categoryRepository.findAll();

            return categories.stream()
                    .map(category -> {
                        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();

                        categoryResponseDTO.setName(category.getName());
                        categoryResponseDTO.setDescription(category.getDescription());

                        if (category.getParentCategory() != null) {
                            categoryResponseDTO.setParentCategoryId(category.getParentCategory().getId());
                        }

                        List<String> subCategoryIds = category.getSubCategories().stream()
                                .map(Category::getId)
                                .collect(Collectors.toList());
                        categoryResponseDTO.setSubCategoryId(subCategoryIds);

                        return categoryResponseDTO;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }

    @Override
    public void createCategory(CategoryCreationRequest categoryCreationRequest) {
        try {
            Category category = new Category();
            category.setName(categoryCreationRequest.getName());
            category.setDescription(categoryCreationRequest.getDescription());

            if (categoryCreationRequest.getParentId() != null) {
                Category parentCategory = categoryRepository.findById(categoryCreationRequest.getParentId())
                        .orElseThrow(() -> new CategoryNotFoundException(
                                "Parent category not found with ID: " + categoryCreationRequest.getParentId()));
                category.setParentCategory(parentCategory);
            }

            categoryRepository.save(category);
        } catch (CategoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }

    @Override
    public void deleteCategory(String id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));

            categoryRepository.delete(category);

        } catch (CategoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }

    @Override
    public void updateCategory(String id, CategoryUpdateRequest categoryUpdateRequest) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));

            category.setName(categoryUpdateRequest.getName());
            category.setDescription(categoryUpdateRequest.getDescription());

            if (categoryUpdateRequest.getParentId() != null) {
                Category parentCategory = categoryRepository.findById(categoryUpdateRequest.getParentId())
                        .orElseThrow(() -> new CategoryNotFoundException(
                                "Parent category not found with ID: " + categoryUpdateRequest.getParentId()));
                category.setParentCategory(parentCategory);
            } else {
                category.setParentCategory(null);
            }

            categoryRepository.save(category);

        } catch (CategoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }
}
