package com.vn.account_service.service;

import com.vn.account_service.entity.Account;
import com.vn.account_service.exception.AccountNotFoundException;
import com.vn.account_service.exception.UsernameAlreadyExistsException;
import com.vn.account_service.repository.IAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {

    private IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public void registerAccount(Account account) {
        if (accountRepository.findByName(account.getName()).isPresent()) {
            throw new UsernameAlreadyExistsException("This name already exists!");
        }
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(String name) {
        Optional<Account> accountOptional = accountRepository.findByName(name);
        if (accountOptional.isPresent()) {
            accountRepository.delete(accountOptional.get());
        } else {
            throw new AccountNotFoundException("Account with name " + name + " not found.");
        }
    }

    @Override
    public Account getUserById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + id + " not found."));
    }

    @Override
    public List<String> getAllEmails() {
        return accountRepository.findAll()
                .stream()
                .map(Account::getEmail)
                .collect(Collectors.toList());
    }
}
