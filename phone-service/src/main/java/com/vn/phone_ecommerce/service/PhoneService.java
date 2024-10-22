package com.vn.phone_ecommerce.service;

import com.vn.phone_ecommerce.dto.request.PhoneCreationRequest;
import com.vn.phone_ecommerce.dto.request.PhoneUpdateRequest;
import com.vn.phone_ecommerce.dto.response.PhoneResponseDTO;
import com.vn.phone_ecommerce.entity.Category;
import com.vn.phone_ecommerce.entity.Phone;
import com.vn.phone_ecommerce.entity.PhoneCategory;
import com.vn.phone_ecommerce.entity.PhoneCategoryId;
import com.vn.phone_ecommerce.exception.CategoryException;
import com.vn.phone_ecommerce.exception.DatabaseException;
import com.vn.phone_ecommerce.exception.ErrorCode;
import com.vn.phone_ecommerce.exception.PhoneException;
import com.vn.phone_ecommerce.repository.ICategoryRepository;
import com.vn.phone_ecommerce.repository.IPhoneCategoryRepository;
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

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IPhoneCategoryRepository phoneCategoryRepository;

    private IPhoneRepository phoneRepository;

    public PhoneService(IPhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<PhoneResponseDTO> getAllPhone(Pageable pageable) {
        try {
            Page<Phone> phones = phoneRepository.findAll(pageable);

            List<PhoneResponseDTO> phoneResponseDTOs = phones.getContent()
                    .stream()
                    .map(phone -> {
                        PhoneResponseDTO phoneResponseDTO = modelMapper.map(phone, PhoneResponseDTO.class);

                        List<String> categoryIds = phone.getCategories().stream()
                                .map(phoneCategory -> phoneCategory.getCategory().getName())
                                .collect(Collectors.toList());

                        phoneResponseDTO.setCategoryIds(categoryIds);

                        return phoneResponseDTO;
                    })
                    .collect(Collectors.toList());

            return new PageImpl<>(phoneResponseDTOs, pageable, phones.getTotalElements());

        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }

    @Override
    public PhoneResponseDTO getPhoneById(String id) {
        try {
            Phone phone = phoneRepository.findById(id)
                    .orElseThrow(() -> new PhoneException(ErrorCode.PHONE_NOT_EXISTED));
            PhoneResponseDTO phoneDTO = modelMapper.map(phone, PhoneResponseDTO.class);

            List<String> categoryIds = phone.getCategories().stream()
                    .map(phoneCategory -> phoneCategory.getCategory().getName())
                    .collect(Collectors.toList());

            phoneDTO.setCategoryIds(categoryIds);

            return phoneDTO;

        } catch (PhoneException e) {
            throw e;

        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }

    @Override
    public List<PhoneResponseDTO> getAllPhoneByName(String name) {
        try {
            List<Phone> phones = phoneRepository.findAllByName(name);
            if (phones.isEmpty()) {
                throw new PhoneException(ErrorCode.PHONE_NOT_EXISTED);
            }
            return phones.stream()
                    .map(phone -> {
                        PhoneResponseDTO phoneResponseDTO = modelMapper.map(phone, PhoneResponseDTO.class);

                        List<String> categoryIds = phone.getCategories().stream()
                                .map(phoneCategory -> phoneCategory.getCategory().getName())
                                .collect(Collectors.toList());

                        phoneResponseDTO.setCategoryIds(categoryIds);

                        return phoneResponseDTO;
                    })
                    .collect(Collectors.toList());

        } catch (PhoneException e) {
            throw e;

        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }

    @Override
    public void addPhone(PhoneCreationRequest phoneCreationRequest) {
        try {
            phoneRepository.findByCode(phoneCreationRequest.getCode())
                    .ifPresent(existingPhone -> {
                        throw new PhoneException(ErrorCode.PHONE_EXISTED);
                    });

            Phone phone = modelMapper.map(phoneCreationRequest, Phone.class);
            Phone savedPhone = phoneRepository.save(phone);

            if (phoneCreationRequest.getCategoryIds() != null && !phoneCreationRequest.getCategoryIds().isEmpty()) {
                for (String categoryId : phoneCreationRequest.getCategoryIds()) {
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new CategoryException(ErrorCode.CATEGORY_NOT_EXISTED));

                    PhoneCategory phoneCategory = new PhoneCategory();
                    PhoneCategoryId phoneCategoryId = new PhoneCategoryId();
                    phoneCategoryId.setPhoneId(savedPhone.getId());
                    phoneCategoryId.setCategoryId(category.getId());
                    phoneCategory.setId(phoneCategoryId);

                    phoneCategory.setPhone(savedPhone);
                    phoneCategory.setCategory(category);

                    phoneCategoryRepository.save(phoneCategory);
                }
            }

        } catch (PhoneException e) {
            throw e;
        } catch (CategoryException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }

    @Override
    public void updatePhone(String id, PhoneUpdateRequest phoneUpdateRequest) {
        try {
            if (phoneRepository.existsById(id)) {
                Phone phone = modelMapper.map(phoneUpdateRequest, Phone.class);
                phoneRepository.save(phone);
            } else {
                throw new PhoneException(ErrorCode.CATEGORY_NOT_EXISTED);
            }
        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }

    @Override
    public void deletePhone(String id) {
        try {
            if (phoneRepository.existsById(id)) {
                phoneRepository.deleteById(id);
            } else {
                throw new PhoneException(ErrorCode.PHONE_NOT_EXISTED);
            }
        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }

    @Override
    public void updatePhoneStock(String id, int stockQuantity) {
        Optional<Phone> optionalPhone = phoneRepository.findById(id);
        if (optionalPhone.isPresent()) {
            Phone phone = optionalPhone.get();
            phone.setStockQuantity(stockQuantity);
            phoneRepository.save(phone);
        } else {
            throw new PhoneException(ErrorCode.PHONE_NOT_EXISTED);
        }
    }
}
