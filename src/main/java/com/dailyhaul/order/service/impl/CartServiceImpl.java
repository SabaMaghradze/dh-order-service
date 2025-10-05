package com.dailyhaul.order.service.impl;

import com.dailyhaul.order.client.ProductServiceClient;
import com.dailyhaul.order.client.UserServiceClient;
import com.dailyhaul.order.dto.CartItemRequest;
import com.dailyhaul.order.dto.CartItemResponse;
import com.dailyhaul.order.dto.ProductResponse;
import com.dailyhaul.order.dto.UserResponse;
import com.dailyhaul.order.exception.OutOfStockException;
import com.dailyhaul.order.exception.ProductNotFoundException;
import com.dailyhaul.order.exception.UserNotFoundException;
import com.dailyhaul.order.model.CartItem;
import com.dailyhaul.order.repository.CartRepository;
import com.dailyhaul.order.service.CartService;
import com.dailyhaul.order.utils.Mappers;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;

    @Override
    public void addToCart(String userId, CartItemRequest cartItemRequest) {

        ProductResponse productResponse = productServiceClient.getProductById(cartItemRequest.getProductId());

        if (productResponse == null) {
            throw new ProductNotFoundException("Product not found");
        }

        if (productResponse.getQuantity() == null || productResponse.getQuantity() == 0) {
            throw new OutOfStockException("Product is out of stock");
        }

        if (productResponse.getQuantity() < cartItemRequest.getQuantity()) {
            throw new OutOfStockException("This product of " + cartItemRequest.getQuantity() + " units is not available");
        }

        Optional<UserResponse> userOpt = userServiceClient.getUser(userId);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        CartItem existingCartItem = cartRepository.findByUserIdAndProductId(userId, cartItemRequest.getProductId());

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(100.00));
            cartRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem(userId,
                    cartItemRequest.getProductId(),
                    cartItemRequest.getQuantity(),
                    BigDecimal.valueOf(100.00));

            cartRepository.save(cartItem);
        }
    }

    @Override
    public boolean removeFromCart(String userId, Long productId) {

//        Optional<Product> productOpt = productRepository.findById(productId);
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if (productId != null && userId != null) {
            cartRepository.deleteByUserIdAndProductId(userId, productId);
            return true;
        }

        return false;
    }

    @Override
    public CartItemResponse fetchCart(Long cartId) {
        return cartRepository.findById(cartId)
                .map(Mappers::getCartResponse)
                .orElse(null);
    }

    @Override
    public List<CartItem> fetchUserCarts(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void clearCart(String userId) {
        cartRepository.deleteByUserId(userId);
    }
}
