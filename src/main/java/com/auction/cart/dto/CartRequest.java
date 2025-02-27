package com.auction.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
        private Long productId;
        private Integer quantity;
}
/*
public record CartRequest(
        @NotNull(message = "Product ID cannot be null")
        Long productId,
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) {
}

*/