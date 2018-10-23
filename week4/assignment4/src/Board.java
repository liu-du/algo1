import edu.princeton.cs.algs4.Stack;

public class Board {

    private final int[][] board;
    private final int N;
    private int zeroRow;
    private int zeroCol;
    private int manhanttanDist;

    public Board(int[][] blocks) {
        board = blocks.clone();
        N = board.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
        manhattanCalc();
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int dist = 0;
        for (int i = 0; i < N * N; i++) {
            int row = i / N;
            int col = i % N;

            int num = board[row][col];

            if (num !=0 && num != i + 1) dist++;
        }
        return dist;
    }

    private static int abs(int n) {
        if (n < 0) n = -n;
        return n;
    }

    public int manhattan() {
        return manhanttanDist;
    }

    private void manhattanCalc() {
        int dist = 0;

        for (int i = 0; i < N * N; i++) {
            int row = i / N;
            int col = i % N;

            int num = board[row][col];

            if (num != 0) {
                int destRow = (num - 1) / N;
                int destCol = (num - 1) % N;

                dist = dist + abs(destCol - col) + abs(destRow - row);
            }
        }
        manhanttanDist = dist;
    }

    public boolean isGoal() {
        return manhanttanDist == 0;
    }

    public Board twin() {
        int[][] twin = new int[N][N];
        // clone is shadow!
        for (int i = 0; i < N; i++) {
            twin[i] = board[i].clone();
        }

        int i = 0;
        while (twin[i / N][i % N] == 0) i++;

        int j = i + 1;
        while (twin[j / N][j % N] == 0) j++;

        int swap = twin[i / N][i % N];
        twin[i / N][i % N] = twin[j / N][j % N];
        twin[j / N][j % N] = swap;

        return new Board(twin);
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Board that = (Board) obj;
        return this.toString().equals(that.toString());
    }

    private int[][] swapZero(int row, int col) {
        int[][] neighbor = new int[N][N];
        // clone is shadow!
        for (int i = 0; i < N; i++) {
            neighbor[i] = board[i].clone();
        }
        neighbor[zeroRow][zeroCol] = neighbor[row][col];
        neighbor[row][col] = 0;
        return neighbor;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();

        int[][] neighborIndex = new int[][] {{zeroRow - 1, zeroCol},
                                             {zeroRow + 1, zeroCol},
                                             {zeroRow, zeroCol - 1},
                                             {zeroRow, zeroCol + 1}};

        for (int i = 0; i < 4; i++) {
            int row = neighborIndex[i][0];
            int col = neighborIndex[i][1];
            if (row >= 0 && row < N && col >= 0 && col < N) {
                Board neighbor = new Board(swapZero(row, col));
                neighbors.push(neighbor);
            }
        }

        return neighbors;

    }

    public String toString() {
        StringBuilder s = new StringBuilder(N + "\n");

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }

        return s.toString();
    }

    public static void main(String[] args) {

        Board b = new Board(new int[][]
                {{1, 2, 3},
                 {4, 5, 6},
                 {7, 8, 0}} );
        assert b.isGoal();

        System.out.println(b);
        System.out.println(b.twin());

        System.out.println(b.zeroRow);
        System.out.println(b.zeroCol);

        System.out.println(b.hamming());
        System.out.println(b.manhattan());

        for (Board bs : b.neighbors()) {
            System.out.println(bs);
        }

        System.out.println("Board c");

        Board c = new Board(new int[][]
                       {{8, 1, 3},
                        {4, 0, 2},
                        {7, 6, 5}} );

        System.out.println(c);
        System.out.println(c.twin());

        System.out.println(c.hamming());
        System.out.println(c.manhattan());

        for (Board cs : c.neighbors()) {
            System.out.println(cs);
        }
        assert !c.isGoal();

        System.out.println("Board d");

        Board d = new Board(new int[][]
                       {{8, 0, 3},
                        {4, 1, 2},
                        {7, 6, 5}} );

        System.out.println(d);
        System.out.println(d.twin());

        for (Board ds : d.neighbors()) {
            System.out.println(ds);
        }

        assert !d.isGoal();

        assert b.equals(b);
        assert !b.equals(c);
        assert !b.equals(d);
        assert c.equals(c);
        assert d.equals(d);

    }
}
