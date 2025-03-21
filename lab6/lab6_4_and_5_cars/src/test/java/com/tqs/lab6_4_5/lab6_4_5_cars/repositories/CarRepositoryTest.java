package com.tqs.lab6_4_5.lab6_4_5_cars.repositories;

import com.tqs.lab6_4_5.lab6_4_5_cars.entities.Car;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

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
    @DisplayName("Test find car by id")
    public void whenFindPorscheByExistingId_thenReturnPorscheCar() {
        Car car = new Car("Porsche", "911");
        entityManager.persistAndFlush(car);

        Car fromDb = carRepository.findByCarId(car.getCarId());
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getCarId()).isEqualTo(car.getCarId());
    }

    @Test
    @DisplayName("Test find car by id")
    public void whenCreatedPorsche_thenReturnPorscheCar() {
        Car persistedCar = entityManager.persistAndFlush(new Car("Porsche", "911"));

        Car found = carRepository.findByCarId(persistedCar.getCarId());
        assertThat(found).isNotNull()
                .extracting(Car::getCarId).isEqualTo(persistedCar.getCarId());
    }

    @Test
    @DisplayName("Test find car by id")
    public void whenInvalidId_thenReturnNull() {
        Car fromDb = carRepository.findByCarId(-1111L);
        assertThat(fromDb).isNull();
    }

    @Test
    @DisplayName("Test find all cars")
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

    @Test
    @DisplayName("Test find car by segment, engine type and transmission")
    void whenFindBySegmentAndEnginetypeAndTransmission_thenReturnMatchingCars() {
        carRepository.save(new Car("Audi", "A4", 'D', "I4", "Manual"));
        carRepository.save(new Car("BMW", "420d", 'D', "I4", "Manual"));
        carRepository.save(new Car("Porsche", "911", 'S', "F6", "Automatic"));

        List<Car> similarCars = carRepository.findBySegmentAndEnginetypeAndTransmission('D', "I4", "Manual");

        assertThat(similarCars).hasSize(2);
        assertThat(similarCars.get(0).getMaker()).isEqualTo("Audi");
        assertThat(similarCars.get(1).getMaker()).isEqualTo("BMW");
    }
}
