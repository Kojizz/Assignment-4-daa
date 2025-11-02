package metrics;
import java.util.HashMap;
import java.util.Map;

public class Metrics {
    private long startTime;
    private long endTime;
    private final Map<String, Integer> counters = new HashMap<>();

    /** Start timing and clear previous counts. */
    public void start() {
        counters.clear();
        startTime = System.nanoTime();
    }

    /** Stop timing. */
    public void stop() {
        endTime = System.nanoTime();
    }

    /** Increment a named counter. */
    public void increment(String key) {
        counters.put(key, counters.getOrDefault(key, 0) + 1);
    }

    /** Get counter value by key. */
    public int get(String key) {
        return counters.getOrDefault(key, 0);
    }

    /** Get elapsed time in milliseconds. */
    public long elapsedMillis() {
        return (endTime - startTime) / 1_000_000;
    }

    /** Print all metrics. */
    public void report(String title) {
        System.out.println("=== " + title + " Metrics ===");
        for (var e : counters.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
        System.out.println("Elapsed time: " + elapsedMillis() + " ms");
        System.out.println("============================\n");
    }
}
