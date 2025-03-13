package com.tqs.lab3_2.lab3_2_cars.repositories;

import com.tqs.lab3_2.lab3_2_cars.entities.Car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findByCarId(Long carId);
    List<Car> findAll();
}
