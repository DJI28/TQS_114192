# Lab 3

## Lab3_1

### a) Identify a couple of examples that use AssertJ expressive methods chaining.
AssertJ expressive methods chaining is combining various assertions in a sequencial and fluid way. We can identify AssertJ expressive methods chaining in test `A_EmployeeRepositoryTest` in methods `givenSetOfEmployees_whenFindAll_thenReturnAllEmployees` and `testFindEmplyeedByOrganizationDomain`.

### b) Take note of transitive annotations included in `@DataJpaTest`.
The transitive anotations included in @DataJpaTest are: `@Target({ElementType. TYPE})`, `@Retention(RetentionPolicy. RUNTIME)`, `@Documented`, `@Inherited`, `@BootstrapWith(org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTestContextBootstrapper.class)`, `@ExtendWith({org.springframework.test.context.junit.jupiter.SpringExtension.class})`, `@OverrideAutoConfiguration(enabled = false)`, `@TypeExcludeFilters({org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTypeExcludeFilter.class})`, `@Transactional`, `@AutoConfigureCache`, `@AutoConfigureDataJpa`, `@AutoConfigureTestDatabase`, `@AutoConfigureTestEntityManager`, `@ImportAutoConfiguration`. These serve the purpose of facilitating the configuration and execution of isolated tests on JPA repositories and may serve different purposes, the first four define the behavior and scope of `@DataJpaTest`, the next four are Spring Boot configuration annotations and the rest are test-specific configuration annotations. 

### c) Identify an example in which you mock the behavior of the repository (and avoid involving a database).
We can see a mock of the behavior of the repository in test `B_EmployeeService_UnitTest`. This is done because this test class can be run only on unit tests which are meant to be fast and launching a database can be slow, so to keep the tests fast we just mock the repository responses. 

### d) What is the difference between standard @Mock and @MockBean?
Both `@Mock` and `@MockBean` are meant to mock dependencies in testing classes, but they serve different purposes and are used in different testing contexts. `@Mock` annotation comes from the Mockito library and it is used to create isolated mock objects but it does not integrate with Spring context and is used where the Spring application context is not needed, so it is used mostly with JUnit5 with Mockito annotations.
`@MockBean` is a Spring Boot-specific annotation that creates a mock bean in the Spring application context and will replace the actual bean in the Spring context with a mock, it is used in integration tests where the Spring container is loaded and will ensure that other Spring beans dependent on the mocked bean still work.

So `@Mock` should be used when writing unit tests without Spring Boot and `@MockBean`should be used when testing a Spring component inside the application context.

### e) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?
The “application-integrationtest.properties” file is a Spring Boot properties file specifically used for integration tests (IT). It provides test-specific configurations (i.e customizes configurations for IT) that override the default properties of the application, usually in application.properties or application.yml, to ensure a controlled test environment, this will help ensure test isolation by setting up dedicated configurations that do not affect the real application. This file will be used when we are executing tests with `@SpringBootTest` annotation or something similar.

This is particularly helpful to keep different configurations for production and test environments, preventing unwanted modifications to the real database or external services and ensures improves test stability by ensuring a predictable (testing) environment.

### f) the sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?
Test C is mainly to test the Controller part of the API, loading only the web layer of the Spring Boot with the annotaion `@WebMvcTest(EmployeeRestController.class)` using `@MockBean` to mock the service needed for the controller to work and `MockMvc` to simulate HTTP requests and verify the responses, thus no database connection will happen because service and repository layers are mocked. This test is used to ensure the correctness of the controller logic and request-response logic handling without involving the service or database layers making it perfect when fast, isolated controller tests are needed to verify logic without worrying about service and database interactions.

Test D tests from the boudary to the repository layer this will thus load all the Spring enviroment, `@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = EmployeeMngrApplication.class)`. `MockMvc` will stil be enabled by `@AutoConfigureMockMvc` and is used to simulate API calls and check the responses. This test is using an in-memory database but can be configured to use a real one. The purpose of this test is to verify that the multiple components that make the Spring Boot context (controller, service and repository) interact correcly, is used when controller-service-repository logic need testing but not with real HTTP requests.

Test E is used to test all of the Spring Boot application with no mocking and simulating real HTTP client interactions wuth the REST API, to start the full Spring Boot context on a random port `@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)` is used. `TestRestTemplate` will be used to send real HTTP requests to the application and an actual database will be used (configured by `@TestPropertySource`). This test tests the complete API as a real client would including request serialization, response deserialization and HTTP behaviour.

## Lab3_2

### TDD Aproach
In this exercise I used a TDD aproach from top to bottom, i.e, starting from the contoller and only developing this module and testing it, then the service and then the repository as indicated in the PDF file.

## Lab3_3

### IT Test
To make the integration test I used the test in the suggestion, adapting just the classes and ading more tests to test every endpoint. A `MySQL` server in docker was used to store the data.

### c) What could be the advantages and disadvantages of using a real database connection in testing activities?
Using a real database in testing activities can be useful for the following reasons:
- Realistic Testing Enviroment `->` A real database will ensure tests are closer to real-world scenarios, reducing discrepancies between test and production enviroments;
- Detect Schema and Query Issues `->` Helps identify issues related to database schemas, constraints, migrations and some queries that might not be detected in mocked or in-memory databases;
- Validates ORM (Object Relational Mapper) and Repository Logic `->` Ensures that the persistence layer (JPA, Hibernate, etc) are working correctly including relationships, transactions and cascading effects;
- Ensures Data Integrity `->` Can catch issues related to indexing, constraints (e.g, unique keys, foreign keys), and data persistence that can be missed in mocks; 
- Prevents Over-Mocking `->` Some database-related logic cannot be effectively tested with mocks, making real databse tests more reliable.

---

However, testing with real databases can also have disadvantages such as:
- Slower Execution `->` Connecting to a real database can take time as well as database operations (queries, inserts, updates) take more time than using an in-memory alternative or mocking;
- Complex Setup and Maintenance `->` Since testing with a real database will require a properly configured database, which might need additional setup with Docker, scripts or test migrations;
- Risk of Data Contamination `->` If not properly isolated, tests may leave residual data, causing inconsistent results between test runs;
- Concurrency Issues `->` Running multiple tests that modify the database can introduce concurrency issues and flaky test behavior;
- External Dependencies `->` If the database is down or misconfigured, tests may fail to infrastructure issues rather than actual code problems.

---

Real databases should be used for integration tests, where repository behavior, constaints or real queries need validation;  performace testing to measure query execution time and optimize indexes and end-to-end testgin when verifying the full stack with real data. And should be avoided for unit tests where mocking is sufficient and fast execution is crucial (in the milliseconds range) and for CI/CD (Continous Integration Continous Development) unless using a lighweight, dedicated test database (e.g, Dockerized MySQL (as used in the exercise) or PostgreSQL).