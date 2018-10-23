import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private class Node {
        Node left;
        Node right;
        double x;
        double y;
        int size;
        public Node(double x, double y) {
            this.x = x;
            this.y = y;
            this.size = 1;
        }
    }

    private Node root;

    public KdTree() {}

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;
        else return node.size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("null argument.");
        root = putx(root, p);
    }

    private Node putx(Node node, Point2D p) {
        if (node == null) return new Node(p.x(), p.y());

        if (p.x() < node.x) {
           node.left = puty(node.left, p);
        } else {
           if (node.x == p.x() && node.y == p.y()) return node; // return immediately if point is already in
           node.right = puty(node.right, p); // put to right if xs are the same
        }

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private Node puty(Node node, Point2D p) {
        if (node == null) return new Node(p.x(), p.y());

        if (p.y() < node.y) {
            node.left = putx(node.left, p);
        } else {
            if (node.y == p.y() && node.x == p.x()) return node; // return immediately if point is already in
            node.right = putx(node.right, p);
        }

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("null argument.");
        return getx(root, p) != null;
    }

    private Node getx(Node node, Point2D p) {
        if (node == null) return null;

        if (p.x() < node.x) {
            return gety(node.left, p);
        } else {
            if (node.x == p.x() && node.y == p.y()) return node;
            return gety(node.right, p);
        }
    }

    private Node gety(Node node, Point2D p) {
        if (node == null) return null;

        if (p.y() < node.y) {
            return getx(node.left, p);
        } else {
            if (node.y == p.y() && node.x == p.x()) return node;
            return getx(node.right, p);
        }
    }


    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;
        StdDraw.point(node.x, node.y);
        draw(node.left);
        draw(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException("null argument.");
        Stack<Point2D> stack = new Stack<>();
        range(stack, root, rect, true);
        return stack;
    }

    private void range(Stack<Point2D> stack, Node node, RectHV rect, boolean vertical) {

        if (node == null) return;

        if (isRectLeft(node, rect, vertical)) {
            range(stack, node.left, rect, !vertical);
        } else if (isRectIntersect(node, rect, vertical)) {
            range(stack, node.left, rect, !vertical);
            if (isNodeInRect(node, rect)) {
                push(stack, node);
            }
            range(stack, node.right, rect, !vertical);
        } else {
            range(stack, node.right, rect, !vertical);
        }

    }

    private boolean isRectIntersect(Node node, RectHV rect, boolean vertical) {
        if (vertical) return node.x >= rect.xmin() && node.x <= rect.xmax();
        else return node.y >= rect.ymin() && node.y <= rect.ymax();
    }

    private boolean isNodeInRect(Node node, RectHV rect) {
        return (node.x >= rect.xmin() && node.x <= rect.xmax() && node.y >= rect.ymin() && node.y <= rect.ymax());
    }

    private boolean isRectLeft(Node node, RectHV rect, boolean vertical) {
        if (vertical) return (node.x > rect.xmax());
        else return (node.y > rect.ymax());
    }

    private void push(Stack<Point2D> stack, Node node) {
        stack.push(new Point2D(node.x, node.y));
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("null argument.");
        Node node = nearest(root, p, null, Double.POSITIVE_INFINITY, true);
        if (node == null) return null;
        else return new Point2D(node.x, node.y);
    }

    private double distSquare(Node node, Point2D p) {
        if (node == null) return Double.POSITIVE_INFINITY;
        return (node.x - p.x())*(node.x - p.x()) + (node.y - p.y())*(node.y - p.y());
    }

    private double distSquareX(double x, Point2D p) {
        return (p.x() - x) * (p.x() - x);
    }

    private double distSquareY(double y, Point2D p) {
        return (p.y() - y) * (p.y() - y);
    }

    private Node nearest(Node node, Point2D p, Node currentNearest, double currentSmallestDist, boolean vertical) {

        if (node == null) return null;

        // calculate distance to this node
        double currentNodeDist = distSquare(node, p);

        // calculate small distance to the splitting line
        double currentNodeDistToSplit;
        if (vertical) {
            currentNodeDistToSplit = distSquareX(node.x, p);
        } else {
            currentNodeDistToSplit = distSquareY(node.y, p);
        }

        // if currentNode is closer, update currentNearestNode to currentNode
        if (currentNodeDist < currentSmallestDist) {
            currentSmallestDist = currentNodeDist;
            currentNearest = node;
        }

        double p_split_val, p_other_val, node_split_val, node_other_val;

        if (vertical) {
            p_split_val = p.x();
            node_split_val = node.x;
            p_other_val = p.y();
            node_other_val = node.y;
        } else {
            p_split_val = p.y();
            node_split_val = node.y;
            p_other_val = p.x();
            node_other_val = node.x;
        }


        if (p_split_val < node_split_val) {
            // search left tree first if the point is to the left of the current node
            Node leftNearest = nearest(node.left, p, currentNearest, currentSmallestDist, !vertical);
            double leftNearestDist = distSquare(leftNearest, p);

            // if left nearest is closer, update
            if (leftNearestDist < currentSmallestDist) {
                currentSmallestDist = leftNearestDist;
                currentNearest = leftNearest;
            }

            // if current nearest is greater than currentNodeDistToSplit, search right subtree;
            if (currentSmallestDist > currentNodeDistToSplit) {
                Node rightNearest = nearest(node.right, p, currentNearest, currentSmallestDist, !vertical);
                double rightNearestDist = distSquare(rightNearest, p);
                if (rightNearestDist < currentSmallestDist) {
                    currentNearest = rightNearest;
                }
            }
        } else {
            // return immediately if the point overlap
            if (p_split_val == node_split_val && p_other_val == node_other_val) return node;

            // else if p_split_val >= node_split_val, search right tree first
            Node rightNearest = nearest(node.right, p, currentNearest, currentSmallestDist, !vertical);
            double rightNearestDist = distSquare(rightNearest, p);

            // if left nearest is closer, update
            if (rightNearestDist < currentSmallestDist) {
                currentSmallestDist = rightNearestDist;
                currentNearest = rightNearest;
            }

            // if current nearest if greater than currentNodeDistX, search right subtree;
            if (currentSmallestDist > currentNodeDistToSplit) {
                Node leftNearest = nearest(node.left, p, currentNearest, currentSmallestDist, !vertical);
                double leftNearestDist = distSquare(leftNearest, p);
                if (leftNearestDist < currentSmallestDist) {
                    currentNearest = leftNearest;
                }
            }
        }

        return currentNearest;
    }


    public static void main(String[] args) {

        KdTree ps = new KdTree();

        assert ps.size() == 0;
        assert ps.isEmpty();
        assert !ps.contains(new Point2D(2, 1));
        assert ps.nearest(new Point2D(0.8, 0.5)) == null;

        assert ((Stack<Point2D>) ps.range(new RectHV(0, 0, 1, 1))).isEmpty();

        ps.insert(new Point2D(0.1,0.2));
        ps.insert(new Point2D(0.5,0.6));
        assert ps.size() == 2;
        assert !ps.isEmpty();
        assert ps.contains(new Point2D(0.5, 0.6));
        assert !ps.contains(new Point2D(1.5, 0.6));
        assert ps.nearest(new Point2D(0.1, 0.2)).equals(new Point2D(0.1, 0.2));
        assert ps.nearest(new Point2D(0.2, 0.2)).equals(new Point2D(0.1, 0.2));
        assert ps.nearest(new Point2D(0.5, 0.6)).equals(new Point2D(0.5, 0.6));
        assert ps.nearest(new Point2D(0.4, 0.6)).equals(new Point2D(0.5, 0.6));

        assert ((Stack<Point2D>) ps.range(new RectHV(0, 0, 1, 1))).size() == 2;

        ps.insert(new Point2D(0.9,0.9));
        ps.insert(new Point2D(0.8,0.5));
        assert ps.size() == 4;
        assert !ps.isEmpty();
        assert ps.contains(new Point2D(0.8, 0.5));
        assert ps.contains(new Point2D(0.1, 0.2));
        assert !ps.contains(new Point2D(0.1, 2.2));
        assert ps.nearest(new Point2D(0.9, 0.9)).equals(new Point2D(0.9, 0.9));
        assert ps.nearest(new Point2D(1, 1)).equals(new Point2D(0.9, 0.9));
        assert ps.nearest(new Point2D(0.8, 0.5)).equals(new Point2D(0.8, 0.5));
        assert ps.nearest(new Point2D(0.7, 0.6)).equals(new Point2D(0.8, 0.5));

        assert ((Stack<Point2D>) ps.range(new RectHV(0, 0, 1, 1))).size() == 4;
        assert ((Stack<Point2D>) ps.range(new RectHV(0, 0, 0.1, 1))).size() == 1;
        assert ((Stack<Point2D>) ps.range(new RectHV(0, 0, 0.5, 1))).size() == 2;
        assert ((Stack<Point2D>) ps.range(new RectHV(0.5, 0.5, 0.8, 0.8))).size() == 2;

        // insert a repeated point
        ps.insert(new Point2D(0.8,0.5));
        assert ps.size() == 4;
        assert !ps.isEmpty();
        assert ps.contains(new Point2D(0.8, 0.5));

//        StdDraw.setPenColor(StdDraw.BLACK);
//        StdDraw.setPenRadius(0.01);
//        ps.draw();

        KdTree ps2 = new KdTree();

        ps2.insert(new Point2D(0.375, 0.625));
        assert ps2.size() == 1;

        ps2.insert(new Point2D(1.0, 1.0));
        assert ps2.size() == 2;
        ps2.insert(new Point2D(0.0, 0.875));
        assert ps2.size() == 3;
        ps2.insert(new Point2D(0.75, 0.5));
        assert ps2.size() == 4;
        ps2.insert(new Point2D(0.875, 0.125));
        assert ps2.size() == 5;

        assert ps2.contains(new Point2D(0.875, 0.125));

        System.out.println(-5 % 2);
    }

}
