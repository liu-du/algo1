package sorts;

import java.util.Arrays;
import java.util.Random;

public class DutchNationalFlag implements BasicOperation {

    private void swap(Flag[] a, int i, int j) {
        Flag swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public boolean isSorted(Flag[] a)
    {
        for (int i = 0; i < a.length - 1; i++) {
            if ((a[i+1].getColor().compareTo(a[i].getColor())) < 0) return false;
        }
        return true;
    }


    public void sort(Flag[] a) {
       int next_blue = 0;
       int next_white = a.length - 1;
       int i = 0;

       while (i <= next_white) {

           String current = a[i].getColor();

           switch (current) {
               case "r":
                   i++;
                   break;
               case "b":
                   swap(a, i, next_blue);
                   next_blue++;
                   i++;
                   break;
               case "w":
                   swap(a, i, next_white);
                   next_white--;
                   break;
           }

//           Arrays.stream(a).map(x -> x.getColor()).forEach(x -> System.out.print(x + "\t"));
//           System.out.print("\n");
//           for (int j = 0; j < a.length; j++) {
//               if (j < next_blue || j > next_white) System.out.print("#" + "\t");
//               else if (j == next_blue || j == next_white || j == i) System.out.print("^" + "\t");
//               else System.out.print("_" + "\t");
//           }
//           System.out.print("\n");
       }
    }

    public static void main (String[] args) {

        DutchNationalFlag df = new DutchNationalFlag();

        Random rand = new Random();

        String[] allColors = {"b", "r", "w"};

        Flag[] a = new Flag[10];

        for (int i = 0; i < a.length; i++) {
            a[i] = new Flag(allColors[rand.nextInt(3)]);
        }


        Arrays.stream(a).map(Flag::getColor).forEach(x -> System.out.print(x + "\t"));
        System.out.println("\nsort\n");

        df.sort(a);

        Arrays.stream(a).map(Flag::getColor).forEach(x -> System.out.print(x + "\t"));

        assert df.isSorted(a);

    }
}
