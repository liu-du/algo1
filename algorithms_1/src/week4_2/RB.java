package week4_2;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class RB<Key extends Comparable<Key>, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Key key;
        Value value;
        Node left, right;
        int sz;
        boolean color; // color of parent link

        public Node(Key key, Value value, boolean color) {
            this.key = key;
            this.value = value;
            this.sz = 1;
            this.color = color;
        }
    }

    private Node root;

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    private Node rotateLeft(Node h) {
        assert isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h) {
        assert isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        assert !isRed(h);
        assert isRed(h.left);
        assert isRed(h.right);
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) return new Node(key, value, RED);

        int cmp = node.key.compareTo(key);

        if (cmp < 0)
            node.right = put(node.right, key, value);
        else if (cmp > 0)
            node.left = put(node.left, key, value);
        else node.value = value;

        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);

        node.sz = 1 + size(node.left) + size(node.right);
        return node;
    }

    public Value get(Key key) {
        Node out = get(root, key);
        if (out == null) return null;
        else return out.value;
    }

    private Node get(Node node, Key key) {
        if (node == null) return null;

        int cmp = node.key.compareTo(key);

        if (cmp < 0) return get(node.right, key);
        else if (cmp > 0) return get(node.left, key);
        else return node;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;

        int cmp = node.key.compareTo(key);

        if (cmp == 0) {
            if (node.left == null && node.right == null) return null;
            else if (node.left == null) node = node.right;
            else if (node.right == null) node = node.left;
            else {
                Node rightMin = select(node.right, 1);
                node.right = deleteMin(node.right);
                node.key = rightMin.key;
                node.value = rightMin.value;
            }
        } else if (cmp < 0)
            node.right = delete(node.right, key);
        else
            node.left = delete(node.left, key);

        node.sz = 1 + size(node.left) + size(node.right);
        return node;
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node node) {
        if (node == null) return null;
        if (node.left == null) { return node.right; }

        node.left = deleteMin(node.left);
        node.sz = 1 + size(node.left) + size(node.right);
        return node;
    }

    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node node) {
        if (node == null) return null;
        if (node.right == null) { return node.left; }

        node.right = deleteMax(node.right);
        node.sz = 1 + size(node.left) + size(node.right);
        return node;
    }




    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.sz;
    }

    public int size(Key lo, Key hi) {
        return rank(hi) - rank(lo) + 1;
    }

    public Key min() {
        if (isEmpty()) return null;
        Node current = root;
        while (current.left != null) current = current.left;
        return current.key;
    }

    public Key max() {
        if (isEmpty()) return null;
        Node current = root;
        while (current.right != null) current = current.right;
        return current.key;
    }

    public Key floor(Key key) {
        Node node = floor(root, key);
        if (node == null) return null;
        else return node.key;
    }

    private Node floor(Node node, Key key) {
        if (node == null) return null;

        int cmp = node.key.compareTo(key);
        if (cmp > 0) return floor(node.left, key);
        if (cmp == 0) return node;

        Node out = floor(node.right, key);
        if (out == null) return node;
        else return out;
    }

    public Key ceiling(Key key) {
        Node out = ceiling(root, key);
        if (out == null) return null;
        else return out.key;
    }

    private Node ceiling(Node node, Key key) {
        if (node == null) return null;

        int cmp = node.key.compareTo(key);

        if (cmp < 0) return ceiling(node.right, key);
        if (cmp == 0) return node;

        Node out = ceiling(node.left, key);
        if (out == null) return node;
        else return out;
    }

    public int rank(Key key) {
        if (isEmpty()) return 0;
        else return rank(root, key);
    }

    public int rank(Node node, Key key) {
        if (node == null) return 0;
        int cmp = node.key.compareTo(key);
        if (cmp == 0) {
            return size(node.left) + 1;
        } else if (cmp < 0) {
            return size(node.left) + 1 + rank(node.right, key);
        } else {
            return rank(node.left, key);
        }
    }

    public Key select(int k) {
        if (k <= 0 || k > size()) return null;
        return select(root, k).key;
    }

    private Node select(Node node, int k) {
        if (node == null) return null;

        int currentRank = rank(node.key);
        if (currentRank < k) return select(node.right, k);
        else if (currentRank > k) return select(node.left, k);
        else return node;

    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Stack<Key> stack = new Stack<>();
        push(stack, root, lo, hi);
        return stack;
    }

    private Stack<Key> push(Stack<Key> stack, Node node, Key lo, Key hi) {
        if (node == null) return stack;

        int cmpLo = node.key.compareTo(lo);
        int cmpHi = node.key.compareTo(hi);

        if (cmpLo < 0) {
            push(stack, node.right, lo, hi);
        } else if (cmpLo == 0) {
            push(stack, node.right, lo, hi);
            stack.push(node.key);
        } else if (cmpHi > 0) {
            push(stack, node.left, lo, hi);
        } else if (cmpHi == 0) {
            stack.push(node.key);
            push(stack, node.left, lo, hi);
        } else {
            push(stack, node.right, lo, hi);
            stack.push(node.key);
            push(stack, node.left, lo, hi);
        }
        return stack;
    }

    public Iterable<Key> keys() {
        Stack<Key> stack = new Stack<>();
        push(stack, root);
        return stack;
    }

    private void push(Stack<Key> stack, Node node) {
        if (node == null) return;
        push(stack, node.right);
        stack.push(node.key);
        push(stack, node.left);
    }

    public static void main(String[] args) {

        RB<Character, Integer> st = new RB<>();

        assert st.size() == 0;
        assert st.isEmpty();

        assert st.min() == null;
        assert st.max() == null;

        assert !st.contains('A');
        assert st.rank('A') == 0;
        assert st.select(1) == null;

        Character[] test = {'S', 'E', 'A', 'R', 'C', 'H', 'E', 'X', 'A', 'M', 'P', 'L', 'E'};
        for (int i = 0; i < test.length; i++) {
            Character key = test[i];
            StdOut.println(key);
            st.put(key, i);
        }

        assert st.size() == 10;
        assert st.size('A','C') == 2;
        assert st.size('A','D') == 2;
        assert st.size('C','E') == 2;
        assert st.size('C','F') == 2;
        assert st.size('A','Z') == 10;

        assert !st.isEmpty();

        assert st.min() == 'A';
        assert st.max() == 'X';

        assert st.contains('A');
        assert st.contains('C');
        assert !st.contains('B');

        assert st.rank('A') == 1;
        assert st.rank('C') == 2;
        assert st.rank('X') == 10;

        assert st.select(0) == null;
        assert st.select(1) == 'A';
        assert st.select(2) == 'C';
        assert st.select(10) == 'X';
        assert st.select(11) == null;

        assert st.floor('E') == 'E';
        assert st.floor('F') == 'E';
        assert st.floor('W') == 'S';

        assert st.ceiling('B') == 'C';
        assert st.ceiling('X') == 'X';
        assert st.ceiling('Z') == null;

        st.deleteMin();
        assert st.size() == 9;
        assert st.select(1) == 'C';
        assert st.rank('E') == 2;

        st.deleteMin();
        assert st.size() == 8;
        assert st.select(1) == 'E';
        assert st.rank('H') == 2;

        assert st.select(8) == 'X';
        assert st.select(7) == 'S';
        assert st.select(6) == 'R';
        assert st.select(5) == 'P';

        st.deleteMax();
        assert st.size() == 7;
        assert st.select(1) == 'E';
        assert st.rank('H') == 2;

        assert st.select(8) == null;
        assert st.select(7) == 'S';
        assert st.select(6) == 'R';
        assert st.select(5) == 'P';

        st.deleteMax();
        assert st.size() == 6;
        assert st.select(1) == 'E';
        assert st.rank('H') == 2;

        assert st.select(8) == null;
        assert st.select(7) == null;
        assert st.select(6) == 'R';
        assert st.select(5) == 'P';

        st.delete('P');
        assert st.size() == 5;
        assert !st.contains('P');

        st.delete('R');
        assert st.size() == 4;
        assert !st.contains('P');
        assert !st.contains('R');

        st.delete('E');
        assert st.size() == 3;

        st.delete('H');
        st.delete('L');
        assert st.size() == 1;

        st.delete('M');
        assert st.size() == 0;
        assert st.isEmpty();

        for (Character s: st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }


    }
}
