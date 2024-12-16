package com.auction.cart.dto;


import java.math.BigDecimal;

public record ProductRequest(
        Long productId,

       String name,
      // String description,

       double price

) {

}
