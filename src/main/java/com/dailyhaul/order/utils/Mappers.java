package com.dailyhaul.order.utils;

import com.dailyhaul.order.dto.CartItemResponse;
import com.dailyhaul.order.dto.OrderItemResponse;
import com.dailyhaul.order.dto.OrderResponse;
import com.dailyhaul.order.model.CartItem;
import com.dailyhaul.order.model.Order;
import com.dailyhaul.order.model.OrderItem;

import java.util.List;

public class Mappers {

    public static CartItemResponse getCartResponse(CartItem cartItem) {
        return new CartItemResponse(cartItem.getId(),
                cartItem.getUserId(),
                cartItem.getProductId(),
                cartItem.getQuantity(),
                cartItem.getPrice());
    }

    public static OrderItemResponse getOrderItemResponse(OrderItem orderItem) {
        return new OrderItemResponse(orderItem.getId(),
                orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getPrice());
    }

    public static OrderResponse getOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse(order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt());

        List<OrderItemResponse> orderItemResponses = order.getOrderItems()
                .stream()
                .map(Mappers::getOrderItemResponse)
                .toList();

        orderResponse.setOrderItemsResponses(orderItemResponses);

        return orderResponse;
    }
}
