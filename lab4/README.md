# Lab 4

## Lab4_1

### Selenium Web Driver and Selenium Jupiter
`Selenium Web Driver` allows developers to drive/controller a web browser, as if a real user was operating the browser (via a programing interface, i.e, API), this happens through a driver that is specific for each browser (chromedrive for Chrome, geckodriver for Firefox, etc). `Selenium Jupyter` is a framework that simplifies the use of the `Web Driver` by initiating and closing it automatically for example, this happens by using dependency injection. This is quite useful for developers to run tests on frontend parts of their projects and not to have to rely on human tests (which should still be done but on a later phase of the project)

## Lab4_2

### Selenium IDE
`Selenium IDE` is an extension for browsers that allows us to record tests and then export them to `JUint` tests. This is extremely useful since this way the programmer does not need to write the test from scratch, he only needs to open the Selenium IDE and record the steps he wants to test and then export it to `JUint` and then make any necessary modifications, saving, this way, a lot of time. If the test is broken the IDE will stop the test and indicate the command where the error occured by putting it in a red color.

## Lab4_3

### Locators
The default locators selectd by `Selenium IDE` were `CSS` locators, this means neither `XPath` nor identifier-based locators were used. The locator strategy that is the most robust is identifier-based locators, i.e `HTML IDs`, because they are unique and consistently predictable, being, if avaliable, the prefered method, if `HTML IDs` are not avaliable then our best choice becomes well-written `CSS` selectors, even though `XPath` works as well as `CSS` the syntax is way more troublesome and frequently difficult to debug and, despite being very flexible they are typically not tested by browser vendors and tend to be quite slow. Other locators startegies can be used but are not very recommended like those based on `linkText` and `partialLinkText` because they only work on link elements and they call `querySelectorAll` selectors internally in WebDriver, Tag names are also not recommended as they can be a dangerous way to locate elemets because there arew frequently multiple elements of the same tag present in one page but can be quite useful when calling `findElements(By)` (returns a collection of elements). So the main recommendation is to keep the locators as compact and as readable as possible because asking the `WebDriver` to traverse the `DOM` structure is an expensive operation, and the narrower the scope of the search, the better. So identifier-based locators is the best and most robust locator followed by well-written CSS, other methods are not recommended.

### Implicit Wait VS Explicit Wait
In implicit waits we just define the time that the driver will wait before it executes the next action and it has always to wait for that time. In explicit waits, however, you still define the amount of maximum wait time but you have the option to just wait for a specific element to load and when the element is loaded the wait ends. Explicit wait is generally more recomended because the wait time will only wait for a element to load, being dinamic, in contrast implicit wait will always wait for the amount of time defined.

## Lab4_4

### POM
POM or Page Object Model is an object design pattern in `Selenium` that allows the webpages that are going to be tested to be represented as classes and these pages' elements(of interest) as variables in that class. And the user interactions on the page can be implemented as methods on the class. This will allow for cleaner-looking and easy to read tests as well as improving code reliability, readbility and maintainibility.

### Page Factory

`Page Factory` is a design pattern in `Selenium` that simplifies the initialization of POM. It leverages annotations to automatically instantiate elements and provides a cleaner and more maintainable approach to handling UI elements on web pages. By using `@FindBy` annotations, `Page Factory` allows developers to define locators for web elements without needing to manually instantiate them, which improves the readability and reusability of the code. The primary advantage of `Page Factory` over traditional Page Object Models is that it supports lazy loading of elements, meaning elements are only initialized when they are actually needed in the test, optimizing performance and reducing unnecessary overhead. `Page Factory` also allows `@CacheLookup`, which enables Selenium to cache the located elements once they are found. This means that subsequent accesses to the same element do not require a new search, improving the speed of the test and reducing the need for repeated DOM traversal.

### Implementations
In relation to the implementations most of them went smothly the one that was a bit more difficult was [Toptal](https://www.toptal.com) because the website has changed quite a lot since the linked article was written, the rest of the implementations went pretty much seamlessly. As we can see using `Page Factory` the tests are way more clean looking compared to the tests in lab4_2.

## Lab4_5

### Docker Browsers
Using browsers in `Docker` can be extremely useful because we can test on multiple browsers, however, despite existing methods to show the `GUI` of the browser used for the test I was not able to make it work, well, this is a minor disadvantage, since the main purpose of browsers in `Docker` is to test in multiple browsers and as long as the test passes the `SUT` will probably work in that browser but a `GUI` would be useful to see how the `SUT` will look in multiple browsers.