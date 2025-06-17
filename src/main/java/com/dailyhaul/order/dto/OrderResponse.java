package com.dailyhaul.order.dto;

import com.dailyhaul.order.model.OrderItem;
import com.dailyhaul.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class OrderResponse {

    private Long id;

    private BigDecimal totalAmount;

    private OrderStatus status = OrderStatus.PENDING;

    private List<OrderItemResponse> orderItemsResponses = new ArrayList<>();

    private LocalDateTime createdAt;

    public OrderResponse(Long id, BigDecimal totalAmount, OrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }
}
