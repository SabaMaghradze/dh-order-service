package com.dailyhaul.order.service.impl;

import com.dailyhaul.order.dto.OrderResponse;
import com.dailyhaul.order.model.CartItem;
import com.dailyhaul.order.model.Order;
import com.dailyhaul.order.model.OrderItem;
import com.dailyhaul.order.model.OrderStatus;
import com.dailyhaul.order.repository.OrderRepository;
import com.dailyhaul.order.service.CartService;
import com.dailyhaul.order.service.OrderService;
import com.dailyhaul.order.utils.Mappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    @Override
    public Optional<OrderResponse> createOrder(String userId) {

        List<CartItem> cartItems = cartService.fetchUserCarts(userId);

        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

//        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
//
//        if (userOptional.isEmpty()) {
//            return Optional.empty();
//        }
//
//        User user = userOptional.get();

        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                )).toList();

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(userId);

        return Optional.of(Mappers.getOrderResponse(order));

    }

    @Override
    public List<OrderResponse> getAllOrders(String userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(order -> Mappers.getOrderResponse(order))
                .toList();
    }
}





