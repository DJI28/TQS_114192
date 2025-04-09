package com.tqs.hw1.controllers;

import com.tqs.hw1.dtos.MealWithWeatherDTO;
import com.tqs.hw1.entities.Meal;
import com.tqs.hw1.repositories.MealRepository;
import com.tqs.hw1.services.MealService;
import com.tqs.hw1.services.WeatherService;
import com.tqs.hw1.weather.WeatherForecast;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealRepository mealRepository;
    private final MealService mealService;
    private final WeatherService weatherService;

    // GET todas as refeições com previsão do tempo
    @GetMapping
    public List<MealWithWeatherDTO> getAllMealsWithWeather() {
        return mealRepository.findAll().stream().map(meal -> {
            WeatherForecast forecast = weatherService.getForecast("Aveiro", meal.getDate());
            return new MealWithWeatherDTO(
                    meal.getId(),
                    meal.getName(),
                    meal.getDate(),
                    meal.getType(),
                    meal.getRestaurant().getId(),
                    meal.getRestaurant().getName(),
                    forecast
            );
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable Long id) {
        return mealRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Meal> addMeal(@RequestBody Meal meal) {
        return ResponseEntity.ok(mealService.addMeal(meal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meal> updateMeal(@PathVariable Long id, @RequestBody Meal updatedMeal) {
        return ResponseEntity.ok(mealService.updateMeal(id, updatedMeal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.ok().build();
    }
}
