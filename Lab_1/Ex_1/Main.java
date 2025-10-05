package Lab_1.Ex_1;

public class Main {
    public static void main(String[] args) {
        MyList arrayList = MyList.getList(ListType.Array);
        MyList linkedList = MyList.getList(ListType.Linked);
        MyList syncList = MyList.getList(ListType.Synchronized);

        arrayList.add(10);
        arrayList.add(20);
        arrayList.add(30);

        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);

        syncList.add(100);
        syncList.add(200);

        System.out.println("ArrayList: " + arrayList);
        System.out.println("LinkedList: " + linkedList);
        System.out.println("SynchronizedList: " + syncList);
        System.out.println();

        System.out.println("arrayList.get(1) = " + arrayList.get(1));
        System.out.println("linkedList.get(2) = " + linkedList.get(2));
        System.out.println("syncList.get(0) = " + syncList.get(0));
    }
}
