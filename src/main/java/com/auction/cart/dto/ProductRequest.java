package com.auction.cart.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long productId;
    private String productName;
    private String description;
    private String productImageUrl;
    private BigDecimal getBuyNowPrice;
    private Integer quantity;
}
