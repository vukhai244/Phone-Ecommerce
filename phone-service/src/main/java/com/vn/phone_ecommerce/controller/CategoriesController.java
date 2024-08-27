package com.vn.phone_ecommerce.controller;

import com.vn.phone_ecommerce.dto.CategoriesDTO;
import com.vn.phone_ecommerce.dto.PhoneDTO;
import com.vn.phone_ecommerce.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/{id}/phones")
    public List<PhoneDTO> getPhonesByCategoryId(@PathVariable(name = "id") int id) {
        return categoriesService.getPhonesByCategoryId(id);
    }

    @GetMapping
    public List<CategoriesDTO> getAllCategories() {
        return categoriesService.getAllCategories();
    }

}
