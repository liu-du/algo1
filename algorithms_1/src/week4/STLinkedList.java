package week4;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class STLinkedList<Key, Value> {

    private class Node {
        private Key key;
        private Value value;
        private Node next;
    }
    private Node first;
    private int N;

    public STLinkedList() {}

    public void put(Key key, Value value) {
        Node current = first;
        while (current != null) {
            if (current.key == key) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        // key not in the ST
        Node oldfirst = first;
        first = new Node();
        first.key = key;
        first.value = value;
        first.next = oldfirst;
        N++;
    }

    public Value get(Key key) {
        Node current = first;

        while (current != null) {
            if (current.key == key) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    public void delete(Key key) {
        put(key, null);
        N--;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
       return N;
    }

    public Iterable<Key> keys() {
        Stack<Key> keys = new Stack<>();
        Node current = first;
        while (current != null) {
            keys.push(current.key);
            current = current.next;
        }
        return keys;
    }


    public static void main(String[] args) {

        STLinkedList<Character, Integer> st = new STLinkedList<>();

        Character[] test = {'S', 'E', 'A', 'R', 'C', 'H', 'E', 'X', 'A', 'M', 'P', 'L', 'E'};
        for (int i = 0; i < test.length; i++) {
            Character key = test[i];
            st.put(key, i);
        }
        for (Character s: st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}
