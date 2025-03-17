package com.tqs.lab4_4.bonigarcia;

// Test using the POM approach that allows for code reusability and maintainability

import static org.assertj.core.api.Assertions.assertThat;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginJupiterTest {

    WebDriver driver;
    LoginPage login;

    @BeforeEach
    void setup() {
        driver = WebDriverManager.edgedriver().create();
        login = new LoginPage(driver);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testLoginSuccess() {
        login.with("user", "user");
        assertThat(login.successBoxPresent()).isTrue();
    }

    @Test
    void testLoginFailure() {
        login.with("bad-user", "bad-password");
        assertThat(login.successBoxPresent()).isFalse();
    }

}