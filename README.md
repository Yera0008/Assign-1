# Assignment 1 – Algorithms and Complexity

## 📌 Project Description
This project implements and analyzes several classical algorithms with runtime metrics:

- MergeSort** (with insertion cutoff)
- QuickSort** (randomized pivot, smaller-first recursion)
- Deterministic Select** (Median-of-Medians)
- Closest Pair of Points** (Divide-and-Conquer)

The project is written in **Java 17**, built with **Maven**, and tested with **JUnit 5**.
## 📂 Project Structure
Assign-1
- │── src
- │ ├── main/java/Mergesort.java
- │ │        │──/Quicksort.java
- │ │        │──/Deterministicselect.java
- │ │        │──/Closestpairofpoints.java
- │ │        │──/Metric.java
- │ │        └──/Benchmark
- │ └── test/java/Junit.java
- │── pom.xml
- │── README.md

MergeSort:
Recurrence: T(n) = 2T(n/2) + Θ(n) (divide + merge).
Solution by Master Theorem → Θ(n log n).
Depth: Θ(log n).
Cutoff to insertion sort reduces constants.

QuickSort:
Recurrence: T(n) = T(k) + T(n-k-1) + Θ(n) (random pivot).
Expected runtime: Θ(n log n). Worst case: Θ(n²).
Expected depth: Θ(log n); worst-case depth: Θ(n).
Smaller-first recursion ensures stack = O(log n).

Deterministic Select:
Recurrence: T(n) = T(n/5) + T(7n/10) + Θ(n).
Solved by Akra–Bazzi → Θ(n).
Depth: logarithmic.
Guarantees worst-case linear runtime.

Closest Pair of Points:
Recurrence: T(n) = 2T(n/2) + Θ(n) (divide + strip scan).
Master theorem → Θ(n log n).
Depth: Θ(log n).
Strip step ≤ 7 comparisons per point.

![zvbn.png](img.png)

Summary:
Measurements align with theoretical results:
MergeSort and QuickSort ~ Θ(n log n).
Select ~ Θ(n), but constants large.
Closest Pair ~ Θ(n log n).
