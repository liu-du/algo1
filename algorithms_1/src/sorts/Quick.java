package sorts;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Arrays;


public class Quick implements BasicOperation {

    public Quick () {}

    public void sort (Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    public int partition (Comparable[] a, int lo, int hi) {

        int i = lo;
        int j = hi + 1;

        while (true) {

            while (less(a[++i], a[lo]) && i != hi) {}
            while (less(a[lo], a[--j])) {}

            if (i >= j) break;
            exch(a, i, j);
        }

        exch(a, j, lo);
        return j;
    }

    private void sort (Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int mid = partition(a, lo, hi);
        sort(a, lo, mid - 1);
        sort(a, mid + 1, hi);
    }


    public static void main (String[] args) {

        Quick quick = new Quick();

        Integer[] b = quick.randomInts(11, 0, 100);
        Arrays.stream(b).forEach(x -> System.out.print(x + "\t"));

        quick.sort(b);
        System.out.println("\nsort");

        Arrays.stream(b).forEach(x -> System.out.print(x + "\t"));
        System.out.print("\n");

        assert quick.isSorted(b);
    }

}


