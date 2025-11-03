import graph.scc.*;
import graph.topo.*;
import graph.dagsp.*;
import utils.GraphLoader;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String dataDir = "data";
        File folder = new File(dataDir);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null || files.length == 0) {
            System.out.println("⚠ No JSON datasets found in " + dataDir);
            return;
        }

        // loop through all .json datasets in data

        for (File file : files) {
            System.out.println("\n==============================");
            System.out.println("Processing dataset: " + file.getName());
            System.out.println("==============================");

            // load weighted graph
            var graph = GraphLoader.loadWeighted(file.getPath());

            // convert to adjacency list for SCC

            Map<Integer, List<Integer>> adj = new HashMap<>();
            for (int u : graph.keySet()) {
                List<Integer> list = new ArrayList<>();
                for (int[] e : graph.get(u)) list.add(e[0]);
                adj.put(u, list);
            }

            //SCCs
            SCCFinder scc = new SCCFinder(adj);
            var sccList = scc.findSCCs();
            System.out.println("SCCs: " + sccList);

            // Topological Sort (assuming DAG after compression)
            TopoSort topo = new TopoSort();
            var order = topo.sort(adj);
            System.out.println("Topological order: " + order);

            // shortest & Longest Paths

            DAGShortestPaths sp = new DAGShortestPaths(graph);
            int source = 0; // or choose dynamically if needed
            var shortest = sp.shortestPath(source, order);
            var longest = sp.longestPath(source, order);

            System.out.println("Shortest paths from " + source + ": " + shortest);
            System.out.println("Longest paths from " + source + ": " + longest);
        }

        System.out.println("\nAll datasets processed successfully.");
    }
}
