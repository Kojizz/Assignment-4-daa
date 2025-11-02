package graph.topo;
import java.util.*;

public class TopoSort {
    public List<Integer> sort(Map<Integer, List<Integer>> dag) {
        Map<Integer, Integer> indegree = new HashMap<>();
        for (int u : dag.keySet()) indegree.putIfAbsent(u, 0);
        for (List<Integer> adj : dag.values()) {
            for (int v : adj)
                indegree.put(v, indegree.getOrDefault(v, 0) + 1);
        }

        Queue<Integer> q = new LinkedList<>();
        for (var e : indegree.entrySet())
            if (e.getValue() == 0) q.add(e.getKey());

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : dag.getOrDefault(u, List.of())) {
                indegree.put(v, indegree.get(v) - 1);
                if (indegree.get(v) == 0) q.add(v);
            }
        }
        return order;
    }
}
