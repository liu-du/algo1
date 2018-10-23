import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Node solution;

    private class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private int priority;
        private Node predecessor;

        public Node(Board _board, int _moves, Node _predecessor) {
            board = _board;
            moves = _moves;
            predecessor = _predecessor;
            priority = _moves + _board.manhattan();
        }

        public int compareTo(Node that) {
            if (this.priority < that.priority) return -1;
            else if (this.priority == that.priority) return 0;
            else return 1;
        }
    }

    public Solver(Board initial) {

        if (initial == null) throw new IllegalArgumentException("initial board can't be null.");

        // initializing an empty MinPQ;
        MinPQ<Node> s = new MinPQ<>();
        MinPQ<Node> ts = new MinPQ<>();

        // construct the first Node
        s.insert( new Node(initial, 0, null) );
        ts.insert( new Node(initial.twin(), 0, null) );

        // dequeue the node with lowest priority
        Node current = s.delMin();
        Node tcurrent = ts.delMin();

        while (!current.board.isGoal() && !tcurrent.board.isGoal()) {

            if (current.predecessor == null) {
                for (Board bs : current.board.neighbors()) {
                    s.insert(new Node(bs, current.moves + 1, current));
                }
                for (Board tbs : tcurrent.board.neighbors()) {
                    ts.insert(new Node(tbs, tcurrent.moves + 1, tcurrent));
                }
            } else {
                for (Board bs : current.board.neighbors()) {
                    // ignore neighbor which is the same as predecessor
                    if (!bs.equals(current.predecessor.board)) {
                        s.insert(new Node(bs, current.moves + 1, current));
                    }
                }
                for (Board tbs : tcurrent.board.neighbors()) {
                    // ignore neighbor which is the same as predecessor
                    if (!tbs.equals(tcurrent.predecessor.board)) {
                        ts.insert(new Node(tbs, tcurrent.moves + 1, tcurrent));
                    }
                }

            }

            current = s.delMin();
            tcurrent = ts.delMin();
        }

        if (current.board.isGoal()) {
            solution = current;
        } else {
            solution = null;
        }
    }

    public boolean isSolvable() {
        return solution != null;
    }

    public int moves() {
        if (solution != null) {
            return solution.moves;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> bs = new Stack<>();

        Node current = solution;
        while (current != null) {
            bs.push(current.board);
            current = current.predecessor;
        }

        return bs;
    }

    public static void main(String[] args) {

        // create intial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }

        Board initial = new Board(blocks);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
