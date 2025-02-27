package com.auction.cart.service;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.auction.cart.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ProductServiceClient {


    @Value("${application.config.product-url}")
    private String productServiceUrl;

    private final RestTemplate restTemplate;

    public ProductServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductResponse getProductById(Long productId, String token) {
        String url = productServiceUrl + "/" + productId;

        HttpHeaders headers = new HttpHeaders();
        //headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ProductResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, ProductResponse.class);

        log.info("Received product response: {}", response.getBody());
        return response.getBody();
    }
}