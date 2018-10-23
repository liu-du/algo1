package week4;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class RBBST<Key extends Comparable<Key>, Value> {

    private Node root;

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        private boolean color;

        public Node(Key key, Value value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }
    }

    private void flipColor(Node h) {
        assert isRed(h);
        assert isRed(h.left);
        assert isRed(h.right);

        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    private Node rotateLeft(Node h) {
        assert isRed(h);

        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;

        return x;
    }

    private Node rotateRight(Node h) {
        assert isRed(h);

        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;

        return x;
    }

    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.color;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {

        if (node == null) return new Node(key, value, RED);

        int cmp = key.compareTo(node.key);

        if (cmp < 0) node.left = put(node.left, key, value);
        else if (cmp > 0)node.right = put(node.right, key, value);
        else node.value = value;

        if (isRed(node.right) && !isRed(node.left)) rotateLeft(node); // right leaning
        if (isRed(node.left) && isRed(node.left.left)) rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColor(node);

        return node;
    }

    public Value get(Key key) {
        Node current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current.value;
            }
        }

        return null;
    }

    public Key min() {
        Node current = root;
        if (current == null) return null;
        while (current.left != null) {
            current = current.left;
        }
        return current.key;
    }

    public Key max() {
        Node current = root;
        if (current == null) return null;
        while (current.right != null) {
            current = current.right;
        }
        return current.key;
    }

    public Key floor(Key floor) {
        Node node = floor(root, floor);
        if (node == null) return null;
        else return node.key;
    }

    private Node floor(Node node, Key floor) {
        if (node == null) return null;
        int cmp = node.key.compareTo(floor);
        if (cmp < 0) {
            // current key is smaller than floor, possible to have still bigger keys that are smaller than floor
            Node better = floor(node.right, floor);
            if (better == null) return node;
            else return better;
        } else if (cmp > 0) {
            return floor(node.left, floor);
        } else {
            return node;
        }
    }

    public Key ceiling(Key ceiling) {
        Node node = ceiling(root, ceiling);
        if (node == null) return null;
        else return node.key;
    }

    private Node ceiling(Node node, Key ceiling) {
        if (node == null) return null;
        int cmp = node.key.compareTo(ceiling);
        if (cmp < 0) {
            return ceiling(node.right, ceiling);
        } else if (cmp > 0) {
            Node better = ceiling(node.left, ceiling);
            if (better == null) return node;
            else return better;
        } else {
            return node;
        }
    }

    public Iterable<Key> keys() {
        Stack<Key> stack = new Stack<>();
        push(root, stack);
        return stack;
    }

    private void push(Node node, Stack<Key> stack) {
        if (node != null) {
            push(node.right, stack);
            stack.push(node.key);
            push(node.left, stack);
        }
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node node) {
        if (node == null) return null;
        if (node.left == null) {
            node = node.right;
        } else {
            node.left = deleteMin(node.left);
        }

        return node;
    }

    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node node) {
        if (node == null) return null;
        if (node.right == null) {
            node = node.left;
        } else {
            node.right = deleteMin(node.right);
        }

        return node;
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;
        int cmp = node.key.compareTo(key);

        if (cmp < 0) {
            node.right = delete(node.right, key);
        } else if (cmp > 0) {
            node.left = delete(node.left, key);
        } else {
            if (node.left == null) { // one or no child
                node = node.right;
            } else if (node.right == null) { // one child
                node = node.left;
            } else { // two children, choose to delete move the minimum of the right subtree to this node and remove that node
                node.right = deleteMin(node.right);
            }
        }
        return node;
    }


    public static void main(String[] args) {

        RBBST<Character, Integer> st = new RBBST<>();

        Character[] test = {'S', 'E', 'A', 'R', 'C', 'H', 'E', 'X', 'A', 'M', 'P', 'L', 'E'};
        for (int i = 0; i < test.length; i++) {
            Character key = test[i];
            st.put(key, i);
        }

        StdOut.println(st.max());
        StdOut.println(st.min());

        StdOut.println("floor");
        StdOut.println(st.floor('E'));
        StdOut.println(st.floor('F'));
        StdOut.println(st.floor('W'));
        StdOut.println(st.floor('1'));


        StdOut.println("\nceiling");
        StdOut.println(st.ceiling('B'));
        StdOut.println(st.ceiling('X'));
        StdOut.println(st.ceiling('Z'));

        StdOut.println("\nkeys");
        for (Character s: st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }

        StdOut.println("\ndelete two Mins");
        st.deleteMin();
        st.deleteMin();
        for (Character s: st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }

        StdOut.println("\ndelete P");
        st.delete('P');
        for (Character s: st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }

        StdOut.println("\ndelete X");
        st.delete('X');
        for (Character s: st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }

        StdOut.println("\ndelete E");
        st.delete('E');
        for (Character s: st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }

        StdOut.println("\ndelete two Maxs");
        st.deleteMax();
        st.deleteMax();
        for (Character s: st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }

    }

}

