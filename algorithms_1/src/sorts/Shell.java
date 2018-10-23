package sorts;

import java.util.Arrays;

public class Shell implements BasicOperation {

    public void sort(Comparable[] a) {

        int N = a.length;
        int h = 1;
        while (h < N) h = h * 3 + 1;

        while (h != 1) {
            h = h / 3;
            for (int i = h; i < N; i = i + h) {
                for (int j = i; j > 0 && less(a[j], a[j - h]); j = j - h)
                        exch(a, j, j - h);
            }
        }
    }

    public static void main (String[] args) {

        Shell shell = new Shell();

        Integer[] b = shell.randomInts(10, 0, 100);
        Arrays.stream(b).forEach(x -> System.out.print(x + "\t"));

        shell.sort(b);
        System.out.println("\nsort");

        Arrays.stream(b).forEach(x -> System.out.print(x + "\t"));
        System.out.print("\n");

        assert shell.isSorted(b);

    }
}
