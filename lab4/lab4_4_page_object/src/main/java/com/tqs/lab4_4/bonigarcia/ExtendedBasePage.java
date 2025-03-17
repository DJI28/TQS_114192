package com.tqs.lab4_4.bonigarcia;

// This class models the SUT but with a DSL (Domain Specific Language) approach. DSL is a specific language for a particular domain.
// This way test cases use a simple, readable API provided by the page classes. These classes will encapsulate all the calls to the Selenium WebDriver API to interact with the SUT.

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;

public class ExtendedBasePage {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;
    WebDriverWait wait;
    int timeoutSec = 5; // wait timeout (5 seconds by default)

    public ExtendedBasePage(String browser) {
        driver = WebDriverManager.getInstance(browser).create();
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
    }

    public void setTimeoutSec(int timeoutSec) {
        this.timeoutSec = timeoutSec;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Rest of common methods: quit(), visit(URL url), find(By element), etc.

    public void visit(String url) {
        driver.get(url);
    }

    public void click(By element) {
        driver.findElement(element).click();
    }

    public void type(By element, String text) {
        driver.findElement(element).sendKeys(text);
    }

    public boolean isDisplayed(By element) {
        return  driver.findElement(element).isDisplayed();
    }

    public void click (WebElement element) {
        element.click();
    }

    public void type (WebElement element, String text) {
        element.sendKeys(text);
    }

    public boolean isDisplayed (WebElement element) {
        return element.isDisplayed();
    }
}