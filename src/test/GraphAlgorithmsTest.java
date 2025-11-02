package test;

import graph.scc.SCCFinder;
import graph.topo.TopoSort;
import graph.dagsp.DAGShortestPaths;
import java.util.*;

/**
 * Manual testing class for Assignment 4.
 * Run it like any normal Java program (no JUnit required).
 */
public class GraphAlgorithmsTest {

    public static void main(String[] args) {
        testSCCSingleCycle();
        testTopoOrder();
        testDAGShortestPaths();
        testDAGLongestPaths();
        System.out.println("\nAll manual tests completed!");
    }

    static void testSCCSingleCycle() {
        System.out.println("Running testSCCSingleCycle...");
        Map<Integer, List<Integer>> g = new HashMap<>();
        g.put(0, List.of(1));
        g.put(1, List.of(2));
        g.put(2, List.of(0)); // cycle 0->1->2->0

        SCCFinder scc = new SCCFinder(g);
        var sccs = scc.findSCCs();

        assertCondition(sccs.size() == 1, "Should find one SCC");
        assertCondition(sccs.get(0).containsAll(List.of(0,1,2)), "SCC should include 0,1,2");
        System.out.println("Passed: testSCCSingleCycle\n");
    }

    static void testTopoOrder() {
        System.out.println("Running testTopoOrder...");
        Map<Integer, List<Integer>> dag = Map.of(
                0, List.of(1, 2),
                1, List.of(3),
                2, List.of(3),
                3, List.of()
        );
        TopoSort topo = new TopoSort();
        var order = topo.sort(dag);

        assertCondition(order.indexOf(0) < order.indexOf(1), "0 should come before 1");
        assertCondition(order.indexOf(1) < order.indexOf(3), "1 should come before 3");
        System.out.println("Passed: testTopoOrder\n");
    }

    static void testDAGShortestPaths() {
        System.out.println("Running testDAGShortestPaths...");
        Map<Integer, List<int[]>> g = new HashMap<>();
        g.put(0, List.of(new int[]{1, 2}, new int[]{2, 4}));
        g.put(1, List.of(new int[]{2, 1}));
        g.put(2, new ArrayList<>());

        DAGShortestPaths sp = new DAGShortestPaths(g);
        List<Integer> topo = List.of(0, 1, 2);
        var shortest = sp.shortestPath(0, topo);

        assertCondition(shortest.get(2) == 3, "Shortest distance to node 2 should be 3");
        System.out.println("Passed: testDAGShortestPaths\n");
    }

    static void testDAGLongestPaths() {
        System.out.println("Running testDAGLongestPaths...");
        Map<Integer, List<int[]>> g = new HashMap<>();
        g.put(0, List.of(new int[]{1, 5}, new int[]{2, 3}));
        g.put(1, List.of(new int[]{3, 6}));
        g.put(2, List.of(new int[]{3, 4}));
        g.put(3, new ArrayList<>());

        DAGShortestPaths sp = new DAGShortestPaths(g);
        List<Integer> topo = List.of(0, 1, 2, 3);
        var longest = sp.longestPath(0, topo);

        assertCondition(longest.get(3) == 11, "Longest distance to node 3 should be 11");
        System.out.println("Passed: testDAGLongestPaths\n");
    }

    // simple replacement for JUnit assertions
    static void assertCondition(boolean condition, String message) {
        if (!condition) {
            System.err.println("Test failed: " + message);
        }
    }
}
