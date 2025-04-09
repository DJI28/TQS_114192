package com.tqs.hw1.controllers;

import com.tqs.hw1.dtos.*;
import com.tqs.hw1.entities.*;
import com.tqs.hw1.services.*;
import com.tqs.hw1.repositories.MealRepository;
import com.tqs.hw1.weather.WeatherForecast;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

// ----------- RestaurantController Tests -----------
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantService service;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void testGetAllRestaurants() {
        when(service.getAllRestaurants()).thenReturn(List.of(new Restaurant("Cantina", 10)));
        RestAssuredMockMvc.given().when().get("/api/restaurants").then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void testAddRestaurant() {
        Restaurant r = new Restaurant("Nova", 20);
        when(service.addRestaurant(any())).thenReturn(r);
        RestAssuredMockMvc.given().contentType("application/json")
                .body(r).when().post("/api/restaurants").then()
                .statusCode(200)
                .body("name", is("Nova"));
    }

    @Test
    void testUpdateRestaurant() {
        Restaurant r = new Restaurant(1L, "Atualizada", 30);
        when(service.updateRestaurant(eq(1L), any())).thenReturn(r);
        RestAssuredMockMvc.given().contentType("application/json")
                .body(r).when().put("/api/restaurants/1").then()
                .statusCode(200)
                .body("name", is("Atualizada"));
    }

    @Test
    void testDeleteRestaurant() {
        doNothing().when(service).deleteRestaurant(1L);
        RestAssuredMockMvc.given().when().delete("/api/restaurants/1").then().statusCode(200);
    }
}

// ----------- MealController Tests -----------
@WebMvcTest(MealController.class)
class MealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MealRepository mealRepo;

    @MockitoBean
    private MealService mealService;

    @MockitoBean
    private WeatherService weatherService;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void testGetAllMeals() {
        Restaurant r = new Restaurant(1L, "Cantina", 10);
        Meal m = new Meal(1L, "Meal", LocalDate.now(), MealType.LUNCH, r);
        when(mealRepo.findAll()).thenReturn(List.of(m));
        when(weatherService.getForecast(any(), any())).thenReturn(new WeatherForecast());

        RestAssuredMockMvc.given().when().get("/api/meals").then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void testGetMealById() {
        Meal meal = new Meal(1L, "Meal", LocalDate.now(), MealType.LUNCH, new Restaurant());
        when(mealRepo.findById(1L)).thenReturn(Optional.of(meal));

        RestAssuredMockMvc.given().when().get("/api/meals/1").then()
                .statusCode(200)
                .body("name", is("Meal"));
    }

    @Test
    void testAddMeal() {
        Meal meal = new Meal("Pizza", LocalDate.now(), MealType.LUNCH, new Restaurant());
        when(mealService.addMeal(any())).thenReturn(meal);

        RestAssuredMockMvc.given().contentType("application/json")
                .body(meal).when().post("/api/meals").then()
                .statusCode(200)
                .body("name", is("Pizza"));
    }

    @Test
    void testUpdateMeal() {
        Meal updated = new Meal(1L, "Updated", LocalDate.now(), MealType.DINNER, new Restaurant());
        when(mealService.updateMeal(eq(1L), any())).thenReturn(updated);

        RestAssuredMockMvc.given().contentType("application/json")
                .body(updated).when().put("/api/meals/1").then()
                .statusCode(200)
                .body("name", is("Updated"));
    }

    @Test
    void testDeleteMeal() {
        doNothing().when(mealService).deleteMeal(1L);
        RestAssuredMockMvc.given().when().delete("/api/meals/1").then().statusCode(200);
    }
}

// ----------- ReservationController Tests -----------
@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationService reservationService;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void testCreateReservation() {
        ReservationResponseDTO response = new ReservationResponseDTO("token123", MealType.LUNCH, false);
        when(reservationService.createReservation(any())).thenReturn(response);

        RestAssuredMockMvc.given().contentType("application/json")
                .body(new ReservationRequestDTO(1L, LocalDate.now(), MealType.LUNCH))
                .when().post("/api/reservations").then()
                .statusCode(200)
                .body("token", is("token123"));
    }

    @Test
    void testGetAllReservations() {
        when(reservationService.getAllReservations()).thenReturn(List.of(new ReservationResponseDTO("token1", MealType.LUNCH, false)));
        RestAssuredMockMvc.given().when().get("/api/reservations").then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void testGetReservationByToken() {
        ReservationResponseDTO dto = new ReservationResponseDTO("token1", MealType.DINNER, false);
        when(reservationService.getReservation("token1")).thenReturn(dto);

        RestAssuredMockMvc.given().when().get("/api/reservations/token1").then()
                .statusCode(200)
                .body("type", is("DINNER"));
    }

    @Test
    void testCancelReservation() {
        doNothing().when(reservationService).cancelReservation("token1");
        RestAssuredMockMvc.given().when().delete("/api/reservations/token1").then().statusCode(200);
    }

    @Test
    void testCheckInReservation() {
        doNothing().when(reservationService).checkIn("token1");
        RestAssuredMockMvc.given().when().post("/api/reservations/checkin/token1").then().statusCode(200);
    }
}