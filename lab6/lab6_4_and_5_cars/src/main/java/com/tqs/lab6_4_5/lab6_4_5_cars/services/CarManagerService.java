package com.tqs.lab6_4_5.lab6_4_5_cars.services;

import com.tqs.lab6_4_5.lab6_4_5_cars.entities.Car;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CarManagerService {
    Car save(Car Car);
    List<Car> getAllCars();
    Optional<Car> getCarDetails(Long id);
    Optional<Car> findSimilar(Long id);
}