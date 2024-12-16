package com.auction.cart.controller;

import com.auction.cart.dto.CartRequest;
import com.auction.cart.dto.CartResponse;

import com.auction.cart.dto.ProductResponse;
import com.auction.cart.exception.ProductNotFoundException;
import com.auction.cart.model.Cart;
import com.auction.cart.model.CartItem;
import com.auction.cart.repository.CartRepository;
import com.auction.cart.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartRepository cartRepository;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String getPayment() {
        return "cart api is working ";
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(
            @RequestHeader("X-Username") String username,
            @RequestBody CartRequest request) {
        try {
            CartResponse cartResponse = cartService.addItemToCart(username, request);
            return ResponseEntity.ok(cartResponse);
        } catch (Exception e) {
            log.error("Unexpected error while adding to cart for user: {}", username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/clear-cart")
    public ResponseEntity<?> clearCart(@RequestHeader("X-Username") String username) {
        try {
            cartService.clearCart(username);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Unexpected error while clearing cart for user: {}", username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


    @GetMapping("/get-user-cart")
    public ResponseEntity<?> getCart(@RequestHeader("X-Username") String username) {
        try {
            CartResponse cartResponse = cartService.getCartByUsername(username);
            return ResponseEntity.ok(cartResponse);
        } catch (Exception e) {
            log.error("Unexpected error while fetching cart for user: {}", username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

}

