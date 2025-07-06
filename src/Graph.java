import java.util.*;

public class Graph {
    int V;
    List<Edge> edges;

    public Graph(int V) {
        this.V = V;
        this.edges = new ArrayList<>();
    }

    public void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
