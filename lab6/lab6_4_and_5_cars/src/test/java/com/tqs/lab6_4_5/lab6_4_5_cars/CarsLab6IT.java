package com.tqs.lab6_4_5.lab6_4_5_cars;

import com.tqs.lab6_4_5.lab6_4_5_cars.entities.Car;
import com.tqs.lab6_4_5.lab6_4_5_cars.repositories.CarRepository;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static io.restassured.RestAssured.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
class CarsLab6IT {

    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:13.2")
            .withDatabaseName("cars")
            .withUsername("cars001")
            .withPassword("password001");

    @Autowired
    private CarRepository repository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        //registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop"); // Only need if not using Flyway or Liquibase
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Test
    @DisplayName("Verify Migration")
    public void verifyMigration() {
        assertThat(repository.count()).isEqualTo(10);
    }

    @Test
    @DisplayName("When valid input then create car")
    void whenValidInput_thenCreateCar() {
        Car car = new Car("Toyota", "Corolla");
        Car car2 = new Car("Peugeot", "308SW");
        Car car3 = new Car("Porsche", "911 GT3 RS", 'S', "F6", "Manual");
        given().port(randomServerPort).contentType("application/json").body(car)
                .when().post("/api/v1/cars/create").then().statusCode(201);
        given().port(randomServerPort).contentType("application/json").body(car2)
                .when().post("/api/v1/cars/create").then().statusCode(201);
        given().port(randomServerPort).contentType("application/json").body(car3)
                .when().post("/api/v1/cars/create").then().statusCode(201);

        List<Car> found = repository.findAll();
        assertThat(found).extracting(Car::getMaker).contains("Toyota", "Peugeot", "Porsche");
    }

    @Test
    @DisplayName("When get all cars by id then return car")
    void givenCars_whenGetCars_thenStatus200()  {
        createTestCar("Renault", "Clio");
        createTestCar("VW", "Golf");

        List<Car> response = given().port(randomServerPort).contentType("application/json")
                .when().get("/api/v1/cars/all")
                .then().statusCode(200)
                .extract().body().as(new TypeRef<List<Car>>() {});

        assertThat(response).extracting(Car::getMaker).contains("Renault", "VW");
    }

    @Test
    @DisplayName("When get car by id then return car")
    void whenValidInput_thenGetCar() {
        Car car = new Car("Ford", "Focus");
        Car savedCar = repository.saveAndFlush(car);

        Car response = given().port(randomServerPort).contentType("application/json")
                .when().get("/api/v1/cars/id/" + savedCar.getCarId())
                .then().statusCode(200)
                .extract().body().as(Car.class);

        assertThat(response).extracting(Car::getMaker).isEqualTo("Ford");
    }

    @Test
    @DisplayName("When get similar car by id then return car")
    void whenValidInput_thenGetSimilarCar() {
        Long id = createTestCar("Bugatti", "Veyron", 'S', "W16", "Automatic");
        createTestCar("Bugatti", "Chiron", 'S', "W16", "Automatic");

        Car responseCar = given().port(randomServerPort).contentType("application/json")
                .when().get("/api/v1/cars/similar/" + id)
                .then().statusCode(200)
                .extract().body().as(Car.class);

        assertThat(responseCar).extracting(Car::getModel).isEqualTo("Chiron");
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