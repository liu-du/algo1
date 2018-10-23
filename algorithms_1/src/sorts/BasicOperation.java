package sorts;

import java.util.Random;

public interface BasicOperation {

    default boolean less(Comparable a, Comparable b)
    {
        return a.compareTo(b) < 0;
    }

    default void exch(Comparable[] a, int i, int j)
    {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    default boolean isSorted(Comparable[] a, int lo, int hi)
    {
        for (int i = lo; i < hi; i++) {
            if (less(a[i+1], a[i])) return false;
        }
        return true;
    }

    default boolean isSorted(Comparable[] a)
    {
        for (int i = 0; i < a.length - 1; i++) {
            if (less(a[i+1], a[i])) return false;
        }
        return true;
    }

    default Integer[] randomInts(int size, int start, int end) {
        Random rand = new Random();
        return rand
                .ints(size, start, end)
                .boxed()
                .toArray(Integer[]::new);
    }

}
