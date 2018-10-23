package week1;

public class BinarySearch {
    public static int binarySearch(int[] a, int key) {

        int lo = 0;
        int hi = a.length - 1;
        int mid;

        while (lo <= hi) {

            mid = (lo + hi) / 2;

            if (a[mid] > key) {
                hi = mid - 1;
            } else if (a[mid] < key) {
                lo = mid + 1;
            } else return key;
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] a = new int[10];
        for (int i = 0; i < 10; i++) {
            a[i] = i;
        }

        System.out.println(binarySearch(a, -5));
        System.out.println(binarySearch(a, 0));
        System.out.println(binarySearch(a, 1));
        System.out.println(binarySearch(a, 2));
        System.out.println(binarySearch(a, 5));
        System.out.println(binarySearch(a, 8));
        System.out.println(binarySearch(a, 9));
        System.out.println(binarySearch(a, 10));
        System.out.println(binarySearch(a, 100));
    }
}
