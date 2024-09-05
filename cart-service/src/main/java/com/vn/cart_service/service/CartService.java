package com.vn.cart_service.service;

import com.vn.cart_service.dto.AccountDTO;
import com.vn.cart_service.dto.CartDTO;
import com.vn.cart_service.dto.CartItemDTO;
import com.vn.cart_service.dto.PhoneDTO;
import com.vn.cart_service.entity.Cart;
import com.vn.cart_service.entity.CartItem;
import com.vn.cart_service.exception.AccountNotFoundException;
import com.vn.cart_service.exception.CartNotFoundException;
import com.vn.cart_service.exception.DatabaseException;
import com.vn.cart_service.exception.PhoneNotFoundException;
import com.vn.cart_service.feign.IAccountFeign;
import com.vn.cart_service.feign.IPhoneFeign;
import com.vn.cart_service.repository.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private IPhoneFeign phoneFeign;

    @Autowired
    private IAccountFeign accountFeign;

    @Override
    public void addItemToCart(String userId, String phoneId, int quantity) {
        try {
            Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUserId(userId);
                return cartRepository.save(newCart);
            });

            CartItem cartItem = cart.getItems().stream()
                    .filter(item -> item.getPhoneId().equals(phoneId))
                    .findFirst()
                    .orElseGet(() -> {
                        CartItem newItem = new CartItem();
                        newItem.setPhoneId(phoneId);
                        newItem.setCartId(cart);
                        cart.getItems().add(newItem);
                        return newItem;
                    });

            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartRepository.save(cart);

        } catch (Exception e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }

    @Override
    public void removeItemFromCart(String userId, String phoneId) {
        try {
            Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new CartNotFoundException("Cart not found for userId: " + userId));
            if (cart != null) {
                cart.getItems().removeIf(item -> item.getPhoneId().equals(phoneId));
                cartRepository.save(cart);
            }
        } catch (CartNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }

    @Override
    public CartDTO getCart(String userId) {
        try {
            Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new CartNotFoundException("Cart not found for userId: " + userId));

            if (cart == null) {
                return null;
            }

            AccountDTO user = accountFeign.getUserById(userId);

            if (user == null) {
                throw new AccountNotFoundException("User not found with ID: " + userId);
            }

            CartDTO cartDTO = new CartDTO();
            cartDTO.setId(cart.getId());
            cartDTO.setUserId(user);
            cartDTO.setItems(cart.getItems().stream()
                    .map(item -> {
                        PhoneDTO phone = phoneFeign.getPhoneById(item.getPhoneId());
                        if (phone == null) {
                            throw new PhoneNotFoundException("Phone not found with ID: " + item.getPhoneId());
                        }
                        CartItemDTO cartItemDTO = new CartItemDTO();
                        cartItemDTO.setId(item.getId());
                        cartItemDTO.setPhone(phone);
                        cartItemDTO.setQuantity(item.getQuantity());
                        return cartItemDTO;
                    })
                    .collect(Collectors.toList()));
            return cartDTO;

        } catch (CartNotFoundException | AccountNotFoundException | PhoneNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }
}
