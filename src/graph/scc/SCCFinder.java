package graph.scc;
import java.util.*;

public class SCCFinder {
    private int time = 0;
    private final Map<Integer, List<Integer>> graph;
    private final Map<Integer, Integer> disc = new HashMap<>();
    private final Map<Integer, Integer> low = new HashMap<>();
    private final Stack<Integer> stack = new Stack<>();
    private final Set<Integer> inStack = new HashSet<>();
    private final List<List<Integer>> sccList = new ArrayList<>();

    public SCCFinder(Map<Integer, List<Integer>> graph) {
        this.graph = graph;
    }

    public List<List<Integer>> findSCCs() {
        for (int v : graph.keySet()) {
            if (!disc.containsKey(v)) dfs(v);
        }
        return sccList;
    }

    private void dfs(int u) {
        disc.put(u, time);
        low.put(u, time);
        time++;
        stack.push(u);
        inStack.add(u);

        for (int v : graph.getOrDefault(u, List.of())) {
            if (!disc.containsKey(v)) {
                dfs(v);
                low.put(u, Math.min(low.get(u), low.get(v)));
            } else if (inStack.contains(v)) {
                low.put(u, Math.min(low.get(u), disc.get(v)));
            }
        }

        if (low.get(u).equals(disc.get(u))) {
            List<Integer> component = new ArrayList<>();
            int w;
            do {
                w = stack.pop();
                inStack.remove(w);
                component.add(w);
            } while (w != u);
            sccList.add(component);
        }
    }
}