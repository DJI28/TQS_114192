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


## Lab3_2
## Lab3_3