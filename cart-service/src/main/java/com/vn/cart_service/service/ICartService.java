package com.vn.cart_service.service;

import com.vn.cart_service.dto.request.AddItemRequest;
import com.vn.cart_service.dto.request.DeleteItemRequest;
import com.vn.cart_service.dto.response.CartResponse;

public interface ICartService {

    void addItemToCart(AddItemRequest addItemRequest);

    void removeItemFromCart(DeleteItemRequest deleteItemRequest);

    CartResponse getCart(String userId);

}
