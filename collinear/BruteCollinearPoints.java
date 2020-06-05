/* *****************************************************************************
 *  Name: Xin Wang
 *  Date: 05/26/2020
 **************************************************************************** */

public class BruteCollinearPoints {
    private final LineSegment[] linesegmentlist;

    // find all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        LineSegment[] linesegmentlisttemp = { };
        if (points == null)
            throw new IllegalArgumentException("Input array of points can not be null!");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("The points in the array shouldn't be null!");
        }
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("No equal points are allowed!");
            }

        for (int pi = 0; pi < points.length; pi++) {
            for (int qi = pi + 1; qi < points.length; qi++) {
                for (int ri = qi + 1; ri < points.length; ri++) {
                    for (int si = ri + 1; si < points.length; si++) {
                        if (Double.compare(points[qi].slopeTo(points[pi]),
                                           points[ri].slopeTo(points[pi])) == 0
                                &&
                                Double.compare(points[ri].slopeTo(points[pi]), points[si]
                                        .slopeTo(points[pi])) == 0) {
                            Point[] candidate = {
                                    points[pi], points[qi], points[ri], points[si]
                            };
                            LineSegment[] temp = new LineSegment[linesegmentlisttemp.length + 1];
                            for (int i = 0; i < linesegmentlisttemp.length; i++) {
                                temp[i] = linesegmentlisttemp[i];
                            }
                            temp[linesegmentlisttemp.length] = endpoints(candidate);
                            linesegmentlisttemp = temp;
                        }
                    }
                }
            }
        }
        linesegmentlist = linesegmentlisttemp;
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

    public static void main(String[] args) {
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
            StdOut.printf("%s, %d, %d\n", lineContent, arg0, arg1);
            points[i] = new Point(arg0, arg1);
            i++;
        }

        BruteCollinearPoints test = new BruteCollinearPoints(points);
        LineSegment[] lineresult = test.linesegmentlist;
        StdDraw.setScale(0, 40000);
        for (int j = 0; j < lineresult.length; j++) {
            lineresult[j].draw();
        }
        StdOut.printf("Number of line segments: %d\n", test.numberOfSegments());*/
    }
}
