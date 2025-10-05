package Lab_1.Ex_3;

public class Main {
    public static void main(String[] args) {
        MyList array = MyList.getList(ListType.Array);

        LoggingDecorator loggingList = new LoggingDecorator(array);
        loggingList.add(5);
        
    }
}
