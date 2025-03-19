package com.tqs.lab4_4.blazedemo;

// Same test as exercise 2 but way simpler due to the use of the Factory pattern

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BlazeDemoTest {

    BlazeDemoFactoryPage blazeDemoFactoryPage;

    @BeforeEach
    void setup() {
        blazeDemoFactoryPage = new BlazeDemoFactoryPage("edge");
    }

    @AfterEach
    public void teardown() {
        blazeDemoFactoryPage.quit();
    }

    @Test
    void blazeDemoTest() throws InterruptedException {
        assertThat(blazeDemoFactoryPage.driver.getTitle()).isEqualTo("BlazeDemo");
        blazeDemoFactoryPage.changeFromPort("Philadelphia");
        blazeDemoFactoryPage.changeToPort("Dublin");
        blazeDemoFactoryPage.click(blazeDemoFactoryPage.nextPageButton);
        assertThat(blazeDemoFactoryPage.driver.getTitle()).isEqualTo("BlazeDemo - reserve");
        blazeDemoFactoryPage.click(blazeDemoFactoryPage.chooseCompanyButton);
        blazeDemoFactoryPage.fillInformation("First Last", "123 Main St", "Anytown", "State", "12345", "123456789", "2027", "John Smith");
        assertThat(blazeDemoFactoryPage.driver.getTitle()).isEqualTo("BlazeDemo Purchase");
        blazeDemoFactoryPage.click(blazeDemoFactoryPage.rememberMeCheckbox);
        blazeDemoFactoryPage.click(blazeDemoFactoryPage.purchaseFlightButton);
        blazeDemoFactoryPage.click(blazeDemoFactoryPage.heroUnit);
        blazeDemoFactoryPage.click(blazeDemoFactoryPage.heroUnit);
        assertThat(blazeDemoFactoryPage.driver.getTitle()).isEqualTo("BlazeDemo Confirmation");
    }
}
