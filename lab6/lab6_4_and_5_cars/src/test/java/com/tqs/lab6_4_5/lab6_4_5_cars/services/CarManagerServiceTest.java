package com.tqs.lab6_4_5.lab6_4_5_cars.services;

import com.tqs.lab6_4_5.lab6_4_5_cars.entities.Car;
import com.tqs.lab6_4_5.lab6_4_5_cars.repositories.CarRepository;
import com.tqs.lab6_4_5.lab6_4_5_cars.services.impl.CarManagerServiceImpl;

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

        Car car4 = new Car("Audi", "A4", 'D', "I4", "Manual");
        car4.setCarId(222L);
        Car similarTo4 = new Car("BMW" , "420d", 'D', "I4", "Manual");
        similarTo4.setCarId(333L);
        Car notSimilarTo4 = new Car("BMW" , "420d", 'D', "I4", "Automatic");
        notSimilarTo4.setCarId(444L);

        Car car5 = new Car("Porsche", "911 Turbo S", 'S', "F6", "Automatic");
        car5.setCarId(555L);
        Car similarTo5 = new Car("Porsche", "911 GT3 RS", 'S', "F6", "Automatic");
        similarTo5.setCarId(666L);

        Car car6 = new Car("Lamborghini", "Aventador SVJ", 'S', "V12", "Automatic");
        car6.setCarId(777L);
        Car similarTo6 = new Car("Ferrari", "812 Superfast", 'S', "V12", "Automatic");
        similarTo6.setCarId(888L);

        List<Car> cars = Arrays.asList(car1, car2, car3);

        Mockito.when(carRepository.findById(car1.getCarId())).thenReturn(Optional.of(car1));
        Mockito.when(carRepository.findById(car2.getCarId())).thenReturn(Optional.of(car2));
        Mockito.when(carRepository.findById(-99L)).thenReturn(Optional.empty());
        Mockito.when(carRepository.findById(car3.getCarId())).thenReturn(Optional.of(car3));
        Mockito.when(carRepository.findById(car4.getCarId())).thenReturn(Optional.of(car4));
        Mockito.when(carRepository.findById(car5.getCarId())).thenReturn(Optional.of(car5));
        Mockito.when(carRepository.findById(car6.getCarId())).thenReturn(Optional.of(car6));
        Mockito.when(carRepository.findById(similarTo4.getCarId())).thenReturn(Optional.of(similarTo4));
        Mockito.when(carRepository.findById(similarTo5.getCarId())).thenReturn(Optional.of(similarTo5));
        Mockito.when(carRepository.findById(similarTo6.getCarId())).thenReturn(Optional.of(similarTo6));
        Mockito.when(carRepository.findBySegmentAndEnginetypeAndTransmission(car4.getSegment(), car4.getEnginetype(), car4.getTransmission())).thenReturn(List.of(similarTo4));
        Mockito.when(carRepository.findBySegmentAndEnginetypeAndTransmission(car5.getSegment(), car5.getEnginetype(), car5.getTransmission())).thenReturn(List.of(similarTo5));
        Mockito.when(carRepository.findBySegmentAndEnginetypeAndTransmission(car6.getSegment(), car6.getEnginetype(), car6.getTransmission())).thenReturn(List.of(similarTo6));
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

    @Test
    @DisplayName("Test Get Similar Car")
    public void givenCar_whenGetSimilarCar_thenReturnSimilarCar() {
        Optional<Car> similarCar = carManagerService.findSimilar(222L);
        assertThat(similarCar).isPresent();
        assertThat(similarCar.get().getCarId()).isEqualTo(333L);

        similarCar = carManagerService.findSimilar(555L);
        assertThat(similarCar).isPresent();
        assertThat(similarCar.get().getCarId()).isEqualTo(666L);

        similarCar = carManagerService.findSimilar(777L);
        assertThat(similarCar).isPresent();
        assertThat(similarCar.get().getCarId()).isEqualTo(888L);
        verifyFindByCarIdIsCalledThreeTimes();
    }

    @Test
    @DisplayName("Test Get Similar Car Not Found")
    public void givenCar_whenGetSimilarCar_thenReturnNull() {
        Optional<Car> similarCar = carManagerService.findSimilar(444L);
        assertThat(similarCar).isEmpty();
        verifyFindByCarIdIsCalledOnce();
    }

    private void verifyFindByCarIdIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }

    private void verifyFindByCarIdIsCalledThreeTimes() {
        Mockito.verify(carRepository, VerificationModeFactory.times(3)).findById(Mockito.anyLong());
    }
}
