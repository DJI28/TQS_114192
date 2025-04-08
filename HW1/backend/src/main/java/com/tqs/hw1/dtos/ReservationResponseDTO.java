package com.tqs.hw1.dtos;

import com.tqs.hw1.entities.MealType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {
    private String token;
    private MealType type;
    private boolean cancelled;
}