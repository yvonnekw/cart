package com.auction.cart.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    /*
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }*/
/*
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.errorHandler((ResponseErrorHandler) new RestTemplateResponseErrorHandler()).build();
    }*/

    //@Bean
   // public RestTemplate restTemplate() {
        //return new RestTemplate();
   // }
}
