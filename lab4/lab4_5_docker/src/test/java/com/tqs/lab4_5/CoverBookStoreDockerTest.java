package com.tqs.lab4_5;

// Same as exercise 3 but with the use of a Docker browsers

import io.github.bonigarcia.seljup.BrowserType;
import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.slf4j.Logger;
import java.time.Duration;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@ExtendWith(SeleniumJupiter.class)
@DisplayName("Test from exercise 3 on website CoverBookStore on Docker browsers")
class CoverBookStoreDockerTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    @DisplayName("testBookDemoBefore")
    void testBookDemoBefore(@DockerBrowser(type = BrowserType.FIREFOX) WebDriver driver) {
        driver.get("https://cover-bookstore.onrender.com/");
        driver.manage().window().setSize(new Dimension(1761, 962));
        assertThat(driver.getTitle(), is("Cover - Find your favorite books."));
        driver.findElement(By.cssSelector(".Navbar_searchBarContainer__3UbnF .Navbar_searchBarInput__w8FwI")).click();
        driver.findElement(By.cssSelector(".Navbar_searchBarContainer__3UbnF .Navbar_searchBarInput__w8FwI")).sendKeys("Harry Potter");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2)); // Implicit wait mas o explicit wait é melhor ( necessário para o teste passar )
        driver.findElement(By.cssSelector(".Navbar_searchBarContainer__3UbnF .Navbar_searchBtnIcon__25k0u")).click();
        driver.findElement(By.cssSelector(".SearchList_bookTitle__1wo4a")).click();
    }

    @Test
    @DisplayName("testBookDemoAfter")
    void testBookDemoAfter(@DockerBrowser(type = BrowserType.SAFARI) WebDriver driver) {
        // Explicit wait que vai ser usado para esperar que os vários elementos estejam visíveis
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get("https://cover-bookstore.onrender.com/");
        driver.manage().window().setSize(new Dimension(1761, 962));

        wait.until(ExpectedConditions.titleIs("Cover - Find your favorite books."));
        assertThat(driver.getTitle(), is("Cover - Find your favorite books."));

        // Procurar o input de pesquisa e escrever "Harry Potter"
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='book-search-input']")));
        searchInput.click();
        searchInput.sendKeys("Harry Potter");

        // Procurar o botão de pesquisa e clicar
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".Navbar_searchBarContainer__3UbnF .Navbar_searchBtnIcon__25k0u")));
        searchButton.click();

        // Esperar que os livros apareçam e clicar no livro correto (tolerância de latência de resposta à pesquisa)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='book-search-item']")));
        List<WebElement> books = driver.findElements(By.cssSelector("[data-testid='book-search-item']"));
        // Pode haver vários livros com a mesma classe por isso usamos o título para encontrar o livro correto
        for (WebElement book : books) {
            WebElement title = book.findElement(By.cssSelector(".SearchList_bookTitle__1wo4a"));
            if (title.getText().contains("Harry Potter and the Sorcerer's Stone")) {
                title.click();
                break;
            }
        }
    }
}