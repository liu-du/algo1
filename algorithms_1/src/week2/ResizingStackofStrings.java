package week2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ResizingStackofStrings {

    private String[] s;
    private int N = 0;

    public ResizingStackofStrings() {
        s = new String[1];
    }

    private void resize(int capacity) {
        String[] copy = new String[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    public void push (String item) {
        if (N == s.length) resize(N * 2);
        s[N++] = item;
    }

    public String pop() {
        int s_length = s.length;
        if (s_length > 1 && N == s_length / 4) resize(s_length / 2);
        String item = s[--N];
        s[N] = null;
        return item;
    }

    public boolean isEmpty () {
        return N == 0;
    }

    public static void main (String args[]) {
        ResizingStackofStrings stack = new ResizingStackofStrings();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) StdOut.print(stack.pop());
            else               stack.push(s);
        }

    }
}
