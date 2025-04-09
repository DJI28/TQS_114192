package com.tqs.hw1.dtos;

import com.tqs.hw1.entities.MealType;
import com.tqs.hw1.weather.WeatherForecast;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealWithWeatherDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private MealType type;
    private Long restaurantId;
    private String restaurantName;
    private WeatherForecast weather;
}