/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private final int[][] tiles;
    private int hammingdistance;
    private int manhattandistance;
    private int blankrow;
    private int blankcol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        // check the validity of tiles
        if (tiles == null) throw new IllegalArgumentException("null value is not allowed!");
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].length != tiles.length) throw
                    new IllegalArgumentException("Illegal dimension for Board!");
        }
        this.tiles = copyOf(tiles);

        // calulate the hamming distance and get the position of blank tile
        this.hammingdistance = 0;
        int n = tiles.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // get the position of blank tile
                if (tiles[i][j] == 0) {
                    blankrow = i;
                    blankcol = j;
                    continue;
                }

                if (i != n - 1 || j != n - 1) {
                    if (tiles[i][j] != (i * n + j + 1)) {
                        this.hammingdistance += 1;
                    }
                }
                else {
                    if (tiles[i][j] != 0) {
                        this.hammingdistance += 1;
                    }
                }
            }
        }

        // calculate the sum of Manhattan distances between tiles and goal
        this.manhattandistance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != 0) {
                    int row = (tiles[i][j] - 1) / n;
                    int col = (tiles[i][j] - 1) % n;
                    this.manhattandistance += Math.abs(i - row) + Math.abs(j - col);
                }
                else {
                    this.manhattandistance += 2 * n - 2 - i - j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder tilesstr = new StringBuilder();
        tilesstr.append(Integer.toString(tiles.length));
        tilesstr.append("\n");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tilesstr.append(String.format("%2d ", tiles[i][j]));
            }
            tilesstr.append("\n");
        }

        return tilesstr.toString();
    }

    // borard dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        return hammingdistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattandistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hammingdistance == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (that.tiles.length != this.tiles.length) return false;
        for (int i = 0; i < tiles.length; i++) {
            if (that.tiles[i].length != this.tiles[i].length) return false;
            for (int j = 0; j < this.tiles[i].length; j++) {
                if (that.tiles[i][j] != this.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Board[] neighbors;
        int[][] temptiles;
        int size = 4;
        int next = 0;

        if (blankrow == 0 || blankrow == this.tiles.length - 1) size--;
        if (blankcol == 0 || blankcol == this.tiles.length - 1) size--;

        neighbors = new Board[size];

        // swap with above
        if (blankrow != 0) {
            temptiles = swap(this.tiles, blankrow, blankcol, blankrow - 1, blankcol);
            neighbors[next] = new Board(temptiles);
            next++;
        }
        // swap with below
        if (blankrow != this.tiles.length - 1) {
            temptiles = swap(this.tiles, blankrow, blankcol, blankrow + 1, blankcol);
            neighbors[next] = new Board(temptiles);
            next++;
        }
        // swap with left
        if (blankcol != 0) {
            temptiles = swap(this.tiles, blankrow, blankcol, blankrow, blankcol - 1);
            neighbors[next] = new Board(temptiles);
            next++;
        }
        // swap with right
        if (blankcol != this.tiles.length - 1) {
            temptiles = swap(this.tiles, blankrow, blankcol, blankrow, blankcol + 1);
            neighbors[next] = new Board(temptiles);
        }

        return Arrays.asList(neighbors);
    }

    private int[][] copyOf(int[][] a) {
        int[][] copy = new int[a.length][];
        for (int i = 0; i < a.length; i++) {
            copy[i] = new int[a[i].length];
            for (int j = 0; j < a[i].length; j++) {
                copy[i][j] = a[i][j];
            }
        }
        return copy;
    }


    // a board that is obtained by exchanging any pair of blocks afk
    public Board twin() {
        int[][] copied;
        if (blankcol != 0) {
            copied = swap(this.tiles, 0, 0, 1, 0);
        }
        else {
            copied = swap(this.tiles, 0, 1, 1, 1);
        }
        return new Board(copied);
    }

    private int[][] swap(int[][] a, int x0, int y0, int x1, int y1) {
        int temp = a[x0][y0];
        int[][] result = copyOf(a);
        result[x0][y0] = result[x1][y1];
        result[x1][y1] = temp;
        return result;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board testboard = new Board(tiles);
        int[][] testtiles2 = { { 0, 1, 2 }, { 3, 7, 5 }, { 6, 4, 8 } };
        Board y = new Board(testtiles2);
        StdOut.println("Board:");
        StdOut.println(testboard);
        StdOut.println("dimension:");
        StdOut.println(testboard.dimension());
        StdOut.println("hamming:");
        StdOut.println(testboard.hamming());
        StdOut.println("manhattan:");
        StdOut.println(testboard.manhattan());
        StdOut.println("isGoal:");
        StdOut.println(testboard.isGoal());
        StdOut.println("equals:");
        StdOut.println(testboard.equals(y));
        StdOut.println("neighbors:");
        for (Board i : testboard.neighbors()) {
            StdOut.println(i);
        }
        StdOut.println("twin:");
        StdOut.println(testboard.twin());
    }
}
