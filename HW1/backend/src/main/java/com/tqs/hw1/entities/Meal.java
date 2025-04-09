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
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private MealType type;

    @ManyToOne
    private Restaurant restaurant;

    public Meal(String name, LocalDate date, MealType type, Restaurant restaurant) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.restaurant = restaurant;
    }
}