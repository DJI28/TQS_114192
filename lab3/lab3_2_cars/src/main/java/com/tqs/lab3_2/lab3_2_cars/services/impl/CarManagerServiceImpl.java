package com.tqs.lab3_2.lab3_2_cars.services.impl;

import java.util.List;
import java.util.Optional;

import com.tqs.lab3_2.lab3_2_cars.entities.Car;
import com.tqs.lab3_2.lab3_2_cars.repositories.CarRepository;
import com.tqs.lab3_2.lab3_2_cars.services.CarManagerService;

import org.springframework.stereotype.Service;

@Service
public class CarManagerServiceImpl implements CarManagerService {

    private final CarRepository carRepository;

    public CarManagerServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car save(Car Car) {
        return carRepository.save(Car);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> getCarDetails(Long id) {
        return carRepository.findById(id);
    }
}
