package com.vn.account_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vn.account_service.dto.request.AuthenticationRequest;
import com.vn.account_service.exception.UsernameAlreadyExistsException;
import com.vn.account_service.repository.IAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private IAccountRepository accountRepository;

    @Autowired
    public AuthenticationService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean authenticate(AuthenticationRequest authenticationRequest) {
        var user = accountRepository.findByUserName(authenticationRequest.getUserName())
                .orElseThrow(() -> new UsernameAlreadyExistsException("Username already exists!"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
    }

}
