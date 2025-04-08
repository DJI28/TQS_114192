// Do for postgresql later

package com.tqs.hw1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("API Integration Tests (H2 in-memory)")
@ActiveProfiles("test")
class C_IT {
    @BeforeAll
    static void configureRestAssured() {
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    @DisplayName("GET /api/meals should return meals grouped by date and type")
    void testGetMeals() {
        given()
                .port(port)
                .when()
                .get("/api/meals")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("POST /api/reservations should return token for valid reservation")
    void testPostReservation_Success() {
        Map<String, Object> body = Map.of(
                "restaurantId", 1,
                "date", LocalDate.now().toString(),
                "type", "ALMOCO"
        );

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/reservations")
                .then()
                .statusCode(200)
                .body("token", not(emptyString()));
    }

    @Test
    @DisplayName("POST /api/reservations should return 400 for duplicate reservation")
    void testPostReservation_Duplicate() {
        Map<String, Object> body = Map.of(
                "restaurantId", 1,
                "date", LocalDate.now().toString(),
                "type", "ALMOCO"
        );

        // First reservation
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/reservations")
                .then()
                .statusCode(200);

        // Duplicate reservation
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/reservations")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("GET /api/reservations/{token} should return reservation details")
    void testGetReservationByToken_Success() {
        Map<String, Object> body = Map.of(
                "restaurantId", 1,
                "date", LocalDate.now().toString(),
                "type", "JANTAR"
        );

        String token =
                given()
                        .port(port)
                        .contentType(ContentType.JSON)
                        .body(body)
                        .when()
                        .post("/api/reservations")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token");

        given()
                .port(port)
                .when()
                .get("/api/reservations/" + token)
                .then()
                .statusCode(200)
                .body("token", equalTo(token));
    }

    @Test
    @DisplayName("GET /api/reservations/{token} should return 404 for invalid token")
    void testGetReservationByToken_NotFound() {
        given()
                .port(port)
                .when()
                .get("/api/reservations/invalid-token")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("DELETE /api/reservations/{token} should cancel reservation")
    void testCancelReservation() {
        Map<String, Object> body = Map.of(
                "restaurantId", 1,
                "date", LocalDate.now().toString(),
                "type", "JANTAR"
        );

        String token =
                given()
                        .port(port)
                        .contentType(ContentType.JSON)
                        .body(body)
                        .when()
                        .post("/api/reservations")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token");

        given()
                .port(port)
                .when()
                .delete("/api/reservations/" + token)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("POST /api/checkin/{token} should mark reservation as used")
    void testCheckInReservation() {
        Map<String, Object> body = Map.of(
                "restaurantId", 1,
                "date", LocalDate.now().toString(),
                "type", "ALMOCO"
        );

        String token =
                given()
                        .port(port)
                        .contentType(ContentType.JSON)
                        .body(body)
                        .when()
                        .post("/api/reservations")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token");

        given()
                .port(port)
                .when()
                .post("/api/checkin/" + token)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("GET /api/cache/stats should return cache statistics")
    void testGetCacheStats() {
        given()
                .port(port)
                .when()
                .get("/api/cache/stats")
                .then()
                .statusCode(200)
                .body("totalRequests", greaterThanOrEqualTo(0));
    }
}
