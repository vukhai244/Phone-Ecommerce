package com.vn.account_service.repository;

import com.vn.account_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByUserName(String userName);

    Optional<Account> findByEmail(String email);
}
