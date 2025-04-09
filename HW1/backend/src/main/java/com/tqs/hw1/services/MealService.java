package com.tqs.hw1.services;

import com.tqs.hw1.entities.Meal;
import com.tqs.hw1.repositories.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;

    public Meal addMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public Meal updateMeal(Long id, Meal updatedMeal) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new IllegalStateException("Meal not found"));
        meal.setName(updatedMeal.getName());
        meal.setDate(updatedMeal.getDate());
        meal.setType(updatedMeal.getType());
        meal.setRestaurant(updatedMeal.getRestaurant());
        return mealRepository.save(meal);
    }

    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }
}