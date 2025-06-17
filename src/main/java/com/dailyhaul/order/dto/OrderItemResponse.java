package com.dailyhaul.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class OrderItemResponse {

    private Long id;

    private String productId;

    private Integer quantity;

    private BigDecimal price;

}
