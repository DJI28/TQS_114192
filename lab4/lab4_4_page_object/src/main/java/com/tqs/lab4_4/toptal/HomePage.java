package com.tqs.lab4_4.toptal;

// Code has been refactored from the one present in the website to reflect website changes
// Only the button text changed

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    private final static String PAGE_URL = "https://www.toptal.com/";

    @FindBy(xpath="//ul[@id='navbar-actions-toolbar']/li/a")
    private WebElement freelancerApplyButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void clickFreelancerApplyButton() throws InterruptedException {
        freelancerApplyButton.click();
    }
}
