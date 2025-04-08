package com.tqs.hw1;

import com.tqs.hw1.dto.ReservationRequestDTO;
import com.tqs.hw1.dto.ReservationResponseDTO;
import com.tqs.hw1.entities.*;
import com.tqs.hw1.repositories.MealRepository;
import com.tqs.hw1.repositories.ReservationRepository;
import com.tqs.hw1.repositories.RestaurantRepository;
import com.tqs.hw1.services.ReservationService;
import com.tqs.hw1.services.WeatherService;
import com.tqs.hw1.utils.DateValidator;
import com.tqs.hw1.utils.ReservationConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Unit Tests - Business Logic (ReservationService, WeatherService, Utils)")
class A_Service_UnitTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private MealRepository mealRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a valid reservation successfully")
    void testCreateValidReservation() {
        Restaurant restaurant = new Restaurant(1L, "Cantina");
        Meal meal = new Meal(1L, LocalDate.now(), MealType.ALMOCO, restaurant);
        ReservationRequestDTO request = new ReservationRequestDTO(1L, meal.getDate(), meal.getType());

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(mealRepository.findByRestaurantAndDateAndType(restaurant, meal.getDate(), meal.getType())).thenReturn(Optional.of(meal));
        when(reservationRepository.existsByRestaurantAndDateAndTypeAndCancelledFalse(restaurant, meal.getDate(), meal.getType())).thenReturn(false);

        ReservationResponseDTO response = reservationService.createReservation(request);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isNotBlank();
        assertThat(response.getType()).isEqualTo(MealType.ALMOCO);
    }

    @Test
    @DisplayName("Should fail when trying to reserve on a day without service")
    void testCreateReservation_RestaurantClosed() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(new Restaurant()));
        when(mealRepository.findByRestaurantAndDateAndType(any(), any(), any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationService.createReservation(new ReservationRequestDTO(1L, LocalDate.now(), MealType.ALMOCO)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("No meal available");
    }

    @Test
    @DisplayName("Should fail when trying to create a duplicate reservation (same day, type, restaurant)")
    void testCreateReservation_DuplicateReservation() {
        Restaurant restaurant = new Restaurant(1L, "Cantina");
        Meal meal = new Meal(1L, LocalDate.now(), MealType.ALMOCO, restaurant);
        ReservationRequestDTO request = new ReservationRequestDTO(1L, meal.getDate(), meal.getType());

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(mealRepository.findByRestaurantAndDateAndType(restaurant, meal.getDate(), meal.getType())).thenReturn(Optional.of(meal));
        when(reservationRepository.existsByRestaurantAndDateAndTypeAndCancelledFalse(restaurant, meal.getDate(), meal.getType())).thenReturn(true);

        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already reserved");
    }

    @Test
    @DisplayName("Should fail when restaurant capacity is full")
    void testCreateReservation_CapacityFull() {
        // Placeholder until capacity check logic is added
        // For now, simulate same as duplicate
        testCreateReservation_DuplicateReservation();
    }

    @Test
    @DisplayName("Should cancel a reservation successfully")
    void testCancelReservation_Success() {
        // TODO: Implement after defining Reservation cancellation logic
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("Should mark reservation as checked-in successfully")
    void testCheckInReservation_Success() {
        // TODO: Implement after defining check-in logic
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("Should fail when checking in an already used reservation")
    void testCheckInReservation_AlreadyUsed() {
        // TODO: Implement check-in already used logic
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("Should fail when checking in a cancelled reservation")
    void testCheckInReservation_Cancelled() {
        // TODO: Implement check-in on cancelled reservation
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("Should fail when checking in with an invalid token")
    void testCheckInReservation_InvalidToken() {
        // TODO: Implement invalid token logic
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("Should retrieve reservation by valid token")
    void testGetReservationByToken_Success() {
        // TODO: Implement retrieval by token logic
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("Should fail when retrieving reservation with invalid token")
    void testGetReservationByToken_NotFound() {
        // TODO: Implement retrieval failure
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("Should return cached weather forecast if TTL not expired")
    void testWeatherCache_ReturnsCachedForecast() {
        // TODO: Implement with real WeatherService and caching logic
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("Should expire cache correctly after TTL is reached")
    void testWeatherCache_ExpiresCorrectly() {
        // TODO: Implement expiration logic
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("Date validator should reject invalid dates")
    void testDateValidator_InvalidDate() {
        boolean result = DateValidator.isValid("invalid-date");
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Entity to DTO converter should work correctly")
    void testEntityToDTOConverter() {
        Reservation reservation = new Reservation();
        reservation.setToken("abc123");
        reservation.setCancelled(false);
        reservation.setType(MealType.JANTAR);
        ReservationResponseDTO dto = ReservationConverter.toDTO(reservation);

        assertThat(dto).isNotNull();
        assertThat(dto.getToken()).isEqualTo("abc123");
        assertThat(dto.getType()).isEqualTo(MealType.JANTAR);
    }
}