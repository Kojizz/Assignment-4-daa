package graph.dagsp;
import java.util.*;

public class DAGShortestPaths {
    private final Map<Integer, List<int[]>> graph;

    public DAGShortestPaths(Map<Integer, List<int[]>> graph) {
        this.graph = graph;
    }

    public Map<Integer, Integer> shortestPath(int source, List<Integer> topo) {
        Map<Integer, Integer> dist = new HashMap<>();

        for (int u : graph.keySet()) {
            dist.put(u, Integer.MAX_VALUE);
            for (int[] edge : graph.get(u)) {
                int v = edge[0];
                dist.putIfAbsent(v, Integer.MAX_VALUE);
            }
        }
        dist.put(source, 0);

        for (int u : topo) {
            if (dist.get(u) != Integer.MAX_VALUE) {
                for (int[] edge : graph.getOrDefault(u, List.of())) {
                    int v = edge[0];
                    int w = edge[1];
                    if (dist.get(v) > dist.get(u) + w) {
                        dist.put(v, dist.get(u) + w);
                    }
                }
            }
        }
        return dist;
    }


    public Map<Integer, Integer> longestPath(int source, List<Integer> topo) {
        Map<Integer, Integer> dist = new HashMap<>();

        for (int u : graph.keySet()) {
            dist.put(u, Integer.MIN_VALUE);
            for (int[] edge : graph.get(u)) {
                int v = edge[0];
                dist.putIfAbsent(v, Integer.MIN_VALUE);
            }
        }
        dist.put(source, 0);

        for (int u : topo) {
            if (dist.get(u) != Integer.MIN_VALUE) {
                for (int[] edge : graph.getOrDefault(u, List.of())) {
                    int v = edge[0];
                    int w = edge[1];
                    dist.put(v, Math.max(dist.get(v), dist.get(u) + w));
                }
            }
        }
        return dist;
    }

}
