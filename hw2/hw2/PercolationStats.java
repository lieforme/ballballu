package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int length;
    private int[] result;

    Percolation experiments;
//    public PercolationStats(int N, int T, PercolationFactory pf)   // perform T independent experiments on an N-by-N grid
//    public double mean()                                           // sample mean of percolation threshold
//    public double stddev()                                         // sample standard deviation of percolation threshold
//    public double confidenceLow()                                  // low endpoint of 95% confidence interval
//    public double confidenceHigh()                                 // high endpoint of 95% confidence interval

    public PercolationStats(int N, int T, PercolationFactory pf) {
        length = N;
        result = new int[T];

        for(int i = 0; i < T; i++) {
            experiments = pf.make(N);
            result[i] = Experiment();
        }
    }

    private int Experiment() {
        int row, col;
        while(experiments.percolates() == false) {
            do
            {
                row = StdRandom.uniform(length);
                col = StdRandom.uniform(length);
            }while(experiments.isOpen(row, col) == true);

            experiments.open(row, col);
        }
        return experiments.numberOfOpenSites();
    }

    public double mean() {
        return StdStats.mean(result);
    }

    public double stddev() {
        return StdStats.stddev(result);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(result.length);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(result.length);
    }

}
