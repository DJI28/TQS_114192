package com.tqs.hw1.controllers;

import com.tqs.hw1.dtos.ReservationRequestDTO;
import com.tqs.hw1.dtos.ReservationResponseDTO;
import com.tqs.hw1.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO dto) {
        return ResponseEntity.ok(reservationService.createReservation(dto));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{token}")
    public ResponseEntity<ReservationResponseDTO> getReservation(@PathVariable String token) {
        return ResponseEntity.ok(reservationService.getReservation(token));
    }

    @DeleteMapping("/{token}")
    public ResponseEntity<Void> cancelReservation(@PathVariable String token) {
        reservationService.cancelReservation(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkin/{token}")
    public ResponseEntity<Void> checkIn(@PathVariable String token) {
        reservationService.checkIn(token);
        return ResponseEntity.ok().build();
    }
}