/* *****************************************************************************
 *  Name: Xin Wang
 *  Date: 6/16/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public class KdTree {
    private enum XorY {
        VERTICAL, HORIZONTAL
    }

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("no null argument in KdTree.insert()!");
        if (root == null) {
            RectHV rect = new RectHV(0, 0, 1, 1);
            root = put(null, p, XorY.HORIZONTAL, rect);
        }
        else root = put(root, p, XorY.HORIZONTAL, root.rect());
    }

    private Node put(Node r, Point2D p, XorY xory, RectHV rect) {
        if (r == null) {
            size++;
            return new Node(p, xory, rect);
        }
        XorY nextxory = (r.xory == XorY.HORIZONTAL) ? XorY.VERTICAL : XorY.HORIZONTAL;
        if (r.compareTo(p) < 0) r.right = put(r.right, p, nextxory, r.rectRight());
        else if (r.compareTo(p) > 0) r.left = put(r.left, p, nextxory, r.rectLeft());
        else {
            if (r.point().equals(p)) return r;
            else r.left = put(r.left, p, nextxory, r.rectLeft());
        }
        return r;
    }


    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("No null argument in KdTree.contains()!");
        Node curr = root;
        while (curr != null) {
            if (curr.compareTo(p) < 0) curr = curr.right;
            else if (curr.compareTo(p) > 0) curr = curr.left;
            else {
                if (curr.point().equals(p)) return true;
                else curr = curr.left;
            }
        }
        return false;
    }


    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenRadius(StdDraw.getPenRadius() / 5);
        StdDraw.line(0, 0, 0, 1);
        StdDraw.line(0, 0, 1, 0);
        StdDraw.line(0, 1, 1, 1);
        StdDraw.line(1, 0, 1, 1);
        StdDraw.setPenRadius(StdDraw.getPenRadius() * 5);

        double[] area = { 0, 0, 1, 1 };
        draw(root, area);
    }

    private void draw(Node n, double[] area) {
        if (n != null) {
            n.point().draw();
            double[][] areas = n.drawCutLine(area);
            draw(n.left, areas[0]);
            draw(n.right, areas[1]);
        }
    }


    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("No null argument in KdTree.range()!");

        List<Point2D> targets = new LinkedList<>();
        range(root, targets, rect);
        return targets;
    }

    private void range(Node n, List<Point2D> targets, RectHV rect) {
        if (n == null) return;
        if (n.compareToRectHV(rect) < 0) range(n.left, targets, rect);
        else if (n.compareToRectHV(rect) > 0) range(n.right, targets, rect);
        else {
            if (rect.contains(n.point())) {
                targets.add(n.point());
            }
            range(n.left, targets, rect);
            range(n.right, targets, rect);
        }
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("No null argument in KdTree.contains()!");
        if (root == null) return null;
        else return nearest(root, p, root.point());
    }

    private Point2D nearest(Node n, Point2D p, Point2D closest) {
        if (n == null) return closest;

        if (n.rect().distanceTo(p) <= p.distanceTo(closest)) {
            if (p.distanceTo(n.point()) < p.distanceTo(closest)) {
                closest = n.point();
            }

            if (n.compareTo(p) < 0) {
                closest = nearest(n.right, p, closest);
                closest = nearest(n.left, p, closest);
            }
            else {
                closest = nearest(n.left, p, closest);
                closest = nearest(n.right, p, closest);
            }
        }

        return closest;
    }

    private class Node implements Comparable<Point2D> {
        private final Point2D p;
        private final XorY xory;
        private final RectHV rect;
        private final RectHV rectleft;
        private final RectHV rectright;
        private Node left = null;
        private Node right = null;

        public Node(Point2D p, XorY xory, RectHV rect) {
            this.p = p;
            this.xory = xory;
            this.rect = rect;

            if (xory == XorY.HORIZONTAL) {
                rectleft = new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
                rectright = new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            }
            else {
                rectleft = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
                rectright = new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
            }
        }

        public Point2D point() {
            Point2D point = new Point2D(p.x(), p.y());
            return point;
        }

        public RectHV rect() {
            return rect;
        }

        public RectHV rectLeft() {
            return rectleft;
        }

        public RectHV rectRight() {
            return rectright;
        }

        public int compareTo(Point2D p1) {
            if (xory == XorY.HORIZONTAL) {
                return Double.compare(p.x(), p1.x());
            }
            else {
                return Double.compare(p.y(), p1.y());
            }
        }

        public int compareToRectHV(RectHV queryrect) {
            if (xory == XorY.HORIZONTAL) {
                if (queryrect.xmax() < p.x()) return -1;
                if (queryrect.xmin() > p.x()) return 1;
                return 0;
            }
            else {
                if (queryrect.ymax() < p.y()) return -1;
                if (queryrect.ymin() > p.y()) return 1;
                return 0;
            }
        }

        public double[][] drawCutLine(double[] area) {
            double penr = StdDraw.getPenRadius();
            StdDraw.setPenRadius(penr / 5);
            Color color = StdDraw.getPenColor();

            double[][] areas = new double[2][4];
            areas[0] = area.clone();
            areas[1] = area.clone();
            if (xory == XorY.HORIZONTAL) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(p.x(), area[1], p.x(), area[3]);
                areas[0][2] = p.x();
                areas[1][0] = p.x();
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(area[0], p.y(), area[2], p.y());
                areas[0][3] = p.y();
                areas[1][1] = p.y();
            }

            StdDraw.setPenRadius(penr);
            StdDraw.setPenColor(color);

            return areas;
        }
    }

    // unit testing of the methods
    public static void main(String[] args) {
        In in = new In(args[0]);
        KdTree d2tree = new KdTree();
        double[] positions = in.readAllDoubles();
        for (int i = 0; i < positions.length; i = i + 2) {
            Point2D point = new Point2D(positions[i], positions[i + 1]);
            d2tree.insert(point);
        }
        Point2D point = new Point2D(0.477, 0.873);
        StdDraw.setScale(-0.1, 1.1);
        StdDraw.setPenRadius(0.01);
        d2tree.draw();

        StdDraw.setPenRadius(0.015);
        StdDraw.setPenColor(StdDraw.RED);
        point.draw();

        // check KdTree.nearest()
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.GREEN);
        point = d2tree.nearest(point);
        point.draw();

       /* // check KdTree.range()
        RectHV rect = new RectHV(0, 0.2, 1, 0.5);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        rect.draw();

        Iterable<Point2D> pointsinrange = d2tree.range(rect);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.YELLOW);
        for (Point2D p : pointsinrange) {
            p.draw();
        }*/
        StdDraw.show();
    }
}
