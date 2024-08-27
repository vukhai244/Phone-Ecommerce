package com.vn.account_service.controller;

import com.vn.account_service.entity.Account;
import com.vn.account_service.exception.AccountNotFoundException;
import com.vn.account_service.exception.UsernameAlreadyExistsException;
import com.vn.account_service.service.IAccountService;
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
    public List<Account> getAllAccounts() {
        return accountService.getAllAccount();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAccount(@RequestBody Account account) {
        try {
            accountService.registerAccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteAccount(@PathVariable String name) {
        try {
            accountService.deleteAccount(name);
            return ResponseEntity.noContent().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getUserById(@PathVariable Long id) {
        try {
            Account account = accountService.getUserById(id);
            return ResponseEntity.ok(account);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/emails")
    public ResponseEntity<List<String>> getAllEmails() {
        List<String> emails = accountService.getAllEmails();
        return ResponseEntity.ok(emails);
    }

}
