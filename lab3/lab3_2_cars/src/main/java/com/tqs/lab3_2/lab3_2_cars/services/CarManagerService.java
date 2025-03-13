package com.tqs.lab3_2.lab3_2_cars.services;

import com.tqs.lab3_2.lab3_2_cars.entities.Car;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CarManagerService {
    Car save(Car Car);
    List<Car> getAllCars();
    Optional<Car> getCarDetails(Long id);
}