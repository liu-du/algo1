package sorts;

import java.util.Arrays;

public class Insertion implements BasicOperation {

    public void sort(Comparable[] a) {

        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0; j--)
               if (less(a[j], a[j - 1]))
                   exch(a, j, j - 1);
            else
                break;
        }

    }

    public static void main (String[] args) {

        Insertion insertion = new Insertion();

        Integer[] b = insertion.randomInts(10, 0, 100);
        Arrays.stream(b).forEach(x -> System.out.print(x + "\t"));

        insertion.sort(b);
        System.out.println("\nsort");

        Arrays.stream(b).forEach(x -> System.out.print(x + "\t"));
        System.out.print("\n");

        assert insertion.isSorted(b);

    }
}
