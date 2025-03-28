package com.tqs.lab4_4.bonigarcia;

// Simple page object implementation improving maintainability since the page operations are encapsulated in a single class instead of
// having them scattered throughout the test code.

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasicLoginPage {

    WebDriver driver;

    By usernameInput = By.id("username");
    By passwordInput = By.id("password");
    By submitButton = By.cssSelector("button");
    By successBox = By.id("success");
    By failureBox = By.id("invalid");

    public BasicLoginPage(WebDriver driver) {
        this.driver = driver;

        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/login-form.html");
    }

    public void with(String username, String password) {
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(submitButton).click();
    }

    public boolean successBoxPresent() throws InterruptedException {
        Thread.sleep(1000);
        return driver.findElement(successBox).isDisplayed();
    }

    public boolean failureBoxPresent() {
        return driver.findElement(failureBox).isDisplayed();
    }
}