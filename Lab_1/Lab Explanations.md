# Lab 1 Explanations

## Ex 1:

Here I used the static factory method, the interface "list" acting as the product interface, 
and MyArrayList etc are concrete implementations of the interface. There's no "Creator" interface 
or class, because in this example we don't need to attach any business logic to the lists.

The client code doesn't need to worry about the way the lists are instantiated,
it just provides the type of list it wishes to create and the static method in the 
product interface handles it, the enums for list types are defined in ListType.java, 
this way, objects could be cached or reused to save memory

## Ex 2


In this exercise, I've used the fluent builder pattern, basically there's no need for a director in 
here since we're building only one type of product, just with different configurations (parameters).

This why I used a nested concrete builder class, which allows us to set the common features for cars from the constructor,
then decide upon the optional ones by calling the set functions in a chain then get instantiate the object by getting the result.

This solves the telescoping constructor problem and gives the possiblity of validating configurations before creating
objects (cars in this example)


## Ex 3

Here I've extended the functionality of the lists interface by adding a decorator abstract class which
implements the MyList interface, and provided a concrete implementation of this abstract class which logs
messages everytime something is added to a list wrapped by the decorator.

The decorator implements the "MyList" interface, this way the client code doesn't have to change the way it calls the methods on my lists, it just has to
wrap the lists with the decorator in case it wants additional functionality and gives the possibility of chaining decorators for combined behavior.