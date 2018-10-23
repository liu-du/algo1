package week2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QueueOfStrings {

    private class Node {
        String item;
        Node next;
    }

    private Node first = null;
    private Node last = null;

    public QueueOfStrings() {}

    public void enqueue(String item) {
        Node old_last = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else old_last.next = last;
    }

    public String dequeue() {
        String item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        return item;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public static void main (String args[]) {

        QueueOfStrings queue = new QueueOfStrings();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) StdOut.print(queue.dequeue());
            else               queue.enqueue(s);
        }

    }
}
