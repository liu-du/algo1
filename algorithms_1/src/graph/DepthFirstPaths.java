package graph;

public class DepthFirstPaths {

    private boolean marked[];
    private int edgeTo[];
    private int s;

    public DepthFirstPaths(Graph g, int s) {
        this.s = s;
        int v = g.V();
        edgeTo = new int[v];
        marked = new boolean[v];
        for (int i = 0; i < v; i++) {
            edgeTo[i] = -1;
        }
        dfs(g, s);
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v))
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
            }
    }
}
