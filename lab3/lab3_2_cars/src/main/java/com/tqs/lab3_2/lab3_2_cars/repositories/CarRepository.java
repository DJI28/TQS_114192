package com.tqs.lab3_2.lab3_2_cars.repositories;

import com.tqs.lab3_2.lab3_2_cars.entities.Car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findByCarId(Long carId);
    List<Car> findAll();
    List<Car> findBySegmentAndEnginetypeAndTransmission(char segment, String enginetype, String transmission);
}
