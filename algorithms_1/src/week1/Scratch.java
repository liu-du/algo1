package week1;

import java.util.Arrays;

public class Scratch {
    public static void main (String args[]) {
        Haha ha = new Haha();
        Haha hah = new Haha();

        System.out.println(ha == hah);

        hah.a = 10;

        System.out.println(ha == hah);

        hah = ha;


        // == tests identity, better override equal methods
        System.out.println(ha == hah);


        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};

        // == on array test identity
        System.out.println(a == b);

        // equals on array test identity as well...
        System.out.println(a.equals(b));

        // need to use Arrays.equals to test if every value of the array is the same
        System.out.println(Arrays.equals(a, b));
    }
}
