package com.tqs.lab6_4_5.lab6_4_5_cars.repositories;

import com.tqs.lab6_4_5.lab6_4_5_cars.entities.Car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findByCarId(Long carId);
    List<Car> findAll();
    List<Car> findBySegmentAndEnginetypeAndTransmission(char segment, String enginetype, String transmission);
}
