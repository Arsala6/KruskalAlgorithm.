import java.util.*;

public class Kruskal {
    int[] parent;

    public int find(int i) {
        if (parent[i] == i) return i;
        return parent[i] = find(parent[i]);  // path compression
    }

    public void union(int x, int y) {
        parent[find(x)] = find(y);
    }

    public List<Edge> runKruskal(Graph graph) {
        List<Edge> result = new ArrayList<>();
        Collections.sort(graph.getEdges());

        parent = new int[graph.V];
        for (int i = 0; i < graph.V; i++) parent[i] = i;

        for (Edge edge : graph.getEdges()) {
            int x = find(edge.src);
            int y = find(edge.dest);

            if (x != y) {
                result.add(edge);
                union(x, y);
            }
        }
        return result;
    }
}
