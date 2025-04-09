    package com.tqs.hw1;

    import com.tqs.hw1.entities.Meal;
    import com.tqs.hw1.entities.MealType;
    import com.tqs.hw1.entities.Restaurant;
    import com.tqs.hw1.repositories.ReservationRepository;
    import com.tqs.hw1.repositories.RestaurantRepository;
    import com.tqs.hw1.repositories.MealRepository;
    import io.restassured.http.ContentType;
    import org.junit.jupiter.api.*;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.boot.test.web.server.LocalServerPort;
    import org.springframework.test.context.DynamicPropertyRegistry;
    import org.springframework.test.context.DynamicPropertySource;
    import org.testcontainers.containers.PostgreSQLContainer;
    import org.testcontainers.junit.jupiter.Container;
    import org.testcontainers.junit.jupiter.Testcontainers;

    import java.time.LocalDate;
    import java.util.Map;

    import static io.restassured.RestAssured.*;
    import static org.hamcrest.Matchers.*;
    import static org.assertj.core.api.Assertions.assertThat;

    @Testcontainers
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @AutoConfigureMockMvc
    @DisplayName("API Integration Tests (with PostgreSQL Testcontainers) - Restaurant")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class RestaurantControllerTCIT {

        @LocalServerPort
        int randomServerPort;

        @Container
        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("mealbooking_test")
                .withUsername("admin")
                .withPassword("admin");

        @Autowired
        private RestaurantRepository restaurantRepository;

        @DynamicPropertySource
        static void properties(DynamicPropertyRegistry registry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl);
            registry.add("spring.datasource.username", postgres::getUsername);
            registry.add("spring.datasource.password", postgres::getPassword);
            registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
        }

        @Test
        @DisplayName("Verify restaurant migration")
        @Order(0)
        void verifyMigration() {
            assertThat(restaurantRepository.count()).isGreaterThanOrEqualTo(2);
        }

        @Test
        @DisplayName("Add new restaurant")
        @Order(1)
        void addRestaurant() {
            Restaurant restaurant = new Restaurant("Restaurant", 10);

            given().port(randomServerPort).contentType("application/json")
                    .body(restaurant)
                    .when()
                    .post("/api/restaurants")
                    .then()
                    .statusCode(200)
                    .body("name", equalTo("Restaurant"))
                    .body("capacity", equalTo(10));
        }

        @Test
        @DisplayName("Get all restaurants")
        @Order(2)
        void getAllRestaurants() {
            given().port(randomServerPort)
                    .contentType(ContentType.JSON).
                    when()
                    .get("/api/restaurants")
                    .then()
                    .statusCode(200)
                    .body("size()", greaterThanOrEqualTo(2));
        }

        @Test
        @DisplayName("Updated Restaurant")
        @Order(3)
        void updateRestaurant() {
            Restaurant restaurant = new Restaurant("Updated Restaurant", 20);

            Long id = Long.valueOf(given().port(randomServerPort).contentType("application/json")
                    .body(restaurant)
                    .when()
                    .post("/api/restaurants")
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("id").toString());

            Restaurant updatedRestaurant = new Restaurant(id, "Updated Restaurant", 40);

            given().port(randomServerPort).contentType("application/json")
                    .body(updatedRestaurant)
                    .when()
                    .put("/api/restaurants/" + id)
                    .then()
                    .statusCode(200)
                    .body("name", equalTo("Updated Restaurant"))
                    .body("capacity", equalTo(40));
        }

        @Test
        @DisplayName("Delete restaurant")
        @Order(4)
        void deleteRestaurant() {
            Restaurant restaurant = new Restaurant("Restaurant", 10);
            long id = Long.parseLong(given().port(randomServerPort).contentType("application/json")
                    .body(restaurant)
                    .when()
                    .post("/api/restaurants")
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("id").toString());

            given().port(randomServerPort)
                    .contentType("application/json")
                    .when()
                    .delete("/api/restaurants/" + id)
                    .then()
                    .statusCode(200);
        }
    }

    @Testcontainers
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @AutoConfigureMockMvc
    @DisplayName("API Integration Tests (with PostgreSQL Testcontainers) - Meal")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class MealControllerTCIT {

        @LocalServerPort
        int randomServerPort;

        @Container
        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("mealbooking_test")
                .withUsername("admin")
                .withPassword("admin");

        @Autowired
        private MealRepository mealRepository;

        @Autowired
        private RestaurantRepository restaurantRepository;

        @DynamicPropertySource
        static void properties(DynamicPropertyRegistry registry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl);
            registry.add("spring.datasource.username", postgres::getUsername);
            registry.add("spring.datasource.password", postgres::getPassword);
            registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
        }

        @Test
        @DisplayName("Verify meal migration")
        @Order(0)
        void verifyMigration() {
            assertThat(mealRepository.count()).isGreaterThanOrEqualTo(4);
        }

        @Test
        @DisplayName("Add new meal")
        @Order(1)
        void addMeal() {
            Meal meal = new Meal("Pizza", LocalDate.now(), MealType.LUNCH, restaurantRepository.findById(1L).orElseThrow());

            given().port(randomServerPort).contentType("application/json")
                    .body(meal)
                    .when()
                    .post("/api/meals")
                    .then()
                    .statusCode(200)
                    .body("name", equalTo("Pizza"))
                    .body("type", equalTo("LUNCH"));
        }

        @Test
        @DisplayName("Get all meals")
        @Order(2)
        void getAllMeals() {
            given().port(randomServerPort)
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/api/meals")
                    .then()
                    .statusCode(200)
                    .body("size()", greaterThanOrEqualTo(1));
        }

        @Test
        @DisplayName("Update meal")
        @Order(3)
        void updateMeal() {
            Meal meal = new Meal("Pasta", LocalDate.now(), MealType.DINNER, restaurantRepository.findById(1L).orElseThrow());
            Long id = Long.valueOf(given().port(randomServerPort).contentType("application/json")
                    .body(meal)
                    .when()
                    .post("/api/meals")
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("id").toString());

            Meal updatedMeal = new Meal(id, "Updated Pasta", LocalDate.now(), MealType.LUNCH, restaurantRepository.findById(1L).orElseThrow());

            given().port(randomServerPort).contentType("application/json")
                    .body(updatedMeal)
                    .when()
                    .put("/api/meals/" + id)
                    .then()
                    .statusCode(200)
                    .body("name", equalTo("Updated Pasta"))
                    .body("type", equalTo("LUNCH"));
        }

        @Test
        @DisplayName("Delete meal")
        @Order(4)
        void deleteMeal() {
            Meal meal = new Meal("Burger", LocalDate.now(), MealType.LUNCH, restaurantRepository.findById(1L).orElseThrow());
            long id = Long.parseLong(given().port(randomServerPort).contentType("application/json")
                    .body(meal)
                    .when()
                    .post("/api/meals")
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("id").toString());

            given().port(randomServerPort)
                    .contentType("application/json")
                    .when()
                    .delete("/api/meals/" + id)
                    .then()
                    .statusCode(200);
        }
    }

    @Testcontainers
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @AutoConfigureMockMvc
    @DisplayName("API Integration Tests (with PostgreSQL Testcontainers) - Reservation")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class ReservationControllerTCIT {

        @LocalServerPort
        int port;

        @Container
        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("mealbooking_test")
                .withUsername("admin")
                .withPassword("admin");

        @Autowired
        private ReservationRepository reservationRepository;

        @Autowired
        private RestaurantRepository restaurantRepository;

        static String createdToken;

        static final String FIXED_DATE = "2025-04-11";
        static final String FIXED_TYPE = "LUNCH";

        @DynamicPropertySource
        static void properties(DynamicPropertyRegistry registry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl);
            registry.add("spring.datasource.username", postgres::getUsername);
            registry.add("spring.datasource.password", postgres::getPassword);
            registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
        }

        @Test
        @Order(0)
        @DisplayName("Verify reservation migration")
        void verifyMigration() {
            assertThat(reservationRepository.count()).isGreaterThanOrEqualTo(2);
        }

        @Test
        @Order(1)
        @DisplayName("Create a new reservation and store token")
        void createReservation() {
            Long restaurantId = restaurantRepository.findAll().get(1).getId();

            Map<String, Object> requestBody = Map.of(
                    "restaurantId", restaurantId,
                    "date", FIXED_DATE,
                    "type", FIXED_TYPE
            );

            createdToken = given().port(port).contentType(ContentType.JSON)
                    .body(requestBody)
                    .when()
                    .post("/api/reservations")
                    .then()
                    .statusCode(200)
                    .body("token", notNullValue())
                    .body("type", equalTo(FIXED_TYPE))
                    .body("cancelled", equalTo(false))
                    .extract().path("token");
        }

        @Test
        @Order(2)
        @DisplayName("Get reservation by token")
        void getReservationByToken() {
            given().port(port)
                    .when()
                    .get("/api/reservations/" + createdToken)
                    .then()
                    .statusCode(200)
                    .body("token", equalTo(createdToken))
                    .body("type", equalTo(FIXED_TYPE));
        }

        @Test
        @Order(3)
        @DisplayName("Cancel reservation")
        void cancelReservation() {
            given().port(port)
                    .when()
                    .delete("/api/reservations/" + createdToken)
                    .then()
                    .statusCode(200);

            given().port(port)
                    .when()
                    .get("/api/reservations/" + createdToken)
                    .then()
                    .statusCode(200)
                    .body("cancelled", equalTo(true));
        }

        @Test
        @Order(4)
        @DisplayName("Check-in de uma reserva válida")
        void checkInReservation() {
            Long restaurantId = restaurantRepository.findAll().get(0).getId();
            String token = given().port(port).contentType(ContentType.JSON)
                    .body(Map.of(
                            "restaurantId", restaurantId,
                            "date", "2025-04-10",
                            "type", "DINNER"
                    ))
                    .when()
                    .post("/api/reservations")
                    .then()
                    .statusCode(200)
                    .extract().path("token");

            given().port(port)
                    .when()
                    .post("/api/reservations/checkin/" + token)
                    .then()
                    .statusCode(200);
        }
    }
