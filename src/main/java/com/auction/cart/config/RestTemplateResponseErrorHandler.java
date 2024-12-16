package com.auction.cart.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

//@Slf4j
public class RestTemplateResponseErrorHandler  {
/*
    //extends DefaultResponseErrorHandler
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            log.error("Client error: {}", response.getStatusText());
        } else if (response.getStatusCode().is5xxServerError()) {
            log.error("Server error: {}", response.getStatusText());
        }
        super.handleError(response);
    }*/
}

