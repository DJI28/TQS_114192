package com.tqs.lab4_4.toptal;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ApplyAsFreelancerTest {
    WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new EdgeDriver();
    }

    @Test
    public void applyAsFreelancer() throws InterruptedException {
        HomePage home = new HomePage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, java.util.concurrent.TimeUnit.SECONDS);
        home.clickFreelancerApplyButton();

        FreelancerApplyPage applyPage = new FreelancerApplyPage(driver);

        assertThat(applyPage.isPageOpened()).isTrue();

        //applyPage.setFreelancerTalentType("Developer"); // Not able to make this work
        applyPage.setFreelancerFullName("Diogo Domingues Automated Test");
        applyPage.setFreelancerEmail("diogo@ua.pt");
        applyPage.setFreelancerPassword("password123");
        applyPage.setFreelancerPasswordConfirmation("password123");

        // CLick to join is commented out because it would actually submit the form
        // applyPage.clickJoinToptalButton();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

@ExtendWith(SeleniumJupiter.class)
class SelJupFreelancerApplyTest {
    @Test
    public void applyAsFreelancer(EdgeDriver driver) throws  InterruptedException {
        HomePage home = new HomePage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, java.util.concurrent.TimeUnit.SECONDS); //
        home.clickFreelancerApplyButton();

        FreelancerApplyPage applyPage = new FreelancerApplyPage(driver);

        assertThat(applyPage.isPageOpened()).isTrue();

        //applyPage.setFreelancerTalentType("Developer"); // Not able to make this work
        applyPage.setFreelancerFullName("Diogo Domingues Automated Test");
        applyPage.setFreelancerEmail("diogo@ua.pt");
        applyPage.setFreelancerPassword("password123");
        applyPage.setFreelancerPasswordConfirmation("password123");

        // CLick to join is commented out because it would actually submit the form
        // applyPage.clickJoinToptalButton();
    }
}
