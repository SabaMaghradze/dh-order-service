package com.dailyhaul.order.service;

import com.dailyhaul.order.dto.CartItemRequest;
import com.dailyhaul.order.dto.CartItemResponse;
import com.dailyhaul.order.model.CartItem;

import java.util.List;

public interface CartService {

    boolean addToCart(String userId, CartItemRequest cartItemRequest);

    boolean removeFromCart(String userId, String productId);

    CartItemResponse fetchCart(Long cartId);

    List<CartItem> fetchUserCarts(String userId);

    void clearCart(String userId);
}
