# Lab 4

## Lab4_1

## Lab4_2

### Test
If the test is broken the `Selenium IDE` stops the test and indicates the command where the error occured.

## Lab4_3

### Locators
The default locators selectd by `Selenium IDE` were `CSS` locators, this means neither `XPath` nor identifier-based locators were used. The locator strategy that is the most robust is identifier-based locators, i.e `HTML IDs`, because they are unique and consistently predictable, being, if avaliable, the prefered method, if `HTML IDs` are not avaliable then our best choice becomes well-written `CSS` selectors, even though `XPath` works as well as `CSS` the syntax is way more troublesome and frequently difficult to debug and, despite being very flexible they are typically not tested by browser vendors and tend to be quite slow. Other locators startegies can be used but are not very recommended like those based on `linkText` and `partialLinkText` because they only work on link elements and they call `querySelectorAll` selectors internally in WebDriver, Tag names are also not recommended as they can be a dangerous way to locate elemets because there arew frequently multiple elements of the same tag present in one page but can be quite useful when calling `findElements(By)` (returns a collection of elements). So the main recommendation is to keep the locators as compact and as readable as possible because asking the `WebDriver` to traverse the `DOM` structure is an expensive operation, and the narrower the scope of the search, the better. So identifier-based locators is the best and most robust locator followed by well-written CSS, other methods are not recommended.

### Implicit Wait VS Explicit Wait

## Lab4_4

## Lab4_5
