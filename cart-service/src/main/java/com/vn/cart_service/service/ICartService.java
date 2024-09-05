package com.vn.cart_service.service;

import com.vn.cart_service.dto.CartDTO;

public interface ICartService {

    void addItemToCart(String userId, String phoneId, int quantity);

    void removeItemFromCart(String userId, String phoneId);

    CartDTO getCart(String userId);

}
