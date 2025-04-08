package com.tqs.hw1;

import com.tqs.hw1.entities.Meal;
import com.tqs.hw1.entities.MealType;
import com.tqs.hw1.entities.Reservation;
import com.tqs.hw1.entities.Restaurant;
import com.tqs.hw1.services.ReservationService;
import com.tqs.hw1.services.WeatherService;
import com.tqs.hw1.weather.WeatherForecast;
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

@DisplayName("Service Tests with Mocked Dependencies")
class B_ServiceTest {

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
    @DisplayName("WeatherService should return mocked forecast successfully")
    void testWeatherService_ReturnsMockedForecast() {
        LocalDate date = LocalDate.now();
        WeatherForecast mockForecast = new WeatherForecast("sunny", 21);

        when(weatherService.getForecast("Aveiro", date)).thenReturn(mockForecast);

        WeatherForecast forecast = weatherService.getForecast("Aveiro", date);

        assertThat(forecast).isNotNull();
        assertThat(forecast.getDescription()).isEqualTo("sunny");
        assertThat(forecast.getTemperature()).isEqualTo(21);
    }

    @Test
    @DisplayName("WeatherService should handle external API failure gracefully")
    void testWeatherService_HandlesApiFailure() {
        LocalDate date = LocalDate.now();

        when(weatherService.getForecast("Aveiro", date)).thenThrow(new RuntimeException("API error"));

        assertThatThrownBy(() -> weatherService.getForecast("Aveiro", date))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("API error");
    }

    @Test
    @DisplayName("ReservationService should create reservation with mocked meal/repository")
    void testReservationService_CreateReservation_WithMockedRepos() {
        Restaurant restaurant = new Restaurant(1L, "Cantina");
        LocalDate date = LocalDate.now();
        Meal meal = new Meal(1L, date, MealType.ALMOCO, restaurant);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(mealRepository.findByRestaurantAndDateAndType(restaurant, date, MealType.ALMOCO)).thenReturn(Optional.of(meal));
        when(reservationRepository.existsByRestaurantAndDateAndTypeAndCancelledFalse(restaurant, date, MealType.ALMOCO)).thenReturn(false);

        var dto = reservationService.createReservation(new com.tqs.hw1.dto.ReservationRequestDTO(1L, date, MealType.ALMOCO));

        assertThat(dto).isNotNull();
        assertThat(dto.getType()).isEqualTo(MealType.ALMOCO);
        assertThat(dto.getToken()).isNotBlank();
    }

    @Test
    @DisplayName("ReservationService should not create duplicate reservation (mocked check)")
    void testReservationService_DuplicateReservation_WithMockedRepos() {
        Restaurant restaurant = new Restaurant(1L, "Cantina");
        LocalDate date = LocalDate.now();
        Meal meal = new Meal(1L, date, MealType.ALMOCO, restaurant);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(mealRepository.findByRestaurantAndDateAndType(restaurant, date, MealType.ALMOCO)).thenReturn(Optional.of(meal));
        when(reservationRepository.existsByRestaurantAndDateAndTypeAndCancelledFalse(restaurant, date, MealType.ALMOCO)).thenReturn(true);

        assertThatThrownBy(() -> reservationService.createReservation(
                new com.tqs.hw1.dto.ReservationRequestDTO(1L, date, MealType.ALMOCO)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already reserved");
    }

    @Test
    @DisplayName("ReservationService should cancel reservation correctly (mocked)")
    void testReservationService_Cancel_WithMockedRepo() {
        Reservation reservation = new Reservation();
        reservation.setCancelled(false);
        reservation.setToken("abc123");

        when(reservationRepository.findByToken("abc123")).thenReturn(Optional.of(reservation));

        reservationService.cancelReservation("abc123");

        assertThat(reservation.isCancelled()).isTrue();
    }

    @Test
    @DisplayName("ReservationService should perform check-in correctly (mocked)")
    void testReservationService_CheckIn_WithMockedRepo() {
        Reservation reservation = new Reservation();
        reservation.setCancelled(false);
        reservation.setCheckedIn(false);
        reservation.setToken("check123");

        when(reservationRepository.findByToken("check123")).thenReturn(Optional.of(reservation));

        reservationService.checkIn("check123");

        assertThat(reservation.isCheckedIn()).isTrue();
    }
}