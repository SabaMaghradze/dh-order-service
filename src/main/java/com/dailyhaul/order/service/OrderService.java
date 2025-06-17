package com.dailyhaul.order.service;

import com.dailyhaul.order.dto.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<OrderResponse> createOrder(String userId);

    List<OrderResponse> getAllOrders(String userId);
}
