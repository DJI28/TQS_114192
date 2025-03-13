package com.tqs.lab4_2;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@ExtendWith(SeleniumJupiter.class)
@DisplayName("Test from Selenium IDE on website BlazeDemo converted to JUnit Jupiter using Selenium-Jupiter")
class EdgeSeleniumIDEBlazeDemoTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    @DisplayName("testBlazeDemo")
    void testBlazeDemo(EdgeDriver driver) {
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(1761, 962));
        assertThat(driver.getTitle(), is("BlazeDemo"));
        driver.findElement(By.cssSelector("form")).click();
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = 'Philadelphia']")).click();
        }
        {
            WebElement element = driver.findElement(By.name("fromPort"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.name("fromPort"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.name("fromPort"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = 'Dublin']")).click();
        }
        {
            WebElement element = driver.findElement(By.name("toPort"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.name("toPort"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.name("toPort"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertThat(driver.getTitle(), is("BlazeDemo - reserve"));
        driver.findElement(By.cssSelector("tr:nth-child(2) .btn")).click();
        assertThat(driver.getTitle(), is("BlazeDemo Purchase"));
        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys("First Last");
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys("123 Main St.");
        driver.findElement(By.id("city")).click();
        driver.findElement(By.id("city")).sendKeys("Anytown");
        driver.findElement(By.id("state")).click();
        driver.findElement(By.id("state")).sendKeys("State");
        driver.findElement(By.id("zipCode")).click();
        driver.findElement(By.id("zipCode")).sendKeys("12345");
        driver.findElement(By.id("creditCardNumber")).click();
        driver.findElement(By.id("creditCardNumber")).sendKeys("123456789");
        driver.findElement(By.id("creditCardYear")).click();
        driver.findElement(By.id("creditCardYear")).sendKeys("2027");
        driver.findElement(By.id("nameOnCard")).click();
        driver.findElement(By.id("nameOnCard")).sendKeys("John Smith");
        driver.findElement(By.cssSelector(".checkbox")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
        driver.findElement(By.cssSelector(".hero-unit")).click();
        driver.findElement(By.cssSelector(".hero-unit")).click();
        assertThat(driver.getTitle(), is("BlazeDemo Confirmation"));
    }
}