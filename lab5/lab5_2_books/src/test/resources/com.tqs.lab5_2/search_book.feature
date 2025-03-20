# Not using data tables
#Feature: Book search
#  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

#  Scenario: Search books by publication year
#    Given a book with the title 'One good book', written by 'Anonymous', published in 14 March 2013
#      And another book with the title 'Some other book', written by 'Tim Tomson', published in 23 August 2014
#      And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 01 January 2012
#    When the customer searches for books published between 2013-03-01 and 2014-09-25
#    Then 2 books should have been found
#      And Book 1 should have the title 'Some other book'
#      And Book 2 should have the title 'One good book'

#    Scenario Search books by author
#    Given a book with the title 'One good book', written by 'Anonymous', published in 14 March 2013
#      And another book with the title 'Some other book', written by 'Tim Tomson', published in 23 August 2014
#      And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 01 January 2012
#      And another book with the title 'How to cook a dino', written by 'Tim Tomson', published in 01 January 2012
#    When the customer searches for books written by 'Tim Tomson'
#    Then 2 books should have been found
#      And Book 1 should have the title 'Some other book'
#      And Book 2 should have the title 'How to cook a dino'

Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

  Scenario: Search books by publication year
    Given a library with the following books:
      | title           | author        | publication_date |
      | One good book   | Anonymous     | 2013-03-14      |
      | Some other book | Tim Tomson    | 2014-08-23      |
      | How to cook a dino | Fred Flintstone | 2012-01-01 |
    When the customer searches for books published between 2013-03-01 and 2014-09-25
    Then 2 books should have been found
      And Book 1 should have the title 'Some other book'
      And Book 2 should have the title 'One good book'
# falta este
  Scenario Search books by author
    Given I have the following books in the library
      | title           | author        | publication_date |
      | One good book   | Anonymous     | 2013-03-14      |
      | Some other book | Tim Tomson    | 2014-08-23      |
      | How to cook a dino | Fred Flintstone | 2012-01-01 |
      | How to speak with a dino | Tim Tomson    | 2012-01-01 |
    Then 2 books should have been found
    And Book 1 should have the title 'Some other book'
    And Book 2 should have the title 'How to speak with a dino'