package com.tqs.lab4_4.blazedemo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BlazeDemoFactoryPage extends BlazeDemoBasePage {

    @FindBy(css="form")
    @CacheLookup
    WebElement form;

    @FindBy(name="fromPort")
    @CacheLookup
    WebElement fromPort;

    @FindBy(name="toPort")
    @CacheLookup
    WebElement toPort;

    @FindBy(css=".btn-primary")
    @CacheLookup
    WebElement nextPageButton;

    @FindBy(css="tr:nth-child(2) .btn")
    @CacheLookup
    WebElement chooseCompanyButton;

    @FindBy(id="inputName")
    @CacheLookup
    WebElement usernameInput;

    @FindBy(id="address")
    @CacheLookup
    WebElement addressInput;

    @FindBy(id="city")
    @CacheLookup
    WebElement cityInput;

    @FindBy(id="state")
    @CacheLookup
    WebElement stateInput;

    @FindBy(id="zipCode")
    @CacheLookup
    WebElement zipCodeInput;

    @FindBy(id="creditCardNumber")
    @CacheLookup
    WebElement creditCardNumberInput;

    @FindBy(id="creditCardYear")
    @CacheLookup
    WebElement creditCardYearInput;

    @FindBy(id="nameOnCard")
    @CacheLookup
    WebElement nameOnCardInput;

    @FindBy(css=".checkbox")
    @CacheLookup
    WebElement rememberMeCheckbox;

    @FindBy(css=".btn-primary")
    @CacheLookup
    WebElement purchaseFlightButton;

    @FindBy(css=".hero-unit")
    @CacheLookup
    WebElement heroUnit;

    public BlazeDemoFactoryPage(String browser, int timeoutSec) {
        this(browser);
        setTimeoutSec(timeoutSec);
    }

    public BlazeDemoFactoryPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://blazedemo.com/");
    }


    // Same logic to the page class without using the page factory

    public void changeFromPort(String fromPort) {
        selectOption(this.fromPort, fromPort);
    }

    public void changeToPort(String toPort) {
        selectOption(this.toPort, toPort);
    }

    public void fillInformation(String name, String address, String city, String state, String zipCode, String creditCardNumber, String creditCardYear, String nameOnCard) throws InterruptedException {
        type(usernameInput, name);
        type(addressInput, address);
        type(cityInput, city);
        type(stateInput, state);
        type(zipCodeInput, zipCode);
        type(creditCardNumberInput, creditCardNumber);
        type(creditCardYearInput, creditCardYear);
        type(nameOnCardInput, nameOnCard);
    }
}
