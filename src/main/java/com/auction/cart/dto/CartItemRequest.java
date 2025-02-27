package com.auction.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {
    private Long productId;
    private int quantity;
    private BigDecimal price;
    private String productName;
    private String description;
    private String productImageUrl;
}
