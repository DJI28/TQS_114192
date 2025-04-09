package com.tqs.hw1.services;

import com.tqs.hw1.dtos.ReservationRequestDTO;
import com.tqs.hw1.dtos.ReservationResponseDTO;
import com.tqs.hw1.entities.Meal;
import com.tqs.hw1.entities.MealType;
import com.tqs.hw1.entities.Restaurant;
import com.tqs.hw1.entities.Reservation;
import com.tqs.hw1.repositories.MealRepository;
import com.tqs.hw1.repositories.ReservationRepository;
import com.tqs.hw1.repositories.RestaurantRepository;
import com.tqs.hw1.weather.WeatherForecast;
import com.tqs.hw1.utils.DateValidator;
import com.tqs.hw1.utils.ReservationConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit Tests - Reservation Service")
class ReservationTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private MealRepository mealRepository;
    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    @DisplayName("Should create a valid reservation successfully")
    void testCreateValidReservation() {
        Restaurant restaurant = new Restaurant(1L, "Cantina", 1);
        Meal meal = new Meal(1L, "Meal 1",LocalDate.now(), MealType.LUNCH, restaurant);
        ReservationRequestDTO request = new ReservationRequestDTO(1L, meal.getDate(), meal.getType());

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(mealRepository.findByRestaurantAndDateAndType(restaurant, meal.getDate(), meal.getType())).thenReturn(Optional.of(meal));
        when(reservationRepository.countByRestaurantAndDateAndTypeAndCancelledFalse(restaurant, meal.getDate(), meal.getType())).thenReturn(0);

        ReservationResponseDTO response = reservationService.createReservation(request);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isNotBlank();
        assertThat(response.getType()).isEqualTo(MealType.LUNCH);
    }

    @Test
    @DisplayName("Should fail when trying to reserve on a day without service")
    void testCreateReservation_RestaurantClosed() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(new Restaurant()));
        when(mealRepository.findByRestaurantAndDateAndType(any(), any(), any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationService.createReservation(new ReservationRequestDTO(1L, LocalDate.now(), MealType.LUNCH)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("No meal available");
    }

    @Test
    @DisplayName("Should fail when restaurant capacity is full")
    void testCreateReservation_CapacityFull() {
        Restaurant restaurant = new Restaurant(1L, "Cantina", 1);
        LocalDate date = LocalDate.now();
        Meal meal = new Meal(1L, "Meal 1", date, MealType.LUNCH, restaurant);

        ReservationRequestDTO request = new ReservationRequestDTO(1L, date, MealType.LUNCH);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(mealRepository.findByRestaurantAndDateAndType(restaurant, date, MealType.LUNCH)).thenReturn(Optional.of(meal));
        when(reservationRepository.countByRestaurantAndDateAndTypeAndCancelledFalse(restaurant, date, MealType.LUNCH)).thenReturn(1);

        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("full");
    }

    @Test
    @DisplayName("Should cancel a reservation successfully")
    void testCancelReservation_Success() {
        Reservation reservation = new Reservation();
        reservation.setToken("abc123");
        reservation.setCancelled(false);

        when(reservationRepository.findByToken("abc123")).thenReturn(Optional.of(reservation));

        reservationService.cancelReservation("abc123");

        assertThat(reservation.isCancelled()).isTrue();
        verify(reservationRepository).save(reservation);
    }

    @Test
    @DisplayName("Should mark reservation as checked-in successfully")
    void testCheckInReservation_Success() {
        Reservation reservation = new Reservation();
        reservation.setToken("abc123");
        reservation.setCancelled(false);
        reservation.setCheckedIn(false);

        when(reservationRepository.findByToken("abc123")).thenReturn(Optional.of(reservation));

        reservationService.checkIn("abc123");

        assertThat(reservation.isCheckedIn()).isTrue();
        verify(reservationRepository).save(reservation);
    }

    @Test
    @DisplayName("Should fail when checking in an already used reservation")
    void testCheckInReservation_AlreadyUsed() {
        Reservation reservation = new Reservation();
        reservation.setToken("abc123");
        reservation.setCancelled(false);
        reservation.setCheckedIn(true);

        when(reservationRepository.findByToken("abc123")).thenReturn(Optional.of(reservation));

        assertThatThrownBy(() -> reservationService.checkIn("abc123"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Already checked-in");
    }

    @Test
    @DisplayName("Should fail when checking in a cancelled reservation")
    void testCheckInReservation_Cancelled() {
        Reservation reservation = new Reservation();
        reservation.setToken("abc123");
        reservation.setCancelled(true);

        when(reservationRepository.findByToken("abc123")).thenReturn(Optional.of(reservation));

        assertThatThrownBy(() -> reservationService.checkIn("abc123"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("cancelled");
    }

    @Test
    @DisplayName("Should fail when checking in with an invalid token")
    void testCheckInReservation_InvalidToken() {
        when(reservationRepository.findByToken("invalid")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationService.checkIn("invalid"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Token not found");
    }

    @Test
    @DisplayName("Should retrieve reservation by valid token")
    void testGetReservationByToken_Success() {
        Reservation reservation = new Reservation();
        reservation.setToken("abc123");
        reservation.setType(MealType.LUNCH);
        reservation.setCancelled(false);

        when(reservationRepository.findByToken("abc123")).thenReturn(Optional.of(reservation));

        ReservationResponseDTO response = reservationService.getReservation("abc123");

        assertThat(response.getToken()).isEqualTo("abc123");
        assertThat(response.getType()).isEqualTo(MealType.LUNCH);
    }

    @Test
    @DisplayName("Should fail when retrieving reservation with invalid token")
    void testGetReservationByToken_NotFound() {
        when(reservationRepository.findByToken("invalid")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationService.getReservation("invalid"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Token not found");
    }

    @Test
    @DisplayName("Should retrieve all reservations")
    void testGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(List.of(new Reservation(), new Reservation()));
        List<ReservationResponseDTO> reservations = reservationService.getAllReservations();
        assertThat(reservations).hasSize(2);
    }
}

@DisplayName("Unit Tests - Utils")
class UtilsTest {

    @Test
    @DisplayName("Date validator should accept valid dates")
    void testDateValidator_ValidDate() {
        boolean result = DateValidator.isValid("2023-10-01");
        assertThat(result).isTrue();
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
        reservation.setType(MealType.DINNER);
        ReservationResponseDTO dto = ReservationConverter.toDTO(reservation);

        assertThat(dto).isNotNull();
        assertThat(dto.getToken()).isEqualTo("abc123");
        assertThat(dto.getType()).isEqualTo(MealType.DINNER);
    }
}

@DisplayName("Unit Tests - Weather Service")
@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherService weatherService;

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
    @DisplayName("Should return cached weather forecast if TTL not expired")
    void testWeatherCache_ReturnsCachedForecast() {
        LocalDate today = LocalDate.now();
        WeatherForecast cached = new WeatherForecast("sunny", 20);

        when(weatherService.getForecast("Aveiro", today)).thenReturn(cached);

        WeatherForecast forecast1 = weatherService.getForecast("Aveiro", today);
        WeatherForecast forecast2 = weatherService.getForecast("Aveiro", today);

        assertThat(forecast1).isEqualTo(forecast2);
        verify(weatherService, times(2)).getForecast("Aveiro", today);
    }

    @Test
    @DisplayName("Should expire cache correctly after TTL is reached")
    void testWeatherCache_ExpiresCorrectly() {
        LocalDate today = LocalDate.now();
        WeatherForecast oldForecast = new WeatherForecast("sunny", 20);
        WeatherForecast newForecast = new WeatherForecast("rain", 15);

        when(weatherService.getForecast("Aveiro", today))
                .thenReturn(oldForecast)
                .thenReturn(newForecast);

        WeatherForecast firstCall = weatherService.getForecast("Aveiro", today);
        WeatherForecast secondCall = weatherService.getForecast("Aveiro", today);

        assertThat(firstCall).isNotEqualTo(secondCall);
        assertThat(firstCall.getDescription()).isEqualTo("sunny");
        assertThat(secondCall.getDescription()).isEqualTo("rain");

        verify(weatherService, times(2)).getForecast("Aveiro", today);
    }
}

@DisplayName("Unit Tests - Meal Service")
@ExtendWith(MockitoExtension.class)
class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealService mealService;

    @Test
    @DisplayName("Should save new meal")
    void testAddMeal() {
        Meal meal = new Meal();
        when(mealRepository.save(meal)).thenReturn(meal);

        Meal saved = mealService.addMeal(meal);

        assertThat(saved).isEqualTo(meal);
        verify(mealRepository).save(meal);
    }

    @Test
    @DisplayName("Should update existing meal")
    void testUpdateMeal() {
        Meal original = new Meal(1L, "Meal 1",LocalDate.now(), MealType.LUNCH, new Restaurant(1L, "Cantina", 100));
        Meal update = new Meal(null, "Meal 2",LocalDate.now().plusDays(1), MealType.DINNER, new Restaurant(2L, "Bar", 50));

        when(mealRepository.findById(1L)).thenReturn(Optional.of(original));
        when(mealRepository.save(any(Meal.class))).thenAnswer(inv -> inv.getArgument(0));

        Meal result = mealService.updateMeal(1L, update);

        assertThat(result.getDate()).isEqualTo(update.getDate());
        assertThat(result.getType()).isEqualTo(update.getType());
        assertThat(result.getRestaurant().getName()).isEqualTo("Bar");
    }

    @Test
    @DisplayName("Should delete meal by ID")
    void testDeleteMeal() {
        mealService.deleteMeal(1L);
        verify(mealRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should return all meals")
    void testGetAllMeals() {
        List<Meal> meals = List.of(new Meal(), new Meal());
        when(mealRepository.findAll()).thenReturn(meals);

        List<Meal> result = mealService.getAllMeals();
        assertThat(result).hasSize(2);
    }
}

@DisplayName("Unit Tests - Restaurant Service")
@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    @DisplayName("Should save new restaurant")
    void testAddRestaurant() {
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        Restaurant saved = restaurantService.addRestaurant(restaurant);

        assertThat(saved).isEqualTo(restaurant);
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    @DisplayName("Should update existing restaurant")
    void testUpdateRestaurant() {
        Restaurant existing = new Restaurant(1L, "Cantina", 20);
        Restaurant update = new Restaurant(null, "Bar", 50);
        update.setId(1L);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(restaurantRepository.save(any(Restaurant.class))).thenAnswer(inv -> inv.getArgument(0));

        Restaurant result = restaurantService.updateRestaurant(update.getId(), update);

        assertThat(result.getName()).isEqualTo(update.getName());
        assertThat(result.getCapacity()).isEqualTo(update.getCapacity());
    }

    @Test
    @DisplayName("Should delete restaurant by ID")
    void testDeleteRestaurant() {
        restaurantService.deleteRestaurant(1L);
        verify(restaurantRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should return all restaurants")
    void testGetAllRestaurants() {
        List<Restaurant> restaurants = List.of(new Restaurant(), new Restaurant());
        when(restaurantRepository.findAll()).thenReturn(restaurants);

        List<Restaurant> result = restaurantService.getAllRestaurants();
        assertThat(result).hasSize(2);
    }
}