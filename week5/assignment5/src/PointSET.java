import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> ps;

    public PointSET() {
        ps = new TreeSet<>();
    }

    public boolean isEmpty() {
        return ps.isEmpty();
    }

    public int size() {
        return ps.size();
    }

    public void insert(Point2D p) {
        ps.add(p);
    }

    public boolean contains(Point2D p) {
        return ps.contains(p);
    }

    public void draw() {
        for (Point2D p : ps) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
       Stack<Point2D> stack = new Stack<>();

       for (Point2D p : ps) {
           if (p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin() && p.y() <= rect.ymax())
               stack.push(p);
       }

       return stack;
    }

    public Point2D nearest(Point2D p) {

        Point2D out = null;
        double currentNearest = Double.POSITIVE_INFINITY;

        for (Point2D point : ps) {
            double dist = point.distanceSquaredTo(p);
            if (dist < currentNearest) {
                currentNearest = dist;
                out = point;
            }
        }

        return out;
    }

    public static void main(String[] args) {

        PointSET ps = new PointSET();

        assert ps.size() == 0;
        assert ps.isEmpty();
        assert !ps.contains(new Point2D(2, 1));

        ps.insert(new Point2D(0.1,0.2));
        ps.insert(new Point2D(0.5,0.6));

        assert ps.size() == 2;
        assert !ps.isEmpty();
        assert ps.contains(new Point2D(0.5, 0.6));

        ps.insert(new Point2D(0.9,0.9));
        ps.insert(new Point2D(0.8,0.5));

        assert ps.size() == 4;
        assert !ps.isEmpty();
        assert ps.contains(new Point2D(0.8, 0.5));

        assert ps.nearest(new Point2D(0.55, 0.61)).equals(new Point2D(0.5, 0.6));

        for (Point2D p : ps.range(new RectHV(0.05, 0.05, 0.81, 0.81))) {
            System.out.println(p);
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        ps.draw();

    }

}
