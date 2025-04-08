package com.tqs.hw1.utils;

import com.tqs.hw1.dtos.ReservationResponseDTO;
import com.tqs.hw1.entities.Reservation;

public class ReservationConverter {
    public static ReservationResponseDTO toDTO(Reservation r) {
        return new ReservationResponseDTO(r.getToken(), r.getType(), r.isCancelled());
    }
}