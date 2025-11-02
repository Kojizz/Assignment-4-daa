# Assignment 4 — Smart City / Smart Campus Scheduling

## Goal

This assignment integrates three core graph algorithms to solve a practical “Smart City” scheduling scenario — where city maintenance and sensor tasks depend on one another.
It combines:

1. **Strongly Connected Components (SCC)** — to detect and group cyclic dependencies
2. **Topological Sorting (TopoSort)** — to order independent task groups
3. **Shortest and Longest Paths in a DAG (DAG-SP)** — to plan efficient and critical task sequences

---

## Project Structure

```
Assignment4/
├── data/
│   └── tasks.json
├── src/
│   ├── graph/
│   │   ├── scc/SCCFinder.java
│   │   ├── topo/TopoSort.java
│   │   └── dagsp/DAGShortestPaths.java
│   ├── utils/GraphLoader.java
│   ├── metrics/Metrics.java
│   ├── test/GraphAlgorithmsManualTest.java
│   └── Main.java
└── README.md   
```

---

## Running process

**In IntelliJ IDEA:**

1. Open the project folder
2. Right-click `Main.java` → **Run 'Main.main()'**

**From terminal:**

```bash
javac -d out $(find src -name "*.java")
java -cp out Main
```

---

## Dataset Summary

**File:** `data/tasks.json`

| Parameter    | Value                                    |
| ------------ | ---------------------------------------- |
| Directed     | true                                     |
| Nodes (n)    | 8                                        |
| Edges (m)    | 7                                        |
| Source node  | 4                                        |
| Weight model | edge weights                             |
| Graph type   | mixed (1 cyclic component, rest acyclic) |

Example content:

```json
{
  "directed": true,
  "n": 8,
  "edges": [
    {"u": 0, "v": 1, "w": 3},
    {"u": 1, "v": 2, "w": 2},
    {"u": 2, "v": 3, "w": 4},
    {"u": 3, "v": 1, "w": 1},
    {"u": 4, "v": 5, "w": 2},
    {"u": 5, "v": 6, "w": 5},
    {"u": 6, "v": 7, "w": 1}
  ]
}
```

---

## Results

### Console Output (Example Run)

```
SCCs: [[3, 2, 1], [0], [7], [6], [5], [4]]
Topological order: [0, 4, 5, 6, 7]
Shortest paths from 4:
  0 -> ∞
  1 -> ∞
  2 -> ∞
  3 -> ∞
  4 -> 0
  5 -> 2
  6 -> 7
  7 -> 8
Longest paths from 4:
  0 -> -∞
  1 -> -∞
  2 -> -∞
  3 -> -∞
  4 -> 0
  5 -> 2
  6 -> 7
  7 -> 8
```

### Per-Task Metrics (Sample)

| Task                   | Time (ms) | DFS/Edges/Relax | Notes                           |
| ---------------------- | --------: | --------------: | ------------------------------- |
| SCCFinder (Tarjan)     |         2 |           8 DFS | Found 6 SCCs (1 cyclic)         |
| TopoSort (Kahn)        |         1 |         6 edges | Valid order on condensation DAG |
| Shortest Path (DAG-SP) |         1 |   6 relaxations | Found minimal path 4→5→6→7      |
| Longest Path (DAG-SP)  |         1 |   6 relaxations | Found critical path of length 8 |

---

## Analysis

### SCC (Strongly Connected Components)

* Detected **one major cycle {1,2,3}**, representing a group of inter-dependent tasks.
* Tarjan’s algorithm handled it efficiently in O(V+E).
* **Effect of structure:** More dense cyclic graphs increase recursion depth and stack use.

### Topological Sorting

* Operates only on the **condensed DAG** (after SCC compression).
* Time is linear in nodes + edges (O(V+E)).
* **Bottleneck:** For dense DAGs, indegree updates and queue operations dominate runtime.

### DAG Shortest & Longest Paths

* **Shortest Path:** DP over topological order, single relaxation per edge.
* **Longest Path:** Similar DP using `Math.max` (critical path).
* **Effect of structure:** Sparse graphs finish faster; dense DAGs scale linearly with edge count.

### Overall Observations

* Algorithms show near-instant execution for small/medium graphs.
* Performance depends primarily on edge density, not vertex count.
* SCC compression greatly simplifies later steps by removing cycles.

---

## Conclusions

| Method                    | Best Used For                      | Advantages                                  | Notes                     |
| ------------------------- | ---------------------------------- | ------------------------------------------- | ------------------------- |
| **Tarjan SCC**            | Detecting dependency cycles        | Linear time, groups cyclic tasks            | Needed before scheduling  |
| **Kahn Topological Sort** | Ordering independent task groups   | Simple queue-based, predictable performance | Requires DAG input        |
| **DAG Shortest Path**     | Fast scheduling of dependent tasks | Linear time, exact minimal times            | Works only on DAG         |
| **DAG Longest Path**      | Finding critical (slowest) chain   | Identifies bottlenecks                      | Good for project planning |


---

**By:** *Ahmetov Rasul*
**Project:** Assignment 4 — Graph Algorithms for Smart City Scheduling

