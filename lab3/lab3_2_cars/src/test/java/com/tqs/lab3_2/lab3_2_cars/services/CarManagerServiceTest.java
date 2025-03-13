package com.tqs.lab3_2.lab3_2_cars.services;

import com.tqs.lab3_2.lab3_2_cars.entities.Car;
import com.tqs.lab3_2.lab3_2_cars.repositories.CarRepository;
import com.tqs.lab3_2.lab3_2_cars.services.impl.CarManagerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Car Manager Service Test")
public class CarManagerServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerServiceImpl carManagerService;

    @BeforeEach
    public void setUp() {
        Car car1 = new Car("Audi", "A4");
        car1.setCarId(111L);

        Car car2 = new Car("BMW", "X5");
        Car car3 = new Car("Mercedes", "C220");

        List<Car> cars = Arrays.asList(car1, car2, car3);

        Mockito.when(carRepository.findById(car1.getCarId())).thenReturn(Optional.of(car1));
        Mockito.when(carRepository.findById(car2.getCarId())).thenReturn(Optional.of(car2));
        Mockito.when(carRepository.findById(-99L)).thenReturn(Optional.empty());
        Mockito.when(carRepository.findById(car3.getCarId())).thenReturn(Optional.of(car3));
        Mockito.when(carRepository.findAll()).thenReturn(cars);
    }

    @Test
    @DisplayName("Test Car Found on Valid Id")
    public void whenValidId_thenCarShouldBeFound() {
        Car fromDb = carManagerService.getCarDetails(111L).orElse(null);
        assert fromDb != null;
        assertThat(fromDb.getCarId()).isEqualTo(111L);
        verifyFindByCarIdIsCalledOnce();
    }

    @Test
    @DisplayName("Test Car Not Found on Invalid Id")
    public void whenInvalidId_thenCarShouldNotBeFound() {
        Car fromDb = carManagerService.getCarDetails(-99L).orElse(null);
        verifyFindByCarIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    @DisplayName("Test Get All Cars")
    public void given3Cars_whenGetAll_thenReturn3Records() {
        Car car1 = new Car("Audi", "A4");
        Car car2 = new Car("BMW", "X5");
        Car car3 = new Car("Mercedes", "C220");

        List<Car> allCars = carManagerService.getAllCars();
        verifyFindAllCarsIsCalledOnce();
        assertThat(allCars).hasSize(3).extracting(Car::getCarId).contains(car1.getCarId(), car2.getCarId(), car3.getCarId());
    }

    private void verifyFindByCarIdIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }
}
