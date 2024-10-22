package com.vn.cart_service.controller;

import com.vn.cart_service.dto.request.AddItemRequest;
import com.vn.cart_service.dto.request.DeleteItemRequest;
import com.vn.cart_service.dto.response.ApiResponse;
import com.vn.cart_service.dto.response.CartResponse;
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
    public ResponseEntity<String> addItemToCart(@RequestBody AddItemRequest addItemRequest) {
        cartService.addItemToCart(addItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product successfully added to cart");
    }

    @DeleteMapping
    public ResponseEntity<String> removeItemFromCart(@RequestBody DeleteItemRequest deleteItemRequest) {
        cartService.removeItemFromCart(deleteItemRequest);
        return ResponseEntity.ok("Quickly remove product from cart");
    }

    @GetMapping("/{userId}")
    public ApiResponse<CartResponse> getCart(@PathVariable String userId) {
        ApiResponse<CartResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartService.getCart(userId));
        return apiResponse;

    }
}
