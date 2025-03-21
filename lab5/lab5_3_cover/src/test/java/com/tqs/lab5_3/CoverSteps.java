package com.tqs.lab5_3;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CoverSteps {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @Given("the user navigates to {string}")
    public void setUp(String url) {
        driver = WebDriverManager.edgedriver().create();
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(url);
        assertThat(driver.getTitle()).contains("Cover - Find your favorite books.");
    }

    @When("the user enters {string} into the search bar")
    public void search(String query) {
        // Explicit wait for the search bar to load
        wait.until(d -> d.findElement(By.cssSelector(".Navbar_searchBarContainer__3UbnF .Navbar_searchBarInput__w8FwI")));
        driver.findElement(By.cssSelector(".Navbar_searchBarContainer__3UbnF .Navbar_searchBarInput__w8FwI")).click();
        driver.findElement(By.cssSelector(".Navbar_searchBarContainer__3UbnF .Navbar_searchBarInput__w8FwI")).sendKeys(query);
    }

    @And("the user clicks the search button")
    public void clickSearch() {
        driver.findElement(By.cssSelector(".Navbar_searchBarContainer__3UbnF .Navbar_searchBtnIcon__25k0u")).click();
    }

    @Then("{int} book(s) should have been found")
    public void checkResults(int numResults) {
        // Explicit wait for the search results to appear
        wait.until(d -> d.findElement(By.cssSelector(".SearchList_bookTitle__1wo4a, .GenreBooksPage_bookCardContainer__1aB5_")));
        List<WebElement> books = driver.findElements(By.cssSelector(".SearchList_bookTitle__1wo4a, .GenreBooksPage_bookCardContainer__1aB5_"));
        assertThat(books.size()).isEqualTo(numResults);
    }

    @And("the book {int} title should be {string} written by {string}")
    public void checkBook(int bookIndex, String title, String author) {
        WebElement book = driver.findElements(By.cssSelector(".SearchList_bookTitle__1wo4a, .GenreBooksPage_bookTitle__1OmLm")).get(bookIndex - 1);
        WebElement bookAuthor = driver.findElements(By.cssSelector(".SearchList_bookAuthor__3giPc, .GenreBooksPage_bookAuthor__15rsZ")).get(bookIndex - 1);
        String actualAuthorText = bookAuthor.getText();
        if (actualAuthorText.startsWith("by ")) {
            actualAuthorText = actualAuthorText.substring(3);
        }
        assertThat(book.getText()).isEqualTo(title);
        assertThat(actualAuthorText).isEqualTo(author);
    }

    @When("the user clicks on the {string} category")
    public void clickCategory(String category) {
        driver.findElement(By.xpath("//span[contains(text(), '" + category + "')]")).click();
    }

    @Then("the user clicks on the book's title")
    public void clickBookTitle() {
        // Explicit wait for the book to appear
        wait.until(d -> d.findElement(By.cssSelector(".SearchList_bookTitle__1wo4a")));
        driver.findElement(By.cssSelector(".SearchList_bookTitle__1wo4a")).click();
    }

    @And("the page shows the book's title: {string}, author: {string}, rating: {double}, price: €{int}\\/-, description as well as buy and add to cart buttons")
    public void checkBookStats(String title, String author, double rating, int price) {
        wait.until(d -> d.findElement(By.cssSelector(".BookDetails_bookTitle__1eJ1S")));
        WebElement bookTitle = driver.findElement(By.cssSelector(".BookDetails_bookTitle__1eJ1S"));
        WebElement bookAuthor = driver.findElement(By.cssSelector(".BookDetails_bookAuthor__rz2O7"));
        WebElement bookRating = driver.findElement(By.cssSelector(".BookDetails_ratingVal__23FUM"));
        WebElement bookPrice = driver.findElement(By.cssSelector(".BookDetails_bookCost__3wFZ_"));
        WebElement bookDescription = driver.findElement(By.cssSelector(".BookDetails_bookDesc__x4Qyy"));
        WebElement buyButton = driver.findElement(By.cssSelector(".rippleBtn"));
        WebElement addToCartButton = driver.findElement(By.cssSelector(".rippleBtnBorder"));
        String actualAuthorText = bookAuthor.getText();
        if (actualAuthorText.startsWith("by ")) {
            actualAuthorText = actualAuthorText.substring(3);
        }

        assertThat(bookTitle.getText()).isEqualTo(title);
        assertThat(actualAuthorText).isEqualTo(author);
        assertThat(Double.parseDouble(bookRating.getText())).isEqualTo(rating);
        assertThat(bookPrice.getText()).isEqualTo("€" + price + "/-");
        assertThat(bookDescription.getText()).isNotEmpty();
        assertThat(buyButton.getText()).isEqualTo("Buy Now");
        assertThat(addToCartButton.getText()).isEqualTo("Add to Cart");
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}