package com.vn.cart_service.controller;

import com.vn.cart_service.dto.CartDTO;
import com.vn.cart_service.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Void> addItemToCart(@RequestParam Long userId, @RequestParam Long phoneId,
            @RequestParam int quantity) {
        cartService.addItemToCart(userId, phoneId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeItemFromCart(@RequestParam Long userId, @RequestParam Long phoneId) {
        cartService.removeItemFromCart(userId, phoneId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long userId) {
        CartDTO cartDTO = cartService.getCart(userId);
        return ResponseEntity.ok(cartDTO);
    }
}
