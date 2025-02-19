# Lab 1

## Lab1_1

### PDF Tests 
```
Coverage after implementing all methods is 100% with the mentioned tests in the PDF file.
```
### Before disabling test for BoundedTqsStack
|Name Of Class|Class|Methods|Lines|Branch|
|------|------|------------------|-------------|----------------|
|Total|100%|100%|100%|100%|
|TqsStack|100%|100%|100%|100%|
|BoundedTqsStack|100%|100%|100%|100%|

### After disabling test for BoundedTqsStack
|Name Of Class|Class|Methods|Lines|Branch|
|------|------|------------------|-------------|----------------|
|Total|50%|75%|68%|66%|
|TqsStack|100%|100%|100%|100%|
|BoundedTqsStack|0%|0%|0%|0%|
```
I decided to make a separate class for bounded, so that it would be easier to spot diferences in coverage.
```

### AI Tests

```
Relatively to the AI tests now it was also able to provided 100% coverage using ChatGPT model o3-mini-high, this might be because stack is a relativelly know and wide concept but it might not be as good for other types of classes.
```
|Name Of Class|Class|Methods|Lines|Branch|
|------|------|------------------|-------------|----------------|
|Total|100%|100%|100%|100%|
|TqsStack|100%|100%|100%|100%|
|BoundedTqsStack|100%|100%|100%|100%|

### Quality of Code based on Tests

```
TqsSTack will fail when for example when we try to popTopN with zero or a negative number, this proves that despite the high coverage level we cannot assess the full quality of the code based on coverage only, also we should to try and make as many tests as possible and with as many values as possible.
```

## Lab1_2

### Class and Methods Coverage

```
The classes the offer less coverage are CuponEuromillions in methods format and countDips and BoundedSetofNaturals in methods fromArray, add, hashCode, size and intersects. There are also decision branches missing from the tests.
```

### More Tests for BoundedSetOfNaturals

```
After writting more unit tests for BoundedSetofNaturals I was able to get the cover from 54% in missed intructions and 50% in missed branches to 100% in both as seens in the following table.
```
|Moment|Missed Instructions(%)|Missed Branches(%)|
|------|------------------|-------------|
|Before|54%|50%|
|After|100%|100%|

### Improving Coverage
```
To improve the coverage to the desired 90% of lines, tests for equals and hashcode methods had to be developed, this wasn't really needed since these are basic mehods that don't really need to be tested.
```
