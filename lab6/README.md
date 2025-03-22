# Lab 5

## Lab6_1

### Test Containers
Test Containers can be integrated in test cases to prepare ephemeral `Docker` containers required to run integration tests. This is commonly used to preapre a database server with a well-know state. This saves time because we do not need to prepare and initiate a container ourselves and then find the address of that container to use in our tests, instead the TC's do it all by themselves and we simply have to use the `@Testcontainers`, `@Container` and `@DynamicPropertySource`annotations.

### Ordered Tests
Sometimes we need to define the order for our tests to run because some tests may need an entity created on another test, usually tests do not have an order in which they are run. However, we can define the order using `@TestMethodOrder(MethodOrderer.OrderAnnotation.class)` and then `@Order` with a number to define the order.

## Lab6_2

### Migrations
When we start a database for a test it is usually empty, since it was just created, but we may want to make tests were a database has already some information on it. In this case we can use database migrations with `Flyway Migration` (or any other framework with the same effect), to populate the database with information it is simple we just create a file, being careful with the naming scheme and extension (needs to correspond to the database we are using), and then create/update the tables and insert or remove information. And just like this we have data to run out tests on. Migration is also useful because we can then add version control and each version of our code can have an associated migration to it making it easy to go back to an old version and test it. 

## Lab6_3

### Rest Assured
REST Assured is a framework that allows developers to test REST API's, this framework is especially useful in non Spring-Boot projects, since Spring-Boot has other ways of testing API's, but if we are working in a non Spring-Boot project testing an API wouldn't be easy so that is where the Rest Assured framework comes in, it is relativelly easy to use with simple and readble sintax that allows developers to write tests in a fluent, structured, human-friendly way.

## Lab6_4

### Rest Assured with Cars Project
In the previous `Cars` project we used Spring-Boot tools to test our developed `Controller`, but here the objective was to create a new test to the same `Controller` but using REST Assured. This was a relativelly painless and easy process to make all the tests pass.

## Lab6_5

### Cars Project IT Test
In the last exercise we need to implement an Integration Test using what we learned on this Lab (Rest Assured, Test Containers, `Flyway Migrations`), although we already did an IT but it had not Test Containers (we used a previously setup container) and no migration (we started with an empty database) and the API was tested with the Spring-Boot tools, so for this exercise I adapted the test to use all of these frameworks and technologies. This was an easy process and, specially, the Test Containers framework helps hugely in seting up these tests. Migration is also really helpful to have data to test in the tests. Rest Assured is basically the same with what was already being used. One framework I would always use from now on is Test Containers since it really helps on the ease of setup and the fact that I would not need to start (and stop) separate containers.