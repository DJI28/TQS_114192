package com.tqs.hw1.dtos;

import com.tqs.hw1.entities.MealType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
    private Long restaurantId;
    private LocalDate date;
    private MealType type;
}