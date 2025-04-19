package com.auction.cart.controller;

import com.auction.cart.dto.CartRequest;
import com.auction.cart.dto.CartResponse;
import com.auction.cart.dto.UpdateCartItemRequest;
import com.auction.cart.model.Cart;
import com.auction.cart.repository.CartRepository;
import com.auction.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public ResponseEntity<?> addItemToCart(@RequestHeader("Authorization") String token,
                                           @RequestHeader("X-Username") String username,
                                           @RequestBody CartRequest request) {
        try {
            log.info("cart request body in the add controller {} ", request);
            log.info("cart user details in the add controller {} ", username);
            log.info("cart user details in the add controller {} ", token);
            CartResponse cartResponse = cartService.addItemToCart(username, request, token);
            return ResponseEntity.ok(cartResponse);
        } catch (Exception e) {
            log.error("Unexpected error while adding to cart for user: {}", username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/clear-cart")
    public ResponseEntity<?> clearCart(@RequestHeader("Authorization") String token, @RequestHeader("X-Username") String username) {
        try {
            cartService.clearCart(username);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Unexpected error while clearing cart for user: {}", username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/get-user-cart")
    public ResponseEntity<?> getCart(@RequestHeader("Authorization") String token, @RequestHeader("X-Username") String username) {
        try {
            CartResponse cartResponse = cartService.getCartByUsername(username);
            return ResponseEntity.ok(cartResponse);
        } catch (Exception e) {
            log.error("Unexpected error while fetching cart for user: {}", username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/items")
    public ResponseEntity<Cart> getCartItems(@RequestHeader("Authorization") String token, @RequestHeader("X-Username") String username) {
        Cart cartItems = cartService.getCartItems(username);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCart(@RequestHeader("Authorization") String token, @RequestHeader("X-Username") String username, @RequestBody UpdateCartItemRequest request) {
        try {
            cartService.updateCartItem(username, request);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            log.error("Error updating cart: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Respond with 400 Bad Request
        }
    }

    @GetMapping("/get-all-carts")
    @ResponseStatus(HttpStatus.OK)
    public List<CartResponse> getAllCarts() {
        return cartService.getAllCarts();
    }
}
