package com.tqs.hw1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private MealType type;

    private boolean cancelled = false;

    private boolean checkedIn = false;

    @ManyToOne
    private Restaurant restaurant;
}