package com.vn.cart_service.service;

import com.vn.cart_service.dto.AccountDTO;
import com.vn.cart_service.dto.CartDTO;
import com.vn.cart_service.dto.CartItemDTO;
import com.vn.cart_service.dto.PhoneDTO;
import com.vn.cart_service.entity.Cart;
import com.vn.cart_service.entity.CartItem;
import com.vn.cart_service.feign.IAccountFeign;
import com.vn.cart_service.feign.IPhoneFeign;
import com.vn.cart_service.repository.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CartService implements ICartService{

    @Autowired
    private ICartRepository cartRepository;


    @Autowired
    private IPhoneFeign phoneFeign;

    @Autowired
    private IAccountFeign accountFeign;

    @Override
    public void addItemToCart(Long userId, Long phoneId, int quantity) {
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
    }

    @Override
    public void removeItemFromCart(Long userId, Long phoneId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        if (cart != null) {
            cart.getItems().removeIf(item -> item.getPhoneId().equals(phoneId));
            cartRepository.save(cart);
        }
    }

    @Override
    public CartDTO getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        if (cart == null) {
            return null;
        }

        AccountDTO user = accountFeign.getUserById(userId);

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUserId(user);
        cartDTO.setItems(cart.getItems().stream()
                .map(item -> {
                    PhoneDTO phone = phoneFeign.getPhoneById(item.getPhoneId());
                    CartItemDTO cartItemDTO = new CartItemDTO();
                    cartItemDTO.setId(item.getId());
                    cartItemDTO.setPhone(phone);
                    cartItemDTO.setQuantity(item.getQuantity());
                    return cartItemDTO;
                })
                .collect(Collectors.toList()));
        return cartDTO;
    }
}
