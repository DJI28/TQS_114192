package com.tqs.hw1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Functional Tests - MealBooking Frontend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class D_SeleniumWebDriver {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://localhost:5173/");
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Should load homepage and show restaurant list")
    void testLoadHomePageAndShowRestaurants() {
        WebElement title = driver.findElement(By.id("restaurants-title"));
        assertThat(title.getText()).containsIgnoringCase("Available Restaurants");

        WebElement restaurantList = driver.findElement(By.id("restaurant-list"));
        assertThat(restaurantList.findElements(By.tagName("li"))).isNotEmpty();
    }

    @Test
    @Order(2)
    @DisplayName("Should click on a restaurant and load meals")
    void testViewMealsInRestaurantPage() {
        WebElement firstRestaurant = wait.until(driver ->
                driver.findElement(By.cssSelector("#restaurant-list li"))
        );

        firstRestaurant.click();
        WebElement mealsTitle = wait.until(driver ->
                driver.findElement(By.id("restaurant-meals-title"))
        );
        assertThat(mealsTitle.getText()).contains("Meals");
    }

    @Test
    @Order(3)
    @DisplayName("Should open cancel modal from navbar")
    void testOpenCancelModal() {
        WebElement cancelButton = driver.findElement(By.id("cancel-btn"));
        cancelButton.click();

        WebElement modal = driver.findElement(By.id("cancel-modal"));
        assertThat(modal.isDisplayed()).isTrue();
    }

    @Test
    @Order(4)
    @DisplayName("Should open check-in modal from navbar")
    void testOpenCheckInModal() {
        WebElement checkinButton = driver.findElement(By.id("checkin-btn"));
        checkinButton.click();

        WebElement modal = driver.findElement(By.id("checkin-modal"));
        assertThat(modal.isDisplayed()).isTrue();
    }

    @Test
    @Order(5)
    @DisplayName("Should reserve a meal and check-in using alert token")
    void testReserveAndCheckInWithTokenFromAlert() {
        WebElement firstRestaurant = wait.until(d -> d.findElement(By.cssSelector("#restaurant-list li")));
        firstRestaurant.click();

        WebElement reserveButton = wait.until(d -> d.findElement(By.cssSelector("button[id^='reserve-btn-']")));
        reserveButton.click();

        Alert alert = wait.until(d -> {
            try {
                return d.switchTo().alert();
            } catch (NoAlertPresentException e) {
                return null;
            }
        });

        String alertText = alert.getText();
        assertThat(alertText).contains("Token:");
        String token = alertText.split("Token:")[1].trim();
        alert.accept();

        WebElement checkInButton = driver.findElement(By.id("checkin-btn"));
        checkInButton.click();

        WebElement input = wait.until(d -> d.findElement(By.id("checkin-token-input")));
        input.sendKeys(token);

        WebElement confirmBtn = driver.findElement(By.xpath("//div[@id='checkin-modal']//button[text()='Confirm']"));
        confirmBtn.click();

        WebElement toast = wait.until(d -> d.findElement(By.id("success-toast")));
        assertThat(toast.getText()).containsIgnoringCase("Check-in successful");
    }

    @Test
    @Order(6)
    @DisplayName("Should reserve a meal and cancel using alert token")
    void testReserveAndCancelWithTokenFromAlert() {
        WebElement firstRestaurant = wait.until(d -> d.findElement(By.cssSelector("#restaurant-list li")));
        firstRestaurant.click();

        WebElement reserveButton = wait.until(d -> d.findElement(By.cssSelector("button[id^='reserve-btn-']")));
        reserveButton.click();

        Alert alert = wait.until(d -> {
            try {
                return d.switchTo().alert();
            } catch (NoAlertPresentException e) {
                return null;
            }
        });

        String alertText = alert.getText();
        assertThat(alertText).contains("Token:");
        String token = alertText.split("Token:")[1].trim();
        alert.accept();

        WebElement cancelButton = driver.findElement(By.id("cancel-btn"));
        cancelButton.click();

        WebElement input = wait.until(d -> d.findElement(By.id("cancel-token-input")));
        input.sendKeys(token);

        WebElement confirmBtn = driver.findElement(By.xpath("//div[@id='cancel-modal']//button[text()='Confirm']"));
        confirmBtn.click();

        WebElement toast = wait.until(d -> d.findElement(By.id("success-toast")));
        assertThat(toast.getText()).containsIgnoringCase("cancelled");
    }
}
