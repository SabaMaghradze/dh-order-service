package com.dailyhaul.order.controller;

import com.dailyhaul.order.dto.OrderResponse;
import com.dailyhaul.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<OrderResponse> placeOrder(@RequestHeader("X-User-ID") String userId) {
        return orderService.createOrder(userId)
                .map(orderResponse -> new ResponseEntity<>(orderResponse, HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/all-orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders(@RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok().body(orderService.getAllOrders(userId));
    }

}
