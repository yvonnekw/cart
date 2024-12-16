package com.auction.cart.dto;


import java.math.BigDecimal;


public record ProductResponse(
        Long productId,
        String productName,
        BigDecimal price,
        String description
) {

}
