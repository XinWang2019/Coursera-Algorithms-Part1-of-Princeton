/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private int trials;
    private double[] results;    // the number of opened sites upon percolation
    private double meanOfOpened;
    private double stdDeviation;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        Percolation map;
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException(
                "Illegal value for the size of grid or number of trials!");

        this.trials = trials;
        this.results = new double[trials];

        for (int i = 0; i < trials; i++) {
            map = new Percolation(n);
            int row, col;
            while (!map.percolates()) {
                row = StdRandom.uniform(n) + 1;
                col = StdRandom.uniform(n) + 1;
                map.open(row, col);
            }
            this.results[i] = map.numberOfOpenSites() / (n * n * 1.0);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for (int i = 0; i < this.trials; i++) {
            sum += results[i];
        }
        this.meanOfOpened = sum / this.trials;
        return this.meanOfOpened;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double sum = 0;
        double dif;
        for (int i = 0; i < this.trials; i++) {
            dif = this.results[i] - this.meanOfOpened;
            sum += dif * dif;
        }

        if (this.trials <= 1) throw new IllegalArgumentException(
                "Too few experiments to give the standard deviation!");

        this.stdDeviation = sum / (this.trials - 1);
        return this.stdDeviation;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.meanOfOpened - 1.96 * this.stdDeviation / Math.sqrt(this.trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.meanOfOpened + 1.96 * this.stdDeviation / Math.sqrt(this.trials);
    }

    public static void main(String[] args) {
        if (args.length != 2) throw new IllegalArgumentException("Only two arguments is needed!");
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        Stopwatch timeConsumed = new Stopwatch();

        PercolationStats experiment = new PercolationStats(n, trials);
        StdOut.printf("%-23s = %f\n", "mean", experiment.mean());
        StdOut.printf("%-23s = %f\n", "stddev", experiment.stddev());
        StdOut.printf("%-23s = [%f, %f]\n", "95% confidence interval", experiment.confidenceLo(),
                      experiment.confidenceHi());

        StdOut.printf("Elapsed time: %f\n", timeConsumed.elapsedTime());
    }
}
