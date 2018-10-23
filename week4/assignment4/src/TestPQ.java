import edu.princeton.cs.algs4.MinPQ;

public class TestPQ {
    public static void main(String[] args) {
        MinPQ a = new MinPQ();

        a.insert(1);
        a.insert(3);
        a.insert(0);
        a.insert(8);
        System.out.println(a.delMin());
        System.out.println(a.delMin());
        System.out.println(a.delMin());
    }
}
