import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] probs;
    private int T;

    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        T = trials;
        double gridSize = n * n;
        probs = new double[T];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            }
            probs[i] = perc.numberOfOpenSites() / gridSize;
        }
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(probs);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(probs);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    public static void main(String[] args)        // test client (described below)
    {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, T);

        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");

    }
}