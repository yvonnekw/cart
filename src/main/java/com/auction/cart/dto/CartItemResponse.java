package com.auction.cart.dto;

import com.auction.cart.model.Cart;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public record CartItemResponse(
        Long cartItemId,
        Long productId,
        int quantity,
        BigDecimal price
) {
}
