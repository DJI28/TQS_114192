package com.tqs.lab3_2.lab3_2_cars.controller;

import com.tqs.lab3_2.lab3_2_cars.entities.Car;
import com.tqs.lab3_2.lab3_2_cars.services.CarManagerService;
import com.tqs.lab3_2.lab3_2_cars.controllers.CarController;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

@WebMvcTest(CarController.class)
@DisplayName("Car Controller Test")
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CarManagerService service;

    @Test
    @DisplayName("Test create car")
    public void whenPostCar_thenCreateCar() throws Exception {
        Car car = new Car(1L, "Toyota", "Corolla");

        when(service.save(Mockito.any())).thenReturn(car);

        mvc.perform(post("/api/v1/cars/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.carId", is(car.getCarId().intValue())))
                        .andExpect(jsonPath("$.maker", is("Toyota")))
                        .andExpect(jsonPath("$.model", is("Corolla")));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Test get all cars")
    public void givenManyCars_whenAllCars_thenReturnJsonArray() throws Exception {
        Car car1 = new Car(1L, "Toyota", "Corolla");
        Car car2 = new Car(2L, "Ford", "Focus");
        Car car3 = new Car(3L, "Renault", "Clio");

        when(service.getAllCars()).thenReturn(List.of(car1, car2, car3));

        mvc.perform(get("/api/v1/cars/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].carId", is(1)))
                .andExpect(jsonPath("$[0].maker", is("Toyota")))
                .andExpect(jsonPath("$[0].model", is("Corolla")))
                .andExpect(jsonPath("$[1].carId", is(2)))
                .andExpect(jsonPath("$[1].maker", is("Ford")))
                .andExpect(jsonPath("$[1].model", is("Focus")))
                .andExpect(jsonPath("$[2].carId", is(3)))
                .andExpect(jsonPath("$[2].maker", is("Renault")))
                .andExpect(jsonPath("$[2].model", is("Clio")));

        verify(service, times(1)).getAllCars();
    }

    @Test
    @DisplayName("Test get car by id")
    public void givenCar_whenGetCarById_thenReturnJson() throws Exception {
        Car car = new Car("Toyota", "Corolla");
        car.setCarId(12L);

        when(service.getCarDetails(1L)).thenReturn(Optional.of(car));

        mvc.perform(get("/api/v1/cars/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId", is(12)))
                .andExpect(jsonPath("$.maker", is("Toyota")))
                .andExpect(jsonPath("$.model", is("Corolla")));

        verify(service, times(1)).getCarDetails(1L);
    }

    @Test
    @DisplayName("Test get car by id not found")
    public void givenNoCar_whenGetCarById_thenReturnNull() throws Exception {
        when(service.getCarDetails(999L)).thenReturn(java.util.Optional.empty());

        mvc.perform(get("/api/v1/cars/id/999"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getCarDetails(999L);
    }

    @Test
    @DisplayName("Test get similar car")
    public void givenSimilarCar_whenGetSimilarCar_thenReturnJson() throws Exception {
        Car car1 = new Car("Audi", "A4", 'D', "I4", "Manual");
        car1.setCarId(1L);
        Car car2 = new Car("BMW", "420d", 'D', "I4", "Manual");
        car2.setCarId(2L);

        when(service.findSimilar(1L)).thenReturn(Optional.of(car2));

        mvc.perform(get("/api/v1/cars/similar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker").value("BMW"))
                .andExpect(jsonPath("$.model").value("420d"));
    }
}
