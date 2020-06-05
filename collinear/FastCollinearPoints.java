/* *****************************************************************************
 *  Name: Xin Wang
 *  Date: 05/26/2020
 **************************************************************************** */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
***********************************************************
 *  disable the comment if you want to run the main function
 *
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
*/


public class FastCollinearPoints {
    private final LineSegment[] linesegmentlist;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Input array of points can not be null!");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("The points in the array shouldn't be null!");
        }
        Point[] pointscopy = points.clone();
        Arrays.sort(pointscopy);    // enable the finding of duplicate points and remove repeated
        for (int i = 0; i < pointscopy.length - 1; i++) {
            if (pointscopy[i].compareTo(pointscopy[i + 1]) == 0)
                throw new IllegalArgumentException("No duplicate points are allowed!");
        }

        List<LineSegment> lineSegmentlisttemp
                = new LinkedList<>();    // store the valid line segments

        for (int i = 0; i < pointscopy.length; i++) {
            Point[] temp = pointscopy.clone();
            Arrays.sort(temp, pointscopy[i].slopeOrder());

            int count = 2;    // number of points on the line segment

            for (int j = 1; j < temp.length; j++) {
                boolean equal = j + 1 < temp.length
                        &&
                        (Double.compare(temp[j].slopeTo(pointscopy[i]),
                                        temp[j + 1].slopeTo(pointscopy[i])) == 0);
                if (equal) {
                    count += 1;
                }
                // the line segment ends
                else {
                    if (count > 3) {
                        // find all the points on this valid line segments
                        Point[] candidate = new Point[count];
                        candidate[0] = temp[0];
                        int m = count - 1;
                        int kk = j;
                        for (; m > 0; m--) {
                            candidate[m] = temp[kk];
                            kk--;
                        }

                        // check whether or not this line segment has been found before
                        LineSegment newline = endpoints(candidate);
                        boolean exist = candidate[0].compareTo(candidate[1]) > 0;

                        if (!exist) {
                            lineSegmentlisttemp.add(newline);
                        }
                    }
                    count = 2;
                }
            }
        }
        linesegmentlist = lineSegmentlisttemp.toArray(new LineSegment[0]);
    }

    // find the smallest and biggest points
    private LineSegment endpoints(Point[] line) {
        Point[] result = new Point[2];
        result[0] = line[0];
        result[1] = line[0];
        for (int i = 1; i < line.length; i++) {
            if (line[i].compareTo(result[0]) < 0) {
                result[0] = line[i];
            }
            if (line[i].compareTo(result[1]) > 0) {
                result[1] = line[i];
            }
        }
        return new LineSegment(result[0], result[1]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return linesegmentlist.length;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] list = new LineSegment[linesegmentlist.length];
        for (int i = 0; i < linesegmentlist.length; i++) list[i] = linesegmentlist[i];
        return list;
    }

    public static void main(String[] args) /* throws IOException*/ {
        /*
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("input50.txt"));
        }
        catch (FileNotFoundException e) {
            StdOut.printf("File not exist!\n");
            return;
        }

        int arg0;
        int arg1 = 0;
        int i = 0;
        int k;

        // read in the size
        String lineContent;
        lineContent = br.readLine();
        String[] stringContent = lineContent.split("\\s+", 0);
        arg0 = Integer.parseInt(stringContent[0]);
        Point[] points = new Point[arg0];

        // read in the points
        while ((lineContent = br.readLine()) != null) {
            stringContent = lineContent.split("\\s+", 0);
            for (k = 0; k < stringContent.length; k++) {
                if (stringContent[k].length() != 0) {
                    arg0 = Integer.parseInt(stringContent[k]);
                    k++;
                    break;
                }
            }
            for (; k < stringContent.length; k++) {
                if (stringContent[k].length() != 0) {
                    arg1 = Integer.parseInt(stringContent[k]);
                    break;
                }
            }
            points[i] = new Point(arg0, arg1);
            i++;
        }

        Arrays.sort(points, points[0].slopeOrder());
        FastCollinearPoints test = new FastCollinearPoints(points);
        LineSegment[] lineresult = test.segments();
        StdDraw.setScale(0, 40000);
        for (int j = 0; j < lineresult.length; j++) {
            lineresult[j].draw();
        }
        StdOut.printf("Number of line segments: %d, %b\n", test.numberOfSegments(),
                      Double.compare(Double.POSITIVE_INFINITY, 0 - Double.NEGATIVE_INFINITY) == 0);
        StdDraw.show();
        
         */
    }
}
