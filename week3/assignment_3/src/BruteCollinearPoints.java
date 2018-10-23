import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class BruteCollinearPoints {

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

    public BruteCollinearPoints(Point[] points)
    {
        // argument checking;
        if (points == null)
            throw new IllegalArgumentException("null arguments.");
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
        Arrays.sort(this.points); // sort by y cord.

        if (lessThanFour) {
            this.lineSegments = new LineSegment[0];
            this.count = 0;
        } else {

            lineSegments = new LineSegment[1];
            lineSegmentsLength = lineSegments.length;

            for (int i = 0; i < p_length; i++) {
                for (int j = i + 1; j < p_length; j++) {
                    for (int k = j + 1; k < p_length; k++) {
                        for (int l = k + 1; l < p_length; l++) {

                            double slopeJ = this.points[i].slopeTo(this.points[j]);
                            double slopeK = this.points[i].slopeTo(this.points[k]);
                            double slopeL = this.points[i].slopeTo(this.points[l]);

                            if (slopeJ == slopeK && slopeK == slopeL) {
                                if (count >= lineSegmentsLength) doublesize();
                                lineSegments[count++] = new LineSegment(this.points[i], this.points[l]);
                            }
                        }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        System.out.println("finish constructing");
        System.out.print(collinear.count);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
