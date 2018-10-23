import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] opens;
    private final int virtialTop = 0;
    private final int virtialBottom;
    private final int N;
    private int numberOfOpens = 0;
    private WeightedQuickUnionUF uf;

    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        uf = new WeightedQuickUnionUF(n * n + 2);
        N = n;
        virtialBottom = n * n + 1;

        opens = new boolean[n * n + 2]; // plus two virtual sites

        for (int i = 1; i < virtialBottom; i++) {
            opens[i] = false;
        }
    }

    private int toIndex(int row, int col)
    {
        int index = N * (row - 1) + col;
        if (index < 1 || index >= virtialBottom) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        } else {
            return(index);
        }
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        int index = toIndex(row, col);

        // set to open if not
        if (!opens[index]) {

            opens[index] = true;

            // increment number of open sites
            numberOfOpens ++;

            // union with four neighbors if they are open

            if (row > 1 && isOpen(row - 1, col)) { // up
                uf.union(index, toIndex(row - 1, col));
            } else if (row == 1) { // union with virtualTop
                uf.union(index, virtialTop);
            }

            if (row < N && isOpen(row + 1, col)) { // down
                uf.union(index, toIndex(row + 1, col));
            } else if (row == N) { // union with virtualBottom
                uf.union(index, virtialBottom);
            }

            if (col > 1 && isOpen(row, col - 1)) uf.union(index, toIndex(row, col - 1)); // left

            if (col < N && isOpen(row, col + 1)) uf.union(index, toIndex(row, col + 1)); // right
        }
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        return opens[toIndex(row, col)];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        int i = toIndex(row, col);
        return uf.connected(i, virtialTop);
    }

    public int numberOfOpenSites()       // number of open sites
    {
        return numberOfOpens;
    }

    public boolean percolates()              // does the system percolate?
    {
        return uf.connected(virtialTop, virtialBottom);
    }

    public static void main(String[] args)   // test client (optional)
    {

        // instantiate
        Percolation perc = new Percolation(5);

        // open a site at (1, 1)
        perc.open(1,1);
        perc.open(2,1);
        perc.open(2,2);
        perc.open(3,2);

        perc.open(5,5);

        // test is (1, 1) open
        System.out.println(perc.isOpen(1, 1)); // true
        System.out.println(perc.isOpen(2, 1)); // true
        System.out.println(perc.isOpen(2, 2)); // true
        System.out.println(perc.isOpen(3, 2)); // true
        System.out.println(perc.isFull(3, 2)); // true

        System.out.println(perc.isOpen(3, 4)); // false
        System.out.println(perc.isFull(3, 3)); // false

        System.out.println(perc.isOpen(5, 5)); // true
        System.out.println(perc.isFull(5, 5)); // false

        System.out.println(perc.percolates()); // false
        perc.open(4,2);
        System.out.println(perc.percolates()); // false
        perc.open(5,2);
        System.out.println(perc.percolates()); // true

        // test numberOfOpenSites
        System.out.println(perc.numberOfOpenSites());
    }
}

