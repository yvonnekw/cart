package com.auction.cart.dto;

import java.math.BigDecimal;

public record CartItemRequest(
        Long productId,
        String productName,
        int quantity,
        double totalPrice
) {
}
