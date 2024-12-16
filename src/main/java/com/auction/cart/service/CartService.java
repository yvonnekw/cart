package com.auction.cart.service;

import com.auction.cart.dto.CartItemResponse;
import com.auction.cart.dto.CartRequest;
import com.auction.cart.dto.CartResponse;
import com.auction.cart.dto.ProductResponse;
import com.auction.cart.exception.ProductNotFoundException;
import com.auction.cart.model.Cart;
import com.auction.cart.model.CartItem;

import com.auction.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final RestTemplate restTemplate;

    @Value("${application.config.product-url}")
    private String productServiceUrl;

    public CartResponse addItemToCart(String username, CartRequest request) throws ProductNotFoundException {
        Cart cart = cartRepository.findByUsername(username)
                .orElseGet(() -> cartRepository.save(new Cart(username)));

        ProductResponse productResponse = fetchProductById(request.productId(), username);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productResponse.productId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setProductId(productResponse.productId());
                    newItem.setPrice(productResponse.price() != null ? productResponse.price() : BigDecimal.ZERO);
                    newItem.setCart(cart);
                    return newItem;
                });

        item.setQuantity(item.getQuantity() + request.quantity());

        if (!cart.getItems().contains(item)) {
            cart.getItems().add(item);
        }

        cartRepository.save(cart);

        return mapToResponse(cart);
    }

    public CartResponse getCartByUsername(String username) {
        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cart not found for username: " + username));
        return mapToResponse(cart);
    }

    private ProductResponse fetchProductById(Long productId, String username) throws ProductNotFoundException {
        String url = productServiceUrl + "/" + productId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Username", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ProductResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, ProductResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            ProductResponse productResponse = response.getBody();
            if (productResponse.price() == null) {
                log.warn("Product with ID {} has a null price. Defaulting to 0.", productId);
                return new ProductResponse(productResponse.productId(), productResponse.productName(), BigDecimal.ZERO, productResponse.description());
            }
            return productResponse;
        } else {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }
    }

    private CartResponse mapToResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream()
                .map(item -> new CartItemResponse(
                        item.getCartItemId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice() != null ? item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())) : BigDecimal.ZERO
                ))
                .toList();

        return new CartResponse(cart.getCartId(), items);
    }

    public void clearCart(String username) {
        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cart not found for username: " + username));

        cart.getItems().clear();
        cartRepository.save(cart);
    }

        /*
    private ProductResponse fetchProductById(Long productId) throws ProductNotFoundException {
        String url = productServiceUrl + "/" + productId;
        // String url = productServiceUrl + "/api/v1/products/" + productId;
        ResponseEntity<ProductResponse> response = restTemplate.getForEntity(url, ProductResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            ProductResponse productResponse = response.getBody();
            if (productResponse.price() == null) {
                log.warn("Product with ID {} has a null price. Defaulting to 0.", productId);
                return new ProductResponse(productResponse.productId(), productResponse.productName(), BigDecimal.ZERO, productResponse.description());
            }
            return productResponse;
        } else {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }
    }
*/


    /*
    public CartResponse addItemToCart(String username, CartRequest request) throws ProductNotFoundException {
        Cart cart = cartRepository.findByUsername(username)
                .orElseGet(() -> cartRepository.save(new Cart(username)));

        ProductResponse productResponse = fetchProductById(request.productId());

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productResponse.productId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setProductId(productResponse.productId());
                    newItem.setPrice(productResponse.price());
                    newItem.setCart(cart);
                    return newItem;
                });

        item.setQuantity(item.getQuantity() + request.quantity());

        if (!cart.getItems().contains(item)) {
            cart.getItems().add(item);
        }

        cartRepository.save(cart);

        return mapToResponse(cart);
    }
*/

      /*
    //This is bound to fail
    private ProductResponse fetchProductById(Long productId) throws ProductNotFoundException {
        //String url = "/api/v1/products/" + productId;
        String url = productServiceUrl + "/api/v1/products/" + productId;
        ResponseEntity<ProductResponse> response = restTemplate.getForEntity(url, ProductResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }
    }
*/


    /*
    private CartResponse mapToResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream()
                .map(item -> new CartItemResponse(
                        item.getCartItemId(),
                        item.getProductId(),
                        item.getQuantity(),
                        //item.getPrice(),
                        item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))))
                .toList();

        return new CartResponse(cart.getCartId(), items);
    }
    */
}

/*
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final RestTemplate restTemplate;

    @Value("${application.config.product-url}")
    private String productServiceUrl;

    // Method to add item to cart
    public CartResponse addItemToCart(String username, CartRequest request) throws ProductNotFoundException {
        // Find or create cart by username
        Cart cart = cartRepository.findByUsername(username)
                .orElseGet(() -> cartRepository.save(new Cart(username)));

        // Fetch product details from ProductService via REST call
        ProductResponse productResponse = fetchProductById(request.productId());

        // Check if the product already exists in the cart, otherwise create a new CartItem
        CartItem cartItem = cart.getItems().stream()
                .filter(i -> i.productId().equals(productResponse.productId())) // Compare product ID
                .findFirst()
                .orElse(new CartItem(productResponse.toProduct, 0, cart));

        // Update item quantity
        item.setQuantity(item.getQuantity() + request.quantity());

        // Add the item to cart if not already present
        if (!cart.getItems().contains(item)) {
            cart.getItems().add(item);
        }

        // Save updated cart
        cartRepository.save(cart);

        return mapToResponse(cart);
    }

    // Method to get the user's cart by username
    public CartResponse getCartByUsername(String username) {
        // Fetch cart by username
        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cart not found for username: " + username));

        return mapToResponse(cart);
    }

    // Helper method to fetch product from ProductService
    private ProductResponse fetchProductById(Long productId) throws ProductNotFoundException {
        String url = productServiceUrl + "/api/v1/products/" + productId; // Adjust the endpoint as per your ProductService API
        ResponseEntity<ProductResponse> response = restTemplate.getForEntity(url, ProductResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }
    }

    // Helper method to map Cart to CartResponse DTO
    private CartResponse mapToResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream()
                .map(item -> new CartItemResponse(
                        item.getProduct(),
                        //item.getProduct().name(),
                        item.getQuantity(),
                        item.getProduct().price() * item.getQuantity()))
                .toList();

        return new CartResponse(cart.getCartId(), items.indexOf(0));
    }


    /*
    // Method to get a cart by username
    public CartResponse getCartByUsername(String username) {
        // Find cart by username
        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for username: " + username));

        // Map cart to CartResponse (convert Cart to DTO response)
        return mapToResponse(cart);
    }
/*
    // Method to map Cart to CartResponse DTO
    private CartResponse mapToResponse(Cart cart) {
        // Create CartResponse from cart and its items
        List<CartItemResponse> items = cart.getItems().stream()
                .map(item -> new CartItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getPrice() * item.getQuantity()))
                .collect(Collectors.toList());

        return new CartResponse(cart.getId(), items);
    }*/
//}
