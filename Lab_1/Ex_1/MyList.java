package Lab_1.Ex_1;

public interface MyList {
    void add(int value);
    int get(int index);

    static MyList getList(ListType type) {
        return switch (type) {
            case Array -> new MyArrayList();
            case Linked -> new MyLinkedList();
            case Synchronized -> new MySynchronizedList(new MyArrayList());
        };
    }
}


