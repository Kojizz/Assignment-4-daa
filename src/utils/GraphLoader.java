package utils;

import java.nio.file.*;
import java.util.*;


public class GraphLoader {

    public static Map<Integer, List<int[]>> loadWeighted(String path) throws Exception {
        String json = Files.readString(Path.of(path))
                .replaceAll("\\s+", "");  //remove spaces/newlines

        Map<Integer, List<int[]>> graph = new HashMap<>();

        // extract edges section
        int edgesStart = json.indexOf("[", json.indexOf("\"edges\""));
        int edgesEnd = json.indexOf("]", edgesStart);
        String edgesPart = json.substring(edgesStart + 1, edgesEnd);

        // Split individual edge objects

        String[] edgeObjects = edgesPart.split("\\},\\{");
        for (String e : edgeObjects) {
            e = e.replace("{", "").replace("}", "");
            String[] parts = e.split(",");
            int u = -1, v = -1, w = 0;
            for (String p : parts) {
                if (p.startsWith("\"u\":")) u = Integer.parseInt(p.replaceAll("[^0-9]", ""));
                if (p.startsWith("\"v\":")) v = Integer.parseInt(p.replaceAll("[^0-9]", ""));
                if (p.startsWith("\"w\":")) w = Integer.parseInt(p.replaceAll("[^0-9]", ""));
            }
            if (u != -1 && v != -1)
                graph.computeIfAbsent(u, k -> new ArrayList<>()).add(new int[]{v, w});
        }
        return graph;
    }
}
