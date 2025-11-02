import graph.scc.*;
import graph.topo.*;
import graph.dagsp.*;
import utils.GraphLoader;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        var graph = GraphLoader.loadWeighted("data/tasks.json");

        // Convert to adjacency list for SCC

        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int u : graph.keySet()) {
            List<Integer> list = new ArrayList<>();
            for (int[] e : graph.get(u)) list.add(e[0]);
            adj.put(u, list);
        }

        //find SCCs
        SCCFinder scc = new SCCFinder(adj);
        var sccList = scc.findSCCs();
        System.out.println("SCCs: " + sccList);

        // simple topo on original graph (assume DAG after compression)
        TopoSort topo = new TopoSort();
        var order = topo.sort(adj);
        System.out.println("Topological order: " + order);

        // Shortest & Longest paths

        DAGShortestPaths sp = new DAGShortestPaths(graph);
        int source = 4;
        var shortest = sp.shortestPath(source, order);
        var longest = sp.longestPath(source, order);

        System.out.println("Shortest paths from " + source + ": " + shortest);
        System.out.println("Longest paths from " + source + ": " + longest);
    }
}
