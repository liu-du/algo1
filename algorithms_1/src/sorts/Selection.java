package sorts;
import java.util.Arrays;
import java.util.Random;

public class Selection implements BasicOperation {

    public void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++)
                if (less(a[j], a[min]))
                    min = j;
            exch(a, i, min);
        }
    }

    public static void main (String[] args) {

        Selection selection = new Selection();

        Integer[] b = selection.randomInts(10, 0, 100);
        Arrays.stream(b).forEach(x -> System.out.print(x + "\t"));

        selection.sort(b);
        System.out.println("\nsort");

        Arrays.stream(b).forEach(x -> System.out.print(x + "\t"));
        System.out.print("\n");

        assert selection.isSorted(b);

    }
}
