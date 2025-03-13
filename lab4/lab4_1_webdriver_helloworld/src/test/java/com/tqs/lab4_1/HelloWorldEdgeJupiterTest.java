package com.tqs.lab4_1;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.slf4j.Logger;
import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

class HelloWorldEdgeJupiterTest {

    static final Logger log = getLogger(lookup().lookupClass());

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.edgedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new EdgeDriver();
    }

    @Test
    void test() {
        // Exercise
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        // Verify
        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
    }

    @Test
    void testCustom() {
        // Exercise
        String sutUrl = "https://www.youtube.com/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        // Verify
        assertThat(title).isEqualTo("YouTube");
    }

    @Test
    void testCalculator() {
        // Exercise
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);
        driver.findElement(By.linkText("Slow calculator")).click();
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        String currentURL = driver.getCurrentUrl();

        // Verify
        assertThat(currentURL).isEqualTo("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

}

@ExtendWith(SeleniumJupiter.class)
class HelloWorldEdgeSelJupTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    void test(EdgeDriver driver) {
        // Exercise
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        // Verify
        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
    }

}