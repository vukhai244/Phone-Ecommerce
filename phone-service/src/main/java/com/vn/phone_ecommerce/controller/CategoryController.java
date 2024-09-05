package com.vn.phone_ecommerce.controller;

import com.vn.phone_ecommerce.dto.request.CategoryCreationRequest;
import com.vn.phone_ecommerce.dto.request.CategoryUpdateRequest;
import com.vn.phone_ecommerce.dto.response.CategoryResponseDTO;
import com.vn.phone_ecommerce.dto.response.PhoneResponseDTO;
import com.vn.phone_ecommerce.service.CategoryService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public List<PhoneResponseDTO> getPhonesByCategoryId(@PathVariable(name = "id") String id) {
        return categoryService.getPhonesByCategoryId(id);
    }

    @GetMapping
    public List<CategoryResponseDTO> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryCreationRequest categoryCreationRequest) {
        categoryService.createCategory(categoryCreationRequest);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@Valid @PathVariable String id,
            @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        categoryService.updateCategory(id, categoryUpdateRequest);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

}
