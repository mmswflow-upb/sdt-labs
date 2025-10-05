package Lab_1.Ex_1;


public class MyLinkedList implements MyList {
    private static class Node { int v; Node next; Node(int v) { this.v = v; } }
    private Node head, tail;
    private int size = 0;

    @Override 
    public void add(int value) {
        Node n = new Node(value);
        if (head == null) head = tail = n; else { tail.next = n; tail = n; }
        size++;
    }

    @Override 
    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node cur = head;
        for (int i = 0; i < index; i++) cur = cur.next;
        return cur.v;
    }
}
