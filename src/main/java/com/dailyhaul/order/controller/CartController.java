package com.dailyhaul.order.controller;

import com.dailyhaul.order.dto.CartItemRequest;
import com.dailyhaul.order.dto.CartItemResponse;
import com.dailyhaul.order.model.CartItem;
import com.dailyhaul.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
                                            @RequestBody CartItemRequest cartItemRequest) {

        cartService.addToCart(userId, cartItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromCart(@RequestHeader("X-User-ID") String userId,
                                                 @PathVariable Long productId) {

        boolean deleted = cartService.removeFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartItemResponse> fetchCart(@PathVariable Long cartId) {

        CartItemResponse response = cartService.fetchCart(cartId);

        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(response);
    }

    @GetMapping("/my-carts")
    public ResponseEntity<List<CartItem>> fetchUserCarts(@RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(cartService.fetchUserCarts(userId));
    }
}










