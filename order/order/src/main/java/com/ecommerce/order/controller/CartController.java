package com.ecommerce.order.controller;


import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addToCart(@RequestHeader("X-User-ID") String userId, @RequestBody CartItemRequest request) {
       cartService.addToCart(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String userId, @PathVariable Long productId) {
        // Implement remove from cart logic
        boolean result = cartService.deleteItemFromCart(productId,userId);
        return result ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/items/{productId}")
    public ResponseEntity<CartItem> getCartItem(@RequestHeader("X-User-ID") String userId, @PathVariable Long productId) {
        // Implement get cart item logic
        CartItem cartItem = cartService.getCartItem(userId, productId);
        return cartItem != null ? ResponseEntity.ok(cartItem) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            @RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }
}
