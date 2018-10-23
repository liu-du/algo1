import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private Node first;
    private Node last;
    private int size;

    private class DequeIterator implements Iterator<Item> {

        private int sz = size;
        private Node f = first;

        public boolean hasNext() { return sz != 0; }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Empty Deque.");
            Item item = f.item;
            f = f.next;
            sz--;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove is not supported.");
        }
    }


    public Deque() { }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }

    public void addFirst(Item item)
    {
        if (item == null) throw new IllegalArgumentException("cannot add null!");
        if (isEmpty()) {
            first = new Node();
            first.item = item;

            // last points to first
            last = first;
        } else {
            Node old_first = first;
            first = new Node();
            first.item = item;
            first.next = old_first;

            // second first previous field points to first
            old_first.previous = first;
        }
        size++;
    }

    public void addLast(Item item)
    {
        if (item == null) throw new IllegalArgumentException("cannot add null!");
        if (isEmpty()) addFirst(item);
        else {
            Node old_last = last;
            last = new Node();
            last.item = item;
            last.previous = old_last;

            // second last previous field points to last
            old_last.next = last;
            size++;
        }
    }

    public Item removeFirst()
    {
        if (isEmpty()) throw new NoSuchElementException("Empty Deque!");
        Item item = first.item;
        first = first.next;
        if (size == 1) last = null;
        else first.previous = null;
        size--;
        return item;
    }

    public Item removeLast()
    {
        if (isEmpty()) throw new NoSuchElementException("Empty Deque!");
        Item item = last.item;
        last = last.previous;
        if (size == 1) first = null;
        else last.next = null;
        size--;
        return item;
    }

    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    public static void main(String[] args) {

        Deque<Integer> deque0 = new Deque<Integer>();
        assert deque0.isEmpty();
        assert deque0.isEmpty();
        deque0.addLast(2);
        assert deque0.removeLast() == 2;

        Deque<Integer> deque1 = new Deque<Integer>();
        deque1.addLast(0);
        assert deque1.removeFirst() == 0;


        Deque<Integer> deque = new Deque<>();
        assert deque.isEmpty();

        deque.addFirst(1);
        assert !deque.isEmpty();
        assert deque.size() == 1;

        deque.addFirst(2);
        assert deque.size() == 2;

        assert 2 == deque.removeFirst();
        assert 1 == deque.removeFirst();
        assert deque.isEmpty();

        deque.addFirst(1);
        deque.addFirst(2);

        assert 1 == deque.removeLast();
        assert 2 == deque.removeLast();
        assert deque.isEmpty();

        deque.addFirst(1);
        deque.addLast(999);
        deque.addFirst(2);

        assert deque.size() == 3;
        assert 999 == deque.removeLast();
        assert deque.size() == 2;
        assert 2 == deque.removeFirst();
        assert deque.size() == 1;
        assert 1 == deque.removeFirst();
        assert deque.isEmpty();

        deque.addFirst(7);
        deque.addFirst(4);

        for (Integer d : deque) {
            System.out.println(d);
        }

        assert deque.size() == 2;
        assert 7 == deque.removeLast();
        assert deque.size() == 1;
        assert 4 == deque.removeFirst();
        assert deque.isEmpty();


        Deque<Integer> deque2 = new Deque<>();
        deque2.addFirst(1);

        for (Integer d : deque2) {
            System.out.println(d);
        }

    }


}
