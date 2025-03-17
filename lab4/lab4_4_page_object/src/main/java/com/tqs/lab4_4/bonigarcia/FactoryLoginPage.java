package com.tqs.lab4_4.bonigarcia;

// Page Factory the name given to several support classes provided by the Selenium WebDriver API to ease the implementation of page object classes.
// In the class we will initiate the elements of the page using the @FindBy annotation making it different from the other implementations of POM.

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FactoryLoginPage extends ExtendedBasePage {

    @FindBy(id = "username")
    @CacheLookup
    WebElement usernameInput;

    @FindBy(id = "password")
    @CacheLookup
    WebElement passwordInput;

    @FindBy(css = "button")
    @CacheLookup
    WebElement submitButton;

    @FindBy(id = "success")
    @CacheLookup
    WebElement successBox;

    @FindBy(id = "invalid")
    @CacheLookup
    WebElement failureBox;

    public FactoryLoginPage(String browser, int timeoutSec) {
        this(browser);
        setTimeoutSec(timeoutSec);
    }

    public FactoryLoginPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");
    }


    // Same logic to the page class without using the page factory

    public void with(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(submitButton);
    }

    public boolean successBoxPresent() {
        return isDisplayed(successBox);
    }

    public boolean failureBoxPresent() {
        return isDisplayed(failureBox);
    }
}
