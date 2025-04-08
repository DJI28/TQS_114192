package com.tqs.hw1.repositories;

import com.tqs.hw1.entities.MealType;
import com.tqs.hw1.entities.Reservation;
import com.tqs.hw1.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByRestaurantAndDateAndTypeAndCancelledFalse(Restaurant restaurant, LocalDate date, MealType type);
    Optional<Reservation> findByToken(String token);
}