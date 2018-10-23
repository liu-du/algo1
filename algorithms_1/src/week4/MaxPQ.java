package week4;

import sorts.BasicOperation;

public class MaxPQ<Key extends Comparable<Key>> implements BasicOperation {

    private Key[] heap;
    private int N;


    public MaxPQ(int capacity) {
        heap = (Key[]) new Comparable[capacity + 1];
    }

    private void swim(int i) {
        while (i > 1 && less(heap[i / 2], heap[i])) {
            exch(heap, i, i/2);
            i = i / 2;
        }
    }

    private void sink(int i) {

        int child = 2 * i;

        if (N == child && less(heap[i], heap[child])) {
            exch(heap, i, 2 * i);
        } else if (N > child) {
            if (less(heap[child], heap[child + 1])) child++;
            exch(heap, i, child);
            sink(child);
        }
    }

    public void insert(Key v) {
       heap[++N] = v;
       swim(N);
    }

    public Key delMax() {
        Key max = heap[1];
        heap[1] = heap[N];
        heap[N--] = null;
        sink(1);
        return max;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public Key max() {
        return heap[1];
    }

    public int size() {
        return N;
    }

    public static void main(String[] args) {
        MaxPQ<Integer> pq = new MaxPQ<>(10);

        assert pq.size() == 0;
        assert pq.isEmpty();

        pq.insert(3);
        assert pq.size() == 1;
        assert !pq.isEmpty();
        assert pq.max() == 3;

        pq.insert(8);

        assert pq.size() == 2;
        assert pq.max() == 8;

        pq.insert(5);
        pq.insert(1);

        assert pq.delMax() == 8;
        assert pq.size() == 3;
        assert pq.delMax() == 5;
        assert pq.size() == 2;
        assert pq.delMax() == 3;
        assert pq.size() == 1;

        assert pq.max() == 1;
        assert pq.delMax() == 1;
        assert pq.size() == 0;
        assert pq.isEmpty();

    }

}
