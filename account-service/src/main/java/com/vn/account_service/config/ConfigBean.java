package com.vn.account_service.config;

import java.util.HashSet;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vn.account_service.entity.Account;
import com.vn.account_service.enums.Role;
import com.vn.account_service.repository.IAccountRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ConfigBean {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ModelMapper init() {
        return new ModelMapper();
    }

    @Bean
    public ApplicationRunner ApplicationRunner(IAccountRepository accountRepository) {
        return args -> {
            if (accountRepository.findByUserName("ADMIN").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());

                Account account = Account.builder()
                        .fullName("admin")
                        .userName("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(roles)
                        .build();

                accountRepository.save(account);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
