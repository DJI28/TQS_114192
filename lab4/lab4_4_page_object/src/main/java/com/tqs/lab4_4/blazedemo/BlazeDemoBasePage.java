package com.tqs.lab4_4.blazedemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class BlazeDemoBasePage {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;
    WebDriverWait wait;
    int timeoutSec = 5; // wait timeout (5 seconds by default)

    public BlazeDemoBasePage(String browser) {
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

    public void visit(String url) {
        driver.get(url);
    }

    public void click (WebElement element) throws InterruptedException {
        element.click();
        Thread.sleep(1000);
    }

    public void type (WebElement element, String text) {
        element.sendKeys(text);
    }

    public void selectOption(WebElement dropdown, String optionToFind) {
        dropdown.findElement(By.xpath("//option[. = '" + optionToFind + "']")).click();
        Actions builder = new Actions(driver);
        builder.moveToElement(dropdown).clickAndHold().perform();
        builder.moveToElement(dropdown).perform();
        builder.moveToElement(dropdown).release().perform();
    }
}