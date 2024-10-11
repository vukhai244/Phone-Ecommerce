package com.vn.account_service.service;

import com.vn.account_service.dto.request.UserCreationRequest;
import com.vn.account_service.dto.request.UserUpdateRequest;
import com.vn.account_service.dto.response.UserResponse;
import com.vn.account_service.entity.Account;
import com.vn.account_service.enums.Role;
import com.vn.account_service.exception.AccountException;
import com.vn.account_service.exception.DatabaseException;
import com.vn.account_service.exception.EmailException;
import com.vn.account_service.exception.ErrorCode;
import com.vn.account_service.exception.UsernameException;
import com.vn.account_service.repository.IAccountRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {

    private IAccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Page<Account> getAllAccount(Pageable pageable) {
        try {
            return accountRepository.findAll(pageable);

        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }

    @Override
    public void createAccount(UserCreationRequest userCreationRequest) {
        try {
            accountRepository.findByUserName(userCreationRequest.getUserName())
                    .ifPresent(existingAccount -> {
                        throw new UsernameException(
                                ErrorCode.USER_EXISTED);
                    });

            accountRepository.findByEmail(userCreationRequest.getEmail())
                    .ifPresent(existingAccount -> {
                        throw new EmailException(
                                ErrorCode.EMAIL_EXISTED);
                    });

            Account account = modelMapper.map(userCreationRequest, Account.class);

            account.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));

            HashSet<String> role = new HashSet<>();
            role.add(Role.USER.name());
            account.setRole(role);

            accountRepository.save(account);

        } catch (UsernameException | EmailException e) {
            throw e;

        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }

    @Override
    public void deleteAccount(String userId) {
        try {

            Account account = accountRepository.findById(userId)
                    .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));

            accountRepository.delete(account);

        } catch (AccountException e) {
            throw e;

        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }

    @Override
    public UserResponse getUserById(String id) {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));
            return modelMapper.map(account, UserResponse.class);

        } catch (RuntimeException e) {
            throw e;

        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
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
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }

    @Override
    public Account updateAccount(String userId, UserUpdateRequest userUpdateRequest) {
        try {
            Account account = accountRepository.findById(userId)
                    .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));
            modelMapper.map(userUpdateRequest, account);

            if (userUpdateRequest.getPassword() != null && !userUpdateRequest.getPassword().isEmpty()) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                account.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
            }

            return accountRepository.save(account);

        } catch (AccountException e) {
            throw e;

        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }
}
