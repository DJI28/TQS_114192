package com.tqs.lab4_4.bonigarcia;

// Testes without POM (Vanilla)

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.edge.EdgeDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class VanillaTests {

    EdgeDriver driver;

    @BeforeEach
    void setup() {
        driver = new EdgeDriver();
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    void testVanillaBasicLogin() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/login-form.html");

        driver.findElement(By.id("username")).sendKeys("user");
        driver.findElement(By.id("password")).sendKeys("user");
        driver.findElement(By.cssSelector("button")).click();

        assertThat(driver.findElement(By.id("success")).isDisplayed()).isTrue();
    }

    @Test
    void testVanillaBasicLoginFailure() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/login-form.html");

        driver.findElement(By.id("username")).sendKeys("bad-user");
        driver.findElement(By.id("password")).sendKeys("bad-password");
        driver.findElement(By.cssSelector("button")).click();

        assertThat(driver.findElement(By.id("invalid")).isDisplayed()).isTrue();
    }
}
