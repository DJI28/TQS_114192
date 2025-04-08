package com.tqs.hw1.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs.hw1.weather.WeatherForecast;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    private final Map<String, CachedForecast> cache = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    private static final long TTL = 5 * 60 * 1000;

    public WeatherForecast getForecast(String location, LocalDate date) {
        String key = location + ":" + date;
        CachedForecast cached = cache.get(key);

        if (cached != null && System.currentTimeMillis() - cached.timestamp < TTL) {
            return cached.forecast;
        }

        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, location, apiKey);
        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = mapper.readTree(response);
            JsonNode forecast = root.get("list").get(0); // simplificação: usa primeiro item
            String description = forecast.get("weather").get(0).get("description").asText();
            int temperature = (int) forecast.get("main").get("temp").asDouble();

            WeatherForecast result = new WeatherForecast(description, temperature);
            cache.put(key, new CachedForecast(result));
            return result;

        } catch (Exception e) {
            return new WeatherForecast("unavailable", 0);
        }
    }

    public Map<String, CachedForecast> getCacheStats() {
        return cache;
    }

    private record CachedForecast(WeatherForecast forecast, long timestamp) {
        CachedForecast(WeatherForecast forecast) {
            this(forecast, System.currentTimeMillis());
        }
    }
}
