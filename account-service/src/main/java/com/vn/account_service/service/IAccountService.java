package com.vn.account_service.service;

import com.vn.account_service.entity.Account;

import java.util.List;

public interface IAccountService {
    List<Account> getAllAccount();

    void registerAccount(Account account);

    void deleteAccount(String name);

    Account getUserById(Long id);

    List<String> getAllEmails();
}
