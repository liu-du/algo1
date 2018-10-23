package graph;

import edu.princeton.cs.algs4.Queue;

public class BreadthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;

    public BreadthFirstPaths(Graph g, int s) {
        bfs(g, s);
    }


    private void bfs(Graph g, int v) {
        Queue<Integer> q = new Queue<>();

        q.enqueue(v);
        marked[v] = true;
        while (!q.isEmpty()) {
            int w = q.dequeue();
            for (int i : g.adj(w)) {
                if (!marked[i]) {
                    marked[i] = true;
                    edgeTo[i] = w;
                }
                q.enqueue(i);
            }
        }

    }
}
