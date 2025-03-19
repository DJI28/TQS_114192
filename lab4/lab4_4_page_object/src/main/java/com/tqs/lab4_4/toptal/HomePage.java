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

    @FindBy(xpath="//*[@id=\"navbar-actions-toolbar\"]/li[1]/a")
    private WebElement freelancerApplyButton;

    @FindBy(xpath = "//*[@id=\"nav\"]/div/button")
    private WebElement burgerMenuButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void clickFreelancerApplyButton() throws InterruptedException {
        Thread.sleep(1000); // Necessary to wait for the page to load
        if (freelancerApplyButton.isDisplayed()) {
            freelancerApplyButton.click();
        } else {
            burgerMenuButton.click();
            Thread.sleep(1000); // Necessary to wait for the menu to open
            freelancerApplyButton.click();
        }
    }
}
