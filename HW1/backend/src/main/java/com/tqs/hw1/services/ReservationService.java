package com.tqs.hw1.services;

import com.tqs.hw1.dtos.ReservationRequestDTO;
import com.tqs.hw1.dtos.ReservationResponseDTO;
import com.tqs.hw1.entities.Reservation;
import com.tqs.hw1.entities.Meal;
import com.tqs.hw1.entities.Restaurant;
import com.tqs.hw1.repositories.ReservationRepository;
import com.tqs.hw1.repositories.MealRepository;
import com.tqs.hw1.repositories.RestaurantRepository;
import com.tqs.hw1.utils.ReservationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MealRepository mealRepository;
    private final RestaurantRepository restaurantRepository;

    public ReservationResponseDTO createReservation(ReservationRequestDTO request) {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new IllegalStateException("Invalid restaurant ID"));

        Meal meal = mealRepository.findByRestaurantAndDateAndType(restaurant, request.getDate(), request.getType())
                .orElseThrow(() -> new IllegalStateException("No meal available on this date"));

        boolean exists = reservationRepository.existsByRestaurantAndDateAndTypeAndCancelledFalse(restaurant, request.getDate(), request.getType());
        if (exists) {
            throw new IllegalStateException("Reservation already exists");
        }

        int currentReservations = reservationRepository.countByRestaurantAndDateAndTypeAndCancelledFalse(
                restaurant, request.getDate(), request.getType());
        if (currentReservations >= restaurant.getCapacity()) {
            throw new IllegalStateException("Restaurant is full");
        }

        Reservation reservation = new Reservation();
        reservation.setToken(UUID.randomUUID().toString());
        reservation.setDate(request.getDate());
        reservation.setType(request.getType());
        reservation.setRestaurant(restaurant);

        reservationRepository.save(reservation);
        return ReservationConverter.toDTO(reservation);
    }

    public ReservationResponseDTO getReservation(String token) {
        Reservation res = reservationRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));
        return ReservationConverter.toDTO(res);
    }

    public void cancelReservation(String token) {
        Reservation res = reservationRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));
        res.setCancelled(true);
        reservationRepository.save(res);
    }

    public void checkIn(String token) {
        Reservation res = reservationRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));
        if (res.isCancelled()) throw new IllegalStateException("Reservation is cancelled");
        if (res.isCheckedIn()) throw new IllegalStateException("Already checked-in");
        res.setCheckedIn(true);
        reservationRepository.save(res);
    }
}
