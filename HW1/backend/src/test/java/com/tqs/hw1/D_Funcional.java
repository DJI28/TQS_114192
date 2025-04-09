package com.tqs.hw1;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Functional Tests - Web UI with Selenium")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class D_Funcional {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    void setupAll() {
    }

    @AfterAll
    void tearDownAll() {
    }

    @Test
    @DisplayName("User can view meals for a restaurant")
    void testViewMealsPage() {
        fail("Not yet implemented");
    }

    @Test
    @DisplayName("User can book a meal and receive token")
    void testBookMealSuccess() {
        fail("Not yet implemented");
    }

    @Test
    @DisplayName("User cannot book the same meal twice")
    void testBookDuplicateMeal() {
        fail("Not yet implemented");
    }

    @Test
    @DisplayName("User can view their reservations on 'My Reservations' page")
    void testViewMyReservations() {
        fail("Not yet implemented");
    }

    @Test
    @DisplayName("User can cancel a reservation and it disappears from the list")
    void testCancelReservation() {
        fail("Not yet implemented");
    }

    @Test
    @DisplayName("Check-in page accepts valid token and shows success")
    void testCheckInWithValidToken() {
        fail("Not yet implemented");
    }

    @Test
    @DisplayName("Check-in page shows error for invalid token")
    void testCheckInWithInvalidToken() {
        fail("Not yet implemented");
    }

    @Test
    @DisplayName("Token is stored in localStorage after booking")
    void testTokenStoredInLocalStorage() {
        fail("Not yet implemented");
    }

    @Test
    @DisplayName("Weather forecast is displayed along with meals")
    void testWeatherDisplayedWithMeals() {
        fail("Not yet implemented");
    }
}
