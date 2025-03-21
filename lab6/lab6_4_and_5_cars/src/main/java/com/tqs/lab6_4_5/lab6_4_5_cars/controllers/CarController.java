package com.tqs.lab6_4_5.lab6_4_5_cars.controllers;

import com.tqs.lab6_4_5.lab6_4_5_cars.entities.Car;
import com.tqs.lab6_4_5.lab6_4_5_cars.services.CarManagerService;

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

    @GetMapping("/similar/{id}")
    public ResponseEntity<Car> getSimilarCar(@PathVariable Long id) {
        return carManagerService.findSimilar(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}