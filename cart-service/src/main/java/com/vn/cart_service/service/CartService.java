package com.vn.cart_service.service;

import com.vn.cart_service.dto.AccountDTO;
import com.vn.cart_service.dto.PhoneDTO;
import com.vn.cart_service.dto.request.AddItemRequest;
import com.vn.cart_service.dto.request.DeleteItemRequest;
import com.vn.cart_service.dto.response.ApiResponse;
import com.vn.cart_service.dto.response.CartItemResponse;
import com.vn.cart_service.dto.response.CartResponse;
import com.vn.cart_service.entity.Cart;
import com.vn.cart_service.entity.CartItem;
import com.vn.cart_service.exception.UserException;
import com.vn.cart_service.exception.CartException;
import com.vn.cart_service.exception.DatabaseException;
import com.vn.cart_service.exception.ErrorCode;
import com.vn.cart_service.exception.PhoneException;
import com.vn.cart_service.feign.IAccountFeign;
import com.vn.cart_service.feign.IPhoneFeign;
import com.vn.cart_service.repository.ICartRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class CartService implements ICartService {

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private IPhoneFeign phoneFeign;

    @Autowired
    private IAccountFeign accountFeign;

    @Override
    public void addItemToCart(AddItemRequest addItemRequest) {
        try {
            Cart cart = cartRepository.findByUserId(addItemRequest.getUserId()).orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUserId(addItemRequest.getUserId());
                return cartRepository.save(newCart);
            });

            CartItem cartItem = cart.getItems().stream()
                    .filter(item -> item.getPhoneId().equals(addItemRequest.getPhoneId()))
                    .findFirst()
                    .orElseGet(() -> {
                        CartItem newItem = new CartItem();
                        newItem.setPhoneId(addItemRequest.getPhoneId());
                        newItem.setCartId(cart);
                        cart.getItems().add(newItem);
                        return newItem;
                    });

            cartItem.setQuantity(cartItem.getQuantity() + addItemRequest.getQuantity());
            cartRepository.save(cart);

        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }

    @Override
    public void removeItemFromCart(DeleteItemRequest deleteItemRequest) {
        try {
            Cart cart = cartRepository.findByUserId(deleteItemRequest.getUserId())
                    .orElseThrow(() -> new CartException(ErrorCode.CART_NOT_EXISTED));
            if (cart != null) {
                cart.getItems().removeIf(item -> item.getPhoneId().equals(deleteItemRequest.getPhoneId()));
                cartRepository.save(cart);
            }
        } catch (CartException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }

    @Override
    public CartResponse getCart(String userId) {
        try {
            Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new CartException(ErrorCode.CART_NOT_EXISTED));

            if (cart == null) {
                return null;
            }

            AccountDTO user = accountFeign.getUserById(userId)
                    .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_EXISTED));
            ;

            CartResponse cartResponse = CartResponse.builder()
                    .items(cart.getItems().stream()
                            .map(item -> {
                                ApiResponse<PhoneDTO> phoneResponse = phoneFeign.getPhoneById(item.getPhoneId());
                                log.info("Fetching phone details for phoneId: {}", item.getPhoneId());
                                if (phoneResponse.getCode() != 1000 || phoneResponse.getResult() == null) {
                                    throw new PhoneException(ErrorCode.PHONE_NOT_EXISTED);
                                }
                                PhoneDTO phone = phoneResponse.getResult();
                                log.info("Phone details: {}", phone);
                                CartItemResponse cartItemResponse = CartItemResponse.builder()
                                        .id(item.getId())
                                        .phone(phone)
                                        .quantity(item.getQuantity())
                                        .build();

                                return cartItemResponse;
                            })
                            .collect(Collectors.toList()))
                    .build();

            return cartResponse;

        } catch (CartException | UserException | PhoneException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException(ErrorCode.DATABASE_EXCEPTION);
        }
    }
}
