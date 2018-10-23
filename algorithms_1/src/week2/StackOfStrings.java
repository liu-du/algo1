package week2;


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class StackOfStrings {

    private class Node {
        String item;
        Node next;
    }

    private Node first = null;


    public StackOfStrings() {}

    public void push (String item) {
        Node old_first = first;
        first = new Node();
        first.item = item;
        first.next = old_first;
    }

    public String pop() {
        String item = first.item;
        first = first.next;
        return item;
    }

    public boolean isEmpty () {
        return first == null;
    }

    public static void main (String args[]) {
       StackOfStrings stack = new StackOfStrings();

       while (!StdIn.isEmpty()) {
           String s = StdIn.readString();
           if (s.equals("-")) StdOut.print(stack.pop());
           else               stack.push(s);
       }

    }
}
