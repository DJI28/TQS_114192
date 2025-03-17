package com.tqs.lab4_4.toptal;

// Code has been refactored from the one present in the website to reflect website changes
// DeveloperPortalPage has been removed from the website
// Some fields have been added/removed

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FreelancerApplyPage {
    private WebDriver driver;

    @FindBy(tagName="h1")
    WebElement heading;

    // Cannot make it work in Page Factory, done in "normal" way
    /*
    @FindBy(xpath="//form/div/div/div")
    WebElement freelancer_talent_type;
    */

    @FindBy(id="talent_create_applicant[fullName]")
    WebElement freelancer_full_name;

    @FindBy(id="talent_create_applicant[email]")
    WebElement freelancer_email;

    @FindBy(id="talent_create_applicant[password]")
    WebElement freelancer_password;

    @FindBy(id="talent_create_applicant[passwordConfirmation]")
    WebElement freelancer_password_confirmation;

    @FindBy(xpath="//*[@id=\"talent-apply-form\"]/button/span")
    WebElement join_toptal_button;

    public FreelancerApplyPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /*
    public void setFreelancerTalentType(String talent_type) {
        freelancer_talent_type.click();
    }
     */

    public void setFreelancerFullName(String full_name) {
        freelancer_full_name.clear();
        freelancer_full_name.sendKeys(full_name);
    }

    public void setFreelancerEmail(String email) {
        freelancer_email.clear();
        freelancer_email.sendKeys(email);
    }

    public void setFreelancerPassword(String password) {
        freelancer_password.clear();
        freelancer_password.sendKeys(password);
    }

    public void setFreelancerPasswordConfirmation(String password_confirmation) {
        freelancer_password_confirmation.clear();
        freelancer_password_confirmation.sendKeys(password_confirmation);
    }

    public void clickJoinToptalButton() {
        join_toptal_button.click();
    }

    public boolean isPageOpened() {
        return heading.getText().contains("Apply to Join\n" +
                "the World’s Top Talent Network");
    }
}