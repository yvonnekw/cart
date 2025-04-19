package com.auction.cart.service;

import com.auction.cart.dto.*;
import com.auction.cart.exception.ProductNotFoundException;
import com.auction.cart.exception.ResourceNotFoundException;
import com.auction.cart.model.Cart;
import com.auction.cart.model.CartItem;
import com.auction.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;

    public CartResponse addItemToCart(String username, CartRequest request, String token) throws ProductNotFoundException {
        Cart cart = cartRepository.findByUsername(username)
                .orElseGet(() -> cartRepository.save(new Cart(username)));

        ProductResponse productResponse = productServiceClient.getProductById(request.getProductId(), token);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productResponse.getProductId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setProductId(productResponse.getProductId());
                    newItem.setPrice(productResponse.getGetBuyNowPrice() != null ? productResponse.getGetBuyNowPrice() : BigDecimal.ZERO);
                    newItem.setQuantity(0);
                    newItem.setCart(cart);
                    return newItem;
                });

        item.setQuantity(item.getQuantity() + request.getQuantity());
        item.setProductName(productResponse.getProductName());
        item.setDescription(productResponse.getDescription());
        item.setProductImageUrl(productResponse.getProductImageUrl());

        if (!cart.getItems().contains(item)) {
            cart.getItems().add(item);
        }

        cartRepository.save(cart);

        return mapToResponse(cart);
    }

    private CartResponse mapToResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream()
                .map(item -> new CartItemResponse(
                        item.getCartItemId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice() != null ? item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())) : BigDecimal.ZERO,
                        item.getProductName(),
                        item.getDescription(),
                        item.getProductImageUrl()
                ))
                .collect(Collectors.toList());

        return new CartResponse(cart.getCartId(), items);
    }

    public void clearCart(String username) {
        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cart not found for username: " + username));

        cart.getItems().clear();
        cartRepository.save(cart);
    }

    public CartResponse getCartByUsername(String username) {
        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cart not found for username: " + username));
        return mapToResponse(cart);
    }

    public Cart getCartItems(String username) {
        return cartRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for username: " + username));
    }
    public void removeItem(Long itemId) {
        Cart item = cartRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemId));
        cartRepository.delete(item);
    }

    public void updateCartItem(String username, UpdateCartItemRequest request) {
        Cart existingCart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("No cart exists for the user: " + username));

        CartItem item = existingCart.getItems().stream()
                .filter(cartItem -> cartItem.getProductId().equals(request.productId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Product with ID " + request.productId() + " does not exist in the cart for user: " + username
                ));

        item.setQuantity(request.quantity());
        cartRepository.save(existingCart);
    }

    public List<CartResponse> getAllCarts() {
        return cartRepository.findAll()
                .stream()
                .map(cart -> new CartResponse(
                        cart.getCartId(),
                        cart.getItems()
                                .stream()
                                .map(cartItem -> new CartItemResponse(
                                        cartItem.getCartItemId(),
                                        cartItem.getProductId(),
                                        cartItem.getQuantity(),
                                        cartItem.getPrice(),
                                        cartItem.getProductName(),
                                        cartItem.getDescription(),
                                        cartItem.getProductImageUrl()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}


