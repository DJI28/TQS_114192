package com.tqs.lab3_2.lab3_2_cars.repositories;

import com.tqs.lab3_2.lab3_2_cars.entities.Car;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void whenFindPorscheByExistingId_thenReturnPorscheCar() {
        Car car = new Car("Porsche", "911");
        entityManager.persistAndFlush(car);

        Car fromDb = carRepository.findByCarId(car.getCarId());
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getCarId()).isEqualTo(car.getCarId());
    }

    @Test
    public void whenCreatedPorsche_thenReturnPorscheCar() {
        Car persistedCar = entityManager.persistAndFlush(new Car("Porsche", "911"));

        Car found = carRepository.findByCarId(persistedCar.getCarId());
        assertThat(found).isNotNull()
                .extracting(Car::getCarId).isEqualTo(persistedCar.getCarId());
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Car fromDb = carRepository.findByCarId(-1111L);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car porsche = new Car("Porsche", "911");
        Car ferrari = new Car("Ferrari", "F40");
        Car lamborghini = new Car("Lamborghini", "Diablo");

        entityManager.persist(porsche);
        entityManager.persist(ferrari);
        entityManager.persist(lamborghini);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();
        assertThat(allCars).hasSize(3).extracting(Car::getModel).containsOnly(porsche.getModel(), ferrari.getModel(), lamborghini.getModel());
    }
}
