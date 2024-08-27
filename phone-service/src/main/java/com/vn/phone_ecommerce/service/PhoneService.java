package com.vn.phone_ecommerce.service;

import com.vn.phone_ecommerce.dto.PhoneDTO;
import com.vn.phone_ecommerce.entity.Phone;
import com.vn.phone_ecommerce.repository.IPhoneRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class PhoneService implements IPhoneService {
    private IPhoneRepository phoneRepository;

    public PhoneService(IPhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<PhoneDTO> getAllPhone(Pageable pageable) {
        try {
            Page<Phone> phones = phoneRepository.findAll(pageable);

            List<PhoneDTO> phoneDTOs = phones.getContent()
                    .stream()
                    .map(phone -> modelMapper.map(phone, PhoneDTO.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(phoneDTOs, pageable, phones.getTotalElements());

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all phones: " + e.getMessage(), e);
        }
    }

    @Override
    public void addPhone(Phone phone) {
        try {
            phoneRepository.save(phone);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add phone: " + e.getMessage(), e);
        }
    }

    @Override
    public void updatePhone(Phone phone) {
        try {
            if (phoneRepository.existsById(phone.getId())) {
                phoneRepository.save(phone);
            } else {
                throw new RuntimeException("Phone not found with ID: " + phone.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update phone: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletePhone(Long id) {
        try {
            if (phoneRepository.existsById(id)) {
                phoneRepository.deleteById(id);
            } else {
                throw new RuntimeException("Phone not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete phone: " + e.getMessage(), e);
        }
    }

    @Override
    public Phone getPhoneById(Long id) {
        return phoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phone not found with ID: " + id));
    }

    @Override
    public List<Phone> getAllPhoneByName(String name) {
        try {
            return phoneRepository.findAllByName(name);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve phones by name: " + e.getMessage(), e);
        }
    }

    @Override
    public void updatePhoneStock(Long id, int stockQuantity) {
        Optional<Phone> optionalPhone = phoneRepository.findById(id);
        if (optionalPhone.isPresent()) {
            Phone phone = optionalPhone.get();
            phone.setStockQuantity(stockQuantity);
            phoneRepository.save(phone);
        } else {
            throw new RuntimeException("Phone not found with ID: " + id);
        }
    }
}
