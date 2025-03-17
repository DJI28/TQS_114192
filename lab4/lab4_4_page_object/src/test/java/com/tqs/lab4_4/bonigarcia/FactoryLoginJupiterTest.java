package com.tqs.lab4_4.bonigarcia;

// Test class using the Page Factory method.

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FactoryLoginJupiterTest {

    FactoryLoginPage login;

    @BeforeEach
    void setup() {
        login = new FactoryLoginPage("edge");
    }

    @AfterEach
    public void teardown() {
        login.quit();
    }

    @Test
    void testLoginSuccess() {
        login.with("user", "user");
        assertThat(login.successBoxPresent()).isTrue();
    }

    @Test
    void testLoginFailure() {
        login.with("bad-user", "bad-password");
        assertThat(login.failureBoxPresent()).isTrue();
    }
}
