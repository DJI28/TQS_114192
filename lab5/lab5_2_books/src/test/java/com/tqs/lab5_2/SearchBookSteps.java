package com.tqs.lab5_2;

import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.List;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchBookSteps {
    /*
	create a registered type named iso8601Date to map a string pattern from the feature
	into a custom datatype. Extracted parameters should be strings.
	 */
    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDate iso8601Date(String year, String month, String day){
        return Utils.localDateFromDateParts(year, month, day);
    }


    /**
     * load a data table from the feature (tabular format) and call this method
     * for each row in the table. Injected parameter is a map with column name --> value
     */
    @DataTableType
    public Book bookEntry(Map<String, String> tableEntry){
        return new Book(
                tableEntry.get("title"),
                tableEntry.get("author"),
                Utils.isoTextToLocalDate( tableEntry.get("published") ) );
    }

    private Library library;

    List<Book> books;

    @Given("a library with the following books:")
    public void setupLibrary(List<Book> books){
        library = new Library();
        books.forEach( library::addBook );
    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void searchBooks(LocalDate start, LocalDate end){
        books = library.findBooks(start, end);
    }

    @When("the customer searches for books with title {string}")
    public void searchBooksByTitle(String title){
        books = library.findBooksByTitle(title);
    }

    @When("the customer searches for books written by {string}")
    public void searchBooksByAuthor(String author){
        books = library.findBooksByAuthor(author);
    }

    @Then("{int} books should have been found")
    public void checkBooksFound(int expected){
        assertThat(books.size()).isEqualTo(expected);
    }

    @And("Book {int} should have the title {string}")
    public void checkBookTitle(int index, String title){
        assertThat(books.get(index-1).getTitle()).isEqualTo(title);
    }
}