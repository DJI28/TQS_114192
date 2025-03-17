package com.tqs.lab4_4.bonigarcia;

// Test using the most simple implementation of POM.

import static org.assertj.core.api.Assertions.assertThat;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;


class BasicLoginJupiterTest {

    WebDriver driver;
    BasicLoginPage login;

    @BeforeEach
    void setup() {
        driver = WebDriverManager.edgedriver().create();
        login = new BasicLoginPage(driver);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testBasicLoginSuccess() {
        login.with("user", "user");
        assertThat(login.successBoxPresent()).isTrue();
    }

    @Test
    void testBasicLoginFailure() {
        login.with("bad-user", "bad-password");
        assertThat(login.failureBoxPresent()).isTrue();
    }
}