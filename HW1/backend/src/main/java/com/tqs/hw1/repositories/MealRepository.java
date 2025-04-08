package com.tqs.hw1.repositories;

import com.tqs.hw1.entities.Meal;
import com.tqs.hw1.entities.MealType;
import com.tqs.hw1.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {
    Optional<Meal> findByRestaurantAndDateAndType(Restaurant restaurant, LocalDate date, MealType type);
}