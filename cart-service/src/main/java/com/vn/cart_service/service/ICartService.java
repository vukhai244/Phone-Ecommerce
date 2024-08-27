package com.vn.cart_service.service;

import com.vn.cart_service.dto.CartDTO;

public interface ICartService {

    void addItemToCart(Long userId, Long phoneId, int quantity);

    void removeItemFromCart(Long userId, Long phoneId);

    CartDTO getCart(Long userId);

}
