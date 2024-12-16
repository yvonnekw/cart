package com.auction.cart.service;

import com.auction.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductMapper {
   // private final CartRepository cartRepository;
    //private final RestTemplate restTemplate;

   // @Value("${product.service.url}") // ProductService base URL from application.yml
  //  private String productServiceUrl;
/*
    public CartResponse addItemToCart(String username, CartRequest request) {
        // Find or create the cart
        Cart cart = cartRepository.findByUsername(username)
                .orElseGet(() -> cartRepository.save(new Cart(username)));

        // Fetch product details from ProductService
        ProductResponse productResponse = fetchProductById(request.productId());

        // Check if the product already exists in the cart
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

        // Update the item quantity
        item.setQuantity(item.getQuantity() + request.quantity());

        // Add the item to the cart if not already present
        if (!cart.getItems().contains(item)) {
            cart.getItems().add(item);
        }

        // Save the updated cart
        cartRepository.save(cart);

        return mapToResponse(cart);
    }

    public CartResponse getCartByUsername(String username) {
        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cart not found for username: " + username));
        return mapToResponse(cart);
    }

    private ProductResponse fetchProductById(Long productId) {
        String url = productServiceUrl + "/api/v1/products/" + productId; // Adjust endpoint as needed
        ResponseEntity<ProductResponse> response = restTemplate.getForEntity(url, ProductResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }
    }

    private CartResponse mapToResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream()
                .map(item -> new CartItemResponse(
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getPrice() * item.getQuantity()))
                .toList();

        return new CartResponse(cart.getCartId(), items);
    }

 */
}



/*
@Service
@RequiredArgsConstructor
public class ProductMapper {

    public ProductRequest toProductRequest(ProductResponse productResponse) {
        if (productResponse == null) {
            throw new IllegalArgumentException("ProductResponse cannot be null");
        }
        return new ProductRequest(
                productResponse.productId(),
                productResponse.name(),
                productResponse.price()
        );
    }

    // Map ProductRequest to ProductResponse
    public ProductResponse toProductResponse(ProductRequest productRequest) {
        if (productRequest == null) {
            throw new IllegalArgumentException("ProductRequest cannot be null");
        }
        return new ProductResponse(
                productRequest.productId(),
                productRequest.name(),
                productRequest.price()
        );
    }
}



 */