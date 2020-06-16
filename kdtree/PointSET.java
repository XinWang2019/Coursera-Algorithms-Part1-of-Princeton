/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PointSET {
    private SET<Point2D> pointset;

    // construct an empty set of points
    public PointSET() {
        pointset = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointset.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointset.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        pointset.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("No null argument in PointSET.contains()!");
        return pointset.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        Iterator<Point2D> points;
        points = pointset.iterator();
        while (points.hasNext()) {
            Point2D p = points.next();
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("No null argument in PointSET.range()!");
        List<Point2D> targets = new LinkedList<>();
        Iterator<Point2D> points;
        points = pointset.iterator();
        while (points.hasNext()) {
            Point2D p = points.next();
            if (rect.contains(p)) {
                targets.add(p);
            }
        }
        return targets;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("No null argument in PointSET.contains()!");
        Iterator<Point2D> points;
        Point2D target = null;
        points = pointset.iterator();
        while (points.hasNext()) {
            Point2D curr = points.next();
            if (target == null) target = curr;
            else if (p.distanceTo(curr) < p.distanceTo(target)) {
                target = curr;
            }
        }
        return target;
    }

    // unit testing of the methods
    public static void main(String[] args) {
        In in = new In(args[0]);
        PointSET pointset = new PointSET();
        double[] positions = in.readAllDoubles();
        for (int i = 0; i < positions.length; i = i + 2) {
            Point2D point = new Point2D(positions[i], positions[i + 1]);
            pointset.insert(point);
        }
        Point2D point = new Point2D(0.5, 0.9);
        StdDraw.setScale(-0.1, 1.1);
        StdDraw.setPenRadius(0.01);
        pointset.draw();

        StdDraw.setPenRadius(0.015);
        StdDraw.setPenColor(StdDraw.RED);
        point.draw();

        // check PointSET.nearest()
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.GREEN);
        point = pointset.nearest(point);
        point.draw();

        // check PointSET.range()
        RectHV rect = new RectHV(0, 0.2, 1, 0.5);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        rect.draw();

        Iterable<Point2D> pointsinrange = pointset.range(rect);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.YELLOW);
        for (Point2D p : pointsinrange) {
            p.draw();
        }
        StdDraw.show();
    }
}
