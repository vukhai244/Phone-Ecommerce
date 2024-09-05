package com.vn.account_service.controller;

import com.vn.account_service.dto.request.UserCreationRequest;
import com.vn.account_service.dto.request.UserUpdateRequest;
import com.vn.account_service.entity.Account;
import com.vn.account_service.service.IAccountService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Page<Account> getAllAccout(Pageable pageable) {
        return accountService.getAllAccount(pageable);
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@Valid @RequestBody UserCreationRequest userCreationRequest) {
        accountService.createAccount(userCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateAccount(@Valid @PathVariable String userId,
            @RequestBody UserUpdateRequest userUpdateRequest) {
        accountService.updateAccount(userId, userUpdateRequest);
        return ResponseEntity.ok("Update");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteAccount(@PathVariable String userId) {
        accountService.deleteAccount(userId);
        return ResponseEntity.ok("Account deleted successfully");
    }

    @GetMapping("/{id}")
    public Account getUserById(@PathVariable String id) {
        return accountService.getUserById(id);
    }

    @GetMapping("/emails")
    public ResponseEntity<List<String>> getAllEmails() {
        List<String> emails = accountService.getAllEmails();
        return ResponseEntity.ok(emails);
    }

}
