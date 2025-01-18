package com.auction.cart.dto;

public record UpdateCartItemRequest(
        Long productId,
        Integer quantity
) {
}
