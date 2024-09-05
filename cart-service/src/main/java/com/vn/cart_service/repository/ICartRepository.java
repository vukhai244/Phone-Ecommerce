package com.vn.cart_service.repository;

import com.vn.cart_service.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUserId(String userId);
}
