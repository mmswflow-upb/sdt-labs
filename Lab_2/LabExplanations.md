# SDT Lab 2 Explanations

Note: I created a maven project so that I could install JUnit as a dependency for the unit testing done in ex1 and 2.

## Ex 1
To employ the strategy pattern, I created an interface "MyComparator" for comparing elements between each other in a collection, with only one function called compare that takes two elements of any type. The implementation of that function is left to a class called IntegerAscendingComparator which takes two integer objects as parameter and just returns the result of applying "compareTo" on them.

In this case, the startegy is represented by the interface "MyComparator", IntegerAscendingComparator is the concrete strategy and MyCollections is the context, we pass to one of its sort functions the concrete strategy 

I tested it by comparing the implicit comparator used inside the sort function with the sort function that takes as parameter an instance of a class that implements MyComparator strategy and both were compared with a hardcoded array.

## Ex 2
The personalData interface was the "old" service interface that had to be adapted in order for the client code to be able to use the "new" PersonalInformation interface.

For that, I created a concrete class that acts as the adapter which implements the PersonalInformation interface and wraps an object of type PersonalDataInterface, so the client (test class in this case) can interact directly with the PersonalInformation service 



## Ex 3

First I created the event type enums, then the SensorEvent class. Then the NotificationService interface which either handles an event or sets the next handler. The NotificationServiceBase abstract class was made to implement the interface and also hold a reference to the next handler. Then I created multiple concrete classes that extended the base abstract class each with their own implementation of the handle function. 

The sensor is just a class that extends Thread, in which a while loop keeps producing events with randomly generated data about location and event type and it passes them to a chain of handlers.