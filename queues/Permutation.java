import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k;
        RandomizedQueue<String> test = new RandomizedQueue<>();
        k = Integer.parseInt(args[0]);

        // read in the strings
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            test.enqueue(s);
        }
        // print the string randomly
        for (int i = 0; i < k; i++) {
            StdOut.println(test.dequeue());
        }
    }
}
