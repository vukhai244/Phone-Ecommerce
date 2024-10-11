package com.vn.account_service.service;

import com.vn.account_service.dto.request.UserCreationRequest;
import com.vn.account_service.dto.request.UserUpdateRequest;
import com.vn.account_service.dto.response.UserResponse;
import com.vn.account_service.entity.Account;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAccountService {
    Page<Account> getAllAccount(Pageable pageable);

    void createAccount(UserCreationRequest userCreationRequest);

    void deleteAccount(String userId);

    UserResponse getUserById(String id);

    List<String> getAllEmails();

    Account updateAccount(String userId, UserUpdateRequest userUpdateRequest);
}
