package com.vn.cart_service.controller;

import com.vn.cart_service.dto.CartDTO;
import com.vn.cart_service.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private ICartService cartService;

    @Autowired
    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<String> addItemToCart(@RequestParam String userId, @RequestParam String phoneId,
            @RequestParam int quantity) {
        cartService.addItemToCart(userId, phoneId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product successfully added to cart");
    }

    @DeleteMapping
    public ResponseEntity<String> removeItemFromCart(@RequestParam String userId, @RequestParam String phoneId) {
        cartService.removeItemFromCart(userId, phoneId);
        return ResponseEntity.ok("Quickly remove product from cart");
    }

    @GetMapping("/{userId}")
    public CartDTO getCart(@PathVariable String userId) {
        return cartService.getCart(userId);

    }
}
