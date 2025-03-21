Feature: Browse and Search Books on Cover Bookstore

  Background:
    Given the user navigates to "https://cover-bookstore.onrender.com/"

  Scenario: Search for a book by title
    When the user enters "Harry Potter" into the search bar
    And the user clicks the search button
    Then 1 books should have been found
    And the book 1 title should be "Harry Potter and the Sorcerer's Stone" written by "J.K. Rowling"

  Scenario: Search for a book by author
    When the user enters "Rick Riordan" into the search bar
    And the user clicks the search button
    Then 2 books should have been found
    And the book 1 title should be "The Tower of Nero" written by "Rick Riordan"
    And the book 2 title should be "The Lightning Thief" written by "Rick Riordan"

  Scenario: Filter books by category
    When the user clicks on the "Fiction" category
    Then 3 books should have been found
    And the book 1 title should be "A Game of Thrones" written by "George R.R. Martin"
    And the book 2 title should be "Harry Potter and the Sorcerer's Stone" written by "J.K. Rowling"
    And the book 3 title should be "The Lightning Thief" written by "Rick Riordan"

  Scenario: View details of a specific book
    When the user enters "The Lightning Thief" into the search bar
    And the user clicks the search button
    Then the user clicks on the book's title
    And the page shows the book's title: "The Lightning Thief", author: "Rick Riordan", rating: 4.26, price: €40/-, description as well as buy and add to cart buttons