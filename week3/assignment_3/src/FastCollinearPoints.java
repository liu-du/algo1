import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private Point[] points;
    private LineSegment[] lineSegments;
    private boolean lessThanFour;
    private int count;
    private int lineSegmentsLength;

    private void doublesize() {
        LineSegment[] a = new LineSegment[count * 2];
        System.arraycopy(lineSegments, 0, a, 0, count);
        lineSegments = a;
        lineSegmentsLength = count * 2;
    }

    public FastCollinearPoints(Point[] points) {

        // argument checking;
        if (points == null) throw new IllegalArgumentException("null arguments.");

        int p_length = points.length;

        for (int i = 0; i < p_length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("null arguments.");
        }

        for (int i = 0; i < p_length; i++) {
            for (int j = i + 1; j < p_length; j++) {
                if (points[j].compareTo(points[i]) == 0)
                    throw new IllegalArgumentException("repeated points.");
            }
        }

        lessThanFour = p_length < 4;
        this.points = points.clone();

        if (lessThanFour) {
           this.lineSegments = new LineSegment[0];
           this.count = 0;
        } else {

            lineSegments = new LineSegment[1];
            lineSegmentsLength = lineSegments.length;

            for (int i = 0; i < p_length - 3; i++) {
                Point currentPoints = this.points[i];

                // sort from i + 1 to the end of the array by slope order wrt points[i]. O( N*log(N) )
                Arrays.sort(this.points, i + 1, p_length, currentPoints.slopeOrder());

                // slopes of the sorted wrt to current point
                double[] slopes = new double[p_length - i - 1];

                for (int j = 0; j < slopes.length; j++) { // O(N)
                    slopes[j] = currentPoints.slopeTo(this.points[j + i + 1]);
                }

                int current = 0;

                for (int k = 1; k <= slopes.length; k++) {

                    if (k == slopes.length || slopes[k] != slopes[current]) {

                        if (k - current >= 3) {
                            // found a set of collinear points
                            Point[] found = new Point[k - current + 1];

                            found[0] = this.points[i];
                            for (int l = 1; l < found.length; l++)
                                found[l] = this.points[i + current + l];

                            // sort by y-coordinates
                            Arrays.sort(found);

                            if (count >= lineSegmentsLength) doublesize();
                            lineSegments[count] = new LineSegment(found[0], found[k - current]);
                            count++;

                        }

                        // reset
                        current = k;
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        LineSegment[] a = new LineSegment[count];
        System.arraycopy(lineSegments, 0, a, 0, count);
        return a;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        System.out.print("draw finish");
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        System.out.println("finish constructing");
        System.out.println(collinear.segments().length);
        System.out.println(collinear.numberOfSegments());

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
