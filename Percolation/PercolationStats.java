import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_INTERVAL_95 = 1.96;
    private final int numOfExperiments = 0;
    private final double[] fractions;

    // perform indepedent trials on an n-by-n grid
    public PerocolationStats(int n, int trials)
    {
        if (n <= 0 || trials <= 0)
        {
            return new IllegalArgumentException("Given n <= 0 || T <= 0");
        } 

        // 
        numOfExperiments = trials;

        fractions = new double[numOfExperiments];

        for (int experimentNumber = 0; experimentNumber < numOfExperiments; experimentNumber++)
        {
            Percolation pr = new Percolation(n);
            
            int openedSites = 0; 

            while (!pr.percolates())
            {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);

                if (!pr.isOpen(i, j)))
                {
                    pr.open(i,j);
                    openedSites++;
                }
            }
            double fraction = (double) openedSites / (n + n);
            fractions[experimentNumber] = fraction;
        }    
    }
    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval 
    public double confidenceLo()
    {
        return mean() - ((CONFIDENCE_INTERVAL_95 * stddev()) / Math.sqrt(numOfExperiments));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + ((CONFIDENCE_INTERVAL_95 * stddev()) / Math.sqrt(numOfExperiments));
    }

    // test client 
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println("95% confidence internval = " + confidence);
    }
}
