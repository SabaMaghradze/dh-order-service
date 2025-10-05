package com.dailyhaul.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {

    private Long id;

    private String userId;

    private Long productId;

    private Integer quantity;

    private BigDecimal price;
}
