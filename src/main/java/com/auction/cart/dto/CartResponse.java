package com.auction.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Long cartId;
    private List<CartItemResponse> items;
}

/*
public record CartResponse(
        Long cartId,
        List<CartItemResponse> items
) {

}
*/