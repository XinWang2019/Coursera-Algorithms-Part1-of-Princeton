import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Percolation {

    private WeightedQuickUnionUF connectRecord;    // record the connectivity of the map
    private WeightedQuickUnionUF normalConnect;    // no bottom site
    private int[] openRecord;    // record whether or not the site is open
    private int size;    // the dimension of the grid
    private int openCount;    // the number of open sites

    // create n-by-n grid, with all sites initiallly blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("The size of the map is smaller than 0!");
        }
        openRecord = new int[n * n
                + 2];    // add two assistant site at the beginning and the end of the array
        openRecord[0] = 1;
        openRecord[n * n + 1] = 1;    // the assistant site should keep open
        for (int i = 1; i < n * n + 1; i++) {
            openRecord[i] = 0;
        }
        connectRecord = new WeightedQuickUnionUF(n * n + 2);
        normalConnect = new WeightedQuickUnionUF(n * n + 1);
        size = n;
        openCount = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > size)
            throw new IllegalArgumentException("The row index is out of range!");
        if (col < 1 || col > size)
            throw new IllegalArgumentException("The column index is out of range!");
        int index = (row - 1) * size + col;
        if (openRecord[index] == 1) return;
        openRecord[index] = 1;
        openCount += 1;

        // to deal with the union with elements above and below
        if (row == 1) {
            connectRecord.union(index, 0);
            normalConnect.union(index, 0);
            if (openRecord[index + size] == 1) {
                connectRecord.union(index, index + size);
                normalConnect.union(index, index + size);
            }
        }
        else if (row == size) {
            connectRecord.union(index, size * size + 1);
            if (openRecord[index - size] == 1) {
                connectRecord.union(index, index - size);
                normalConnect.union(index, index - size);
            }
        }
        else {
            if (openRecord[index - size] == 1) {
                connectRecord.union(index, index - size);
                normalConnect.union(index, index - size);
            }
            if (openRecord[index + size] == 1) {
                connectRecord.union(index, index + size);
                normalConnect.union(index, index + size);
            }
        }

        // to deal with the union with elements before and after
        if (col != 1) {
            if (openRecord[index - 1] == 1) {
                connectRecord.union(index, index - 1);
                normalConnect.union(index, index - 1);
            }
        }

        if (col != size) {
            if (openRecord[index + 1] == 1) {
                connectRecord.union(index, index + 1);
                normalConnect.union(index, index + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > size)
            throw new IllegalArgumentException("The row index is out of range!");
        if (col < 1 || col > size)
            throw new IllegalArgumentException("The column index is out of range!");
        int index = (row - 1) * size + col;
        return openRecord[index] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > size)
            throw new IllegalArgumentException("The row index is out of range!");
        if (col < 1 || col > size)
            throw new IllegalArgumentException("The column index is out of range!");
        int index = (row - 1) * size + col;
        return normalConnect.find(index) == normalConnect.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolates?
    public boolean percolates() {
        return connectRecord.find(0) == connectRecord.find(size * size + 1);
    }

    // test client
    public static void main(String[] args) throws IOException {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("input20.txt"));
        }
        catch (FileNotFoundException e) {
            StdOut.printf("File not exist!\n");
            return;
        }

        int arg0;
        int arg1 = 0;
        // read in the size
        String lineContent;
        lineContent = br.readLine();
        String[] stringContent = lineContent.split(" ");
        arg0 = Integer.parseInt(stringContent[0]);
        Percolation map = new Percolation(arg0);
        while ((lineContent = br.readLine()) != null) {
            stringContent = lineContent.split("\\s+");
            StdOut.printf("%s, arg0: %s, arg1: %s, arg2: %s\n", lineContent, stringContent[0],
                          stringContent[1], stringContent[2]);
            if (stringContent.length != 3) continue;
            arg0 = Integer.parseInt(stringContent[1]);
            arg1 = Integer.parseInt(stringContent[2]);
            map.open(arg0, arg1);
        }
        StdOut.printf("%b, %b, %d, %d\n", map.isFull(5, 1), map.isFull(5, 1), arg0, arg1);


    }
}
