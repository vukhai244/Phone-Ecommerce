package com.vn.account_service.service;

import com.vn.account_service.exception.EmailAlreadyExistsException;
import com.vn.account_service.dto.request.UserCreationRequest;
import com.vn.account_service.dto.request.UserUpdateRequest;
import com.vn.account_service.entity.Account;
import com.vn.account_service.exception.AccountNotFoundException;
import com.vn.account_service.exception.DatabaseException;
import com.vn.account_service.exception.UsernameAlreadyExistsException;
import com.vn.account_service.repository.IAccountRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {

    private IAccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Page<Account> getAllAccount(Pageable pageable) {
        try {
            return accountRepository.findAll(pageable);

        } catch (Exception e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }

    @Override
    public void createAccount(UserCreationRequest userCreationRequest) {
        try {
            accountRepository.findByUserName(userCreationRequest.getUserName())
                    .ifPresent(existingAccount -> {
                        throw new UsernameAlreadyExistsException(
                                "Username '" + userCreationRequest.getUserName() + "' already exists!");
                    });

            accountRepository.findByEmail(userCreationRequest.getEmail())
                    .ifPresent(existingAccount -> {
                        throw new EmailAlreadyExistsException(
                                "Email '" + userCreationRequest.getEmail() + "' already exists!");
                    });

            Account account = modelMapper.map(userCreationRequest, Account.class);

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            account.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));

            accountRepository.save(account);

        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
            throw e;

        } catch (Exception e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAccount(String userId) {
        try {

            Account account = accountRepository.findById(userId)
                    .orElseThrow(() -> new AccountNotFoundException("Account with id '" + userId + "' not found"));

            accountRepository.delete(account);

        } catch (AccountNotFoundException e) {
            throw e;

        } catch (Exception e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }

    @Override
    public Account getUserById(String id) {
        try {
            return accountRepository.findById(id)
                    .orElseThrow(() -> new AccountNotFoundException("Account with id " + id + " not found"));

        } catch (AccountNotFoundException e) {
            throw e;

        } catch (Exception e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }

    @Override
    public List<String> getAllEmails() {
        try {
            return accountRepository.findAll()
                    .stream()
                    .map(Account::getEmail)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseException("Database error: Unable to retrieve emails.");
        }
    }

    @Override
    public Account updateAccount(String userId, UserUpdateRequest userUpdateRequest) {
        try {
            Account account = getUserById(userId);
            modelMapper.map(userUpdateRequest, account);

            if (userUpdateRequest.getPassword() != null && !userUpdateRequest.getPassword().isEmpty()) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                account.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
            }

            return accountRepository.save(account);

        } catch (AccountNotFoundException e) {
            throw e;

        } catch (Exception e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }
}
