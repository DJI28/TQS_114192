package com.tqs.lab3_2.lab3_2_cars.controllers;

import com.tqs.lab3_2.lab3_2_cars.entities.Car;
import com.tqs.lab3_2.lab3_2_cars.services.CarManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    private CarManagerService carManagerService;

    @Autowired
    public CarController(CarManagerService carManagerService) {
        this.carManagerService = carManagerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Car> createCar(@RequestBody  Car car) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carManagerService.save(car));
    }

    @GetMapping("/all")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carManagerService.getCarDetails(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}