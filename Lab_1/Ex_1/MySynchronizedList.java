package Lab_1.Ex_1;

public class MySynchronizedList implements MyList {
    
    private MyList list;

    public MySynchronizedList(MyList l) {
        list = l;
    }

    @Override
    public synchronized void add(int n) {
        list.add(n);
    }

    @Override
    public synchronized int get(int index) {
        return list.get(index);
    }
}
