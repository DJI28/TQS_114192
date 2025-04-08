package com.tqs.hw1.controllers;

import com.tqs.hw1.entities.Restaurant;
import com.tqs.hw1.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.addRestaurant(restaurant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @PutMapping
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(restaurant));
    }
}