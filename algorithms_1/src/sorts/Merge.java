package sorts;

import java.util.Arrays;

public class Merge implements BasicOperation {

    private void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
    {
        assert isSorted(aux, lo, mid);
        assert isSorted(aux, mid + 1, hi);

        int i = lo, j = mid + 1;

        if (less(aux[mid], aux[j]))
            System.arraycopy(aux, lo, a, lo, hi - lo + 1);
        else {
            for (int k = lo; k <= hi; k++) {
                if (i > mid || (j <= hi && less(aux[j], aux[i]))) a[k] = aux[j++]; // stable merge sort
                else a[k] = aux[i++];
            }
        }

        assert isSorted(a, lo, hi);
    }

    private void sort(Comparable[] a, Comparable[] aux, int i, int j)
    {
        // assume i <= j;
        if (i == j) return;
        else {
            int mid = (i + j) / 2;
            sort(aux, a, i, mid);
            sort(aux, a, mid + 1, j);
            merge(a, aux, i, mid, j);
        }
    }

    public void sort(Comparable[] a) {
        Comparable[] aux = a.clone();
        sort(a, aux, 0, a.length - 1);
    }

    public static void main (String[] args) {

        Merge merge = new Merge();

        Integer[] b = merge.randomInts(11, 0, 100);
        Arrays.stream(b).forEach(x -> System.out.print(x + "\t"));

        merge.sort(b);
        System.out.println("\nsort");

        Arrays.stream(b).forEach(x -> System.out.print(x + "\t"));
        System.out.print("\n");

        assert merge.isSorted(b);
    }
}
