package com.dailyhaul.order.service.impl;

import com.dailyhaul.order.dto.CartItemRequest;
import com.dailyhaul.order.dto.CartItemResponse;
import com.dailyhaul.order.model.CartItem;
import com.dailyhaul.order.repository.CartRepository;
import com.dailyhaul.order.service.CartService;
import com.dailyhaul.order.utils.Mappers;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
//    private final ProductRepository productRepository;
//    private final UserRepository userRepository;

    @Override
    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {

//        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
//        if (productOpt.isEmpty()) {
//            return false;
//        }
//
//        Product product = productOpt.get();
//
//        if (product.getQuantity() < cartItemRequest.getQuantity()) {
//            return false;
//        }
//
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        if (userOpt.isEmpty()) {
//            return false;
//        }
//
//        User user = userOpt.get();

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
        return true;

    }

    @Override
    public boolean removeFromCart(String userId, String productId) {

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
