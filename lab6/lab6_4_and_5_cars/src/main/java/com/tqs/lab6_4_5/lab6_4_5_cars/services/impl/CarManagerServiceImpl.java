package com.tqs.lab6_4_5.lab6_4_5_cars.services.impl;

import java.util.List;
import java.util.Optional;

import com.tqs.lab6_4_5.lab6_4_5_cars.entities.Car;
import com.tqs.lab6_4_5.lab6_4_5_cars.repositories.CarRepository;
import com.tqs.lab6_4_5.lab6_4_5_cars.services.CarManagerService;

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

    @Override
    public Optional<Car> findSimilar(Long id) {
        return carRepository.findById(id)
                .flatMap(car -> carRepository.findBySegmentAndEnginetypeAndTransmission(car.getSegment(), car.getEnginetype(), car.getTransmission())
                .stream()
                .filter(replacement -> !replacement.getCarId().equals(id))
                .findFirst());
    }
}
