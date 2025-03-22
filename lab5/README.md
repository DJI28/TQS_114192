# Lab 5

## Lab5_1

### Behaviour Driven Development (BDD)
BDD is a development technique that promotes collaboration between developers, QA, non-technical stakeholders, and the business team. It aligns with the concepts of verification and validation.

### Cucumber and Gherkin
`Cucumber` is a testing tool used for BDD. It allows developers, testers and even non-technical team members to write test scenarios in plain language that everybody can understand. These test scenarios are written using a language called `Gherkin` (with `Given`, `When`, `Then` steps), said scenarios will be read by `Cucumber` and executes them against the software to check if it behaves as expected.
These tools can be particularly useful to test User Stories acceptance criteria that are usually written with `Given`, `When`, `Then`, making them very interesting for developers to use.

### Implementation
To get started with these tools we started by developing tests for a Reverse Polish Notation Calculator, we made tests for basic arithmetic operations. We started by writing the diferent scenarios we wanted to test and then we used annotations on `Java` such as `@Given`, `@When` and `@Then` to develop the test for each step.

## Lab5_2

### Implementation
In these exercise we used two different approaches from the last one:
- ParameterType Configuration -> This allows us to define a new parameter type in the test code to use in the mathing expressions (deafaults: {string}, {int}, etc), with this configuration we can define paremters like {iso8601Date} and others.
- DataTable Mapping -> In Gherkin we can write tabular data to avoid cluttering the test scenarios when adding multiple entities of something (in our case books), then we simply write the mapping in the test code to convert these tables to usable objects that can then be used in testting, and avoiding hardcoding values. We should be careful and give the same name to the input data and the mapping in the code otherwise we get an error code.

## Lab5_3

### Cucumber and Selenium WebDriver
In the first two exercises we used `Cucumber` primarily to test the backend logic of our application. However, `Cucumber` can also be effectively combined with `Selenium WebDriver` to perform automated frontend testing allowing us to simulate user interactions within a web browser. 
With this setup, we can write `Gherkin` scenarios that describe how a user should interact with the user interface (UI), and then implement those steps using `Selenium` to control a real browser (like Chrome or Firefox). This enables end-to-end testing, ensuring that the application behaves correctly from the user's perspective.