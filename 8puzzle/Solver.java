/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Solver {

    private boolean issolvable = true;
    private final int moves;
    private final Node end;

    // find a solution to the initial board? (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null)
            throw new IllegalArgumentException(("The initial Board can not be null!"));

        // initialize the queues
        MinPQ<Node> twinQ = new MinPQ<>(
                new ManhattanPriority());    // assistant queue to find out whether or not initial is solvable
        MinPQ<Node> mainQ = new MinPQ<>(new ManhattanPriority());
        mainQ.insert(new Node(initial, null, 0));
        twinQ.insert(new Node(initial.twin(), null, 0));

        // to find out whether the initial board is solvable and solve the problem
        Node minnode1 = mainQ.delMin();
        Node minnode2 = twinQ.delMin();
        while (!minnode1.board.isGoal()) {
            if (minnode2.board.isGoal()) {
                issolvable = false;
                break;
            }

            int currentmoves = minnode1.moves;
            // if (currentmoves > 21)
            // StdOut.println("current moves:" + currentmoves + "****" + minnode1.board);
            currentmoves++;
            for (Board neighbor : minnode1.board.neighbors()) {
                if (minnode1.prev != null) {
                    if (neighbor.equals(minnode1.prev.board)) continue;
                }
                mainQ.insert(new Node(neighbor, minnode1, currentmoves));
            }
            for (Board neighbor : minnode2.board.neighbors()) {
                if (minnode2.prev != null) {
                    if (neighbor.equals(minnode2.prev.board)) continue;
                }
                twinQ.insert(new Node(neighbor, minnode2, currentmoves));
            }
            minnode1 = mainQ.delMin();
            minnode2 = twinQ.delMin();
        }
        if (issolvable) {
            end = minnode1;
            moves = minnode1.moves;
        }
        else {
            end = null;
            moves = 0;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return issolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (issolvable) return moves;
        else return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!issolvable) return null;
        Board[] trace = new Board[moves + 1];
        Node prevnode = end;
        for (int i = moves + 1; i > 0; i--) {
            trace[i - 1] = prevnode.board;
            prevnode = prevnode.prev;
        }
        return Arrays.asList(trace);
    }

    private class Node {
        public final Board board;
        public final Node prev;
        public final int moves;
        public final int priority;

        public Node(Board board, Node prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
            this.priority = moves + board.manhattan();
        }
    }

    /* private static class HammingPriority implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return Integer.compare(a.priority, b.priority);
        }
    }*/

    private static class ManhattanPriority implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return Integer.compare(a.priority, b.priority);
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
