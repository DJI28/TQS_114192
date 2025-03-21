/*
package com.tqs.lab6_4_5.lab6_4_5_cars;

import com.tqs.lab6_4_5.lab6_4_5_cars.entities.Car;
import com.tqs.lab6_4_5.lab6_4_5_cars.repositories.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @AutoConfigureTestDatabase

// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
@TestPropertySource( locations = "application-integrationtest.properties")
class CarsIT {

    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    // a REST client that is test-friendly
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("When valid input then create car")
    void whenValidInput_thenCreateCar() {
        Car car = new Car("Toyota", "Corolla");
        Car car2 = new Car("Peugeot", "308SW");
        Car car3 = new Car("Porsche", "911 GT3 RS", 'S', "F6", "Manual");
        restTemplate.postForEntity("/api/v1/cars/create", car, Car.class);
        restTemplate.postForEntity("/api/v1/cars/create", car2, Car.class);
        restTemplate.postForEntity("/api/v1/cars/create", car3, Car.class);

        List<Car> found = repository.findAll();
        assertThat(found).extracting(Car::getMaker).containsExactly("Toyota", "Peugeot", "Porsche");
    }

    @Test
    @DisplayName("When get all cars by id then return car")
    void givenCars_whenGetCars_thenStatus200()  {
        createTestCar("Renault", "Clio");
        createTestCar("VW", "Golf");

        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/v1/cars/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).containsExactly("Renault", "VW");
    }

    @Test
    @DisplayName("When get car by id then return car")
    void whenValidInput_thenGetCar() {
        Car car = new Car("Ford", "Focus");
        Car savedCar = repository.saveAndFlush(car);

        ResponseEntity<Car> response = restTemplate.getForEntity("/api/v1/cars/id/" + savedCar.getCarId(), Car.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).isEqualTo("Ford");
    }

    @Test
    @DisplayName("When get similar car by id then return car")
    void whenValidInput_thenGetSimilarCar() {
        Long id = createTestCar("Bugatti", "Veyron", 'S', "W16", "Automatic");
        createTestCar("Bugatti", "Chiron", 'S', "W16", "Automatic");

        ResponseEntity<Car> response = restTemplate.getForEntity("/api/v1/cars/similar/" + id, Car.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getModel).isEqualTo("Chiron");
    }

    private void createTestCar(String maker, String model) {
        Car car = new Car(maker, model);
        repository.saveAndFlush(car);
    }

    private Long createTestCar(String maker, String model, char segment, String enginetype, String transmission) {
        Car car = new Car(maker, model, segment, enginetype, transmission);
        repository.saveAndFlush(car);
        return car.getCarId();
    }
}
 */