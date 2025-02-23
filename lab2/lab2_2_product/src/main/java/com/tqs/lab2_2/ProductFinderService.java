package com.tqs.lab2_2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.Optional;

public class ProductFinderService {
    private final String API_PRODUCTS;
    private final ISimpleHttpClient httpClient;

    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
        this.API_PRODUCTS = "https://fakestoreapi.com/products";
    }

    public Optional<Product> findProductDetails(int id) throws IOException, ParseException {
        String response = httpClient.doHttpGet(API_PRODUCTS + "/" + id);
        if (response == null) {
            return Optional.empty();
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return Optional.of(objectMapper.readValue(response, Product.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}