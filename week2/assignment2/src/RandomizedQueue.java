import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a = (Item[]) new Object[1];
    private int N;

    private class IteratorRandomizedQueue<Item> implements Iterator<Item> {

        private Item[] randomArray;
        private int current;
        private int sz;

        public IteratorRandomizedQueue() {

            randomArray = (Item[]) new Object[N];
            sz = N;

            for (int i = 0; i < N; i++) randomArray[i] = (Item) a[i];

            StdRandom.shuffle(randomArray);

        }

        public boolean hasNext()
        {
            return current != sz;
        }

        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException("no next.");
            return randomArray[current++];
        }

        public void remove()
        {
            throw new UnsupportedOperationException("remove not supported.");
        }
    }


    public boolean isEmpty()
    {
        return N == 0;
    }

    public int size()
    {
        return N;
    }

    private void resize(int n) {
        Item[] new_a = (Item[]) new Object[n];
        int j = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != null) {
                new_a[j] = a[i];
                j++;
            }
        }
        a = new_a;
    }

    public void enqueue(Item item)
    {
        if (item == null) throw new IllegalArgumentException("Cannot enqueue null.");
        if (N == a.length) resize(N * 2);
        a[N] = item;
        N++;
    }

    public Item dequeue()
    {
        if (isEmpty()) throw new NoSuchElementException("Empty queue.");
        if (N > 0 && N == a.length / 4) resize(N * 2);
        int randomNumber = StdRandom.uniform(N);
        Item item = a[randomNumber];
        a[randomNumber] = a[N - 1]; // move last item back
        a[N - 1] = null; // avoid loitering

        N--;
        return item;
    }

    public Item sample()
    {
        if (isEmpty()) throw new NoSuchElementException("Empty queue.");
        int randomNumber = StdRandom.uniform(N);
        return a[randomNumber];
    }

    public Iterator<Item> iterator()
    {
        return new IteratorRandomizedQueue<>();
    }

    public static void main(String[] args)
    {

        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        assert rq.isEmpty();

        rq.enqueue(1);
        assert !rq.isEmpty();
        assert rq.size() == 1;

        assert rq.dequeue() == 1;

        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        assert rq.size() == 3;

        for (int i = 0; i < 5; i++) {
            System.out.println( rq.sample() );
        }

        assert rq.size() == 3;
        assert !rq.isEmpty();


        while (!rq.isEmpty()) {
            System.out.println( rq.dequeue() );
        }

        assert rq.size() == 0;
        assert rq.isEmpty();

        System.out.println("Test iterable");

        int summ1 = 0;
        int summ2 = 0;

        for (int i = 0; i < 5; i++) {
            rq.enqueue(i);
            summ1 += i;
        }

        for (int j : rq) {
            System.out.println(j);
            summ2 += j;
        }

        assert summ1 == summ2;

        System.out.println("test again");
        assert rq.size() == 5;
        while (!rq.isEmpty()) {
            System.out.println(rq.dequeue());
        }

    }
}
