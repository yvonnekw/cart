package com.auction.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartRequest(
        @NotNull(message = "Product ID cannot be null")
        Long productId,
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) {
}

