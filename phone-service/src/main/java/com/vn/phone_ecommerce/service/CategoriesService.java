package com.vn.phone_ecommerce.service;

import com.vn.phone_ecommerce.dto.CategoriesDTO;
import com.vn.phone_ecommerce.dto.PhoneDTO;
import com.vn.phone_ecommerce.entity.Categories;
import com.vn.phone_ecommerce.entity.Phone;
import com.vn.phone_ecommerce.repository.ICategoriesRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoriesService implements ICategoriesService{
    @Autowired
    private ICategoriesRepository categoriesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PhoneDTO> getPhonesByCategoryId(int id) {
        Categories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Set<Phone> phones = category.getPhones();
        return phones.stream()
                .map(phone -> modelMapper.map(phone, PhoneDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoriesDTO> getAllCategories() {
        List<Categories> categories = categoriesRepository.findAll();
        return modelMapper.map(categories, new TypeToken<List<CategoriesDTO>>(){}.getType());
    }
}
