package com.tqs.lab6_4_5.lab6_4_5_cars.controller;

import com.tqs.lab6_4_5.lab6_4_5_cars.controllers.CarController;
import com.tqs.lab6_4_5.lab6_4_5_cars.services.CarManagerService;
import com.tqs.lab6_4_5.lab6_4_5_cars.entities.Car;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

@WebMvcTest(CarController.class)
public class CarControllerRestAssuredTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @DisplayName("Test create car")
    public void whenPostCar_thenCreateCar() {
        when(service.save(Mockito.any())).thenReturn(new Car(1L, "Toyota", "Corolla"));

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .mockMvc(mockMvc)
                .body("{\"maker\":\"Toyota\",\"model\":\"Corolla\"}")
                .when().post("/api/v1/cars/create")
                .then()
                .statusCode(201)
                .body("carId", is(1))
                .body("maker", is("Toyota"))
                .body("model", is("Corolla"));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Test get all cars")
    public void whenGetAllCars_thenReturnAllCars() {
        when(service.getAllCars()).thenReturn(List.of(new Car(1L, "Toyota", "Corolla"),
                new Car(2L, "Peugeot", "308SW"),
                new Car("Porsche", "911 GT3 RS", 'S', "F6", "Manual")));

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .mockMvc(mockMvc)
                .when().get("/api/v1/cars/all")
                .then()
                .statusCode(200)
                .body("maker", hasSize(3))
                .body("maker", hasItems("Toyota", "Peugeot", "Porsche"));

        verify(service, times(1)).getAllCars();
    }

    @Test
    @DisplayName("Test get car by id")
    public void whenGetCarById_thenReturnCar() {
        when(service.getCarDetails(1L)).thenReturn(Optional.of(new Car(1L, "Toyota", "Corolla")));

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .mockMvc(mockMvc)
                .when().get("/api/v1/cars/id/1")
                .then()
                .statusCode(200)
                .body("carId", is(1))
                .body("maker", is("Toyota"))
                .body("model", is("Corolla"));

        verify(service, times(1)).getCarDetails(1L);
    }

    @Test
    @DisplayName("Test get car by id not found")
    public void whenGetCarByIdNotFound_thenReturnNull() {
        when(service.getCarDetails(-99L)).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .mockMvc(mockMvc)
                .when().get("/api/v1/cars/id/-99")
                .then()
                .statusCode(404);

        verify(service, times(1)).getCarDetails(-99L);
    }

    @Test
    @DisplayName("Test get similar car by id")
    public void whenGetSimilarCarById_thenReturnSimilarCar() {
        Car car1 = new Car("Audi", "A4", 'D', "I4", "Manual");
        car1.setCarId(1L);
        Car car2 = new Car("BMW", "420d", 'D', "I4", "Manual");
        car2.setCarId(2L);
        Car car3 = new Car("Mercedes", "C220", 'D', "I4", "Automatic");
        car3.setCarId(3L);

        when(service.findSimilar(1L)).thenReturn(Optional.of(car2));

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .mockMvc(mockMvc)
                .when().get("/api/v1/cars/similar/1")
                .then()
                .statusCode(200)
                .body("carId", is(2))
                .body("maker", is("BMW"))
                .body("model", is("420d"));
    }
}