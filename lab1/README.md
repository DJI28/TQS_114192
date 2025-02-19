# Lab 1

-- fazer tabelinhas dps

## Lab1_1
```
Coverage after implementing all methods is 100% with the mentioned tests in c, disabling bounded stack test for example will bring down the coverage to 50% in Class, 75% in Methods, 68% in Lines and 66% in Branch.
```
```
Relatively to the AI tests now it was also able to provided 100% coverage, although the tests don't seem as extensive as the ones in c, for example it does not verify if the stack has n elements, that after n pops it is empty.
```
```
TqsSTack will fail when for example when we try to popTopN with zero or a negative number, this proves that despite the high coverage level we cannot assess the full quality of the code based on coverage only, also we should to try and make as many tests as possible and with as many values as possible.
```

## Lab1_2
```
The classes the offer less coverage are CuponEuromillions in methods format and countDips and BoundedSetofNaturals in methods fromArray, add, hashCode, size and intersects. There are also decision branches missing from the tests.
```
```
After writting more unit tests for BoundedSetofNaturals ...
```

Para uso futuro
coverage BoundedSetofNaturals 54% cov in missed intructions and 50% in missed branches
now at 100%