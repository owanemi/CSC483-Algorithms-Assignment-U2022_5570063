# CSC483-Algorithms-Assignment-U20225570063

**Student:** Owanemi Osaye-William  
**ID:** U2022/5570063  
**Course:** CSC 483.1 - Algorithms Analysis and Design  
**Session:** 2025/2026, First Semester

---

## Project Structure

```
src/
  main/java/com/csc483/
    assignment1/search/
      Product.java            - Product data class
      SearchEngine.java       - Sequential, binary, hybrid search + addProduct
      DataGenerator.java      - Generates and writes product datasets
      TechMartBenchmark.java  - Main benchmark program for Question 1
    assignment2/sorting/
      Sorter.java             - Interface for all sorting algorithms
      InsertionSort.java
      MergeSort.java
      QuickSort.java
      SortingDataGenerator.java - Generates test arrays of various types
      SortingBenchmark.java   - Main benchmark program for Question 2
  test/java/com/csc483/
    assignment1/
      SearchEngineTest.java   - JUnit 5 tests for Question 1
    assignment2/
      SortingAlgorithmsTest.java - JUnit 5 tests for Question 2
datasets/
  products_100k.csv           - 100,000 product records (generated on first run)
  sort_random_1000.txt        - Random integer dataset, size 1000
  sort_sorted_1000.txt        - Sorted integer dataset, size 1000
  sort_reverse_1000.txt       - Reverse-sorted integer dataset, size 1000
  sort_nearly_1000.txt        - Nearly sorted integer dataset, size 1000
  sort_dupes_1000.txt         - Many-duplicates integer dataset, size 1000
```

---

## Dependencies

- Java 11 or higher
- Maven 3.6 or higher
- JUnit 5.10 (pulled automatically by Maven)

---

## Compilation

```bash
mvn compile
```

---

## Running the Tests

```bash
mvn test
```

All tests should pass. The test suite covers correctness, edge cases (empty arrays, null inputs, single elements, duplicates), counter behaviour, and agreement between algorithms.

---

## Running the Benchmarks

**Question 1 - TechMart Search Benchmark:**

```bash
mvn exec:java -Dexec.mainClass="com.csc483.assignment1.search.TechMartBenchmark"
```

This generates `datasets/products_100k.csv` and prints a performance comparison table to stdout.

**Question 2 - Sorting Benchmark:**

```bash
mvn exec:java -Dexec.mainClass="com.csc483.assignment2.sorting.SortingBenchmark"
```

This generates dataset files under `datasets/` and prints comparison tables for all data types and input sizes.

---

## Sample Output

```
================================================================
TECHMART SEARCH PERFORMANCE ANALYSIS (n = 100,000 products)
================================================================

SEQUENTIAL SEARCH:
  Best Case  (ID at position 0)  : 0.023 ms
  Average Case (random ID)       : 45.678 ms
  Worst Case (ID not found)      : 89.345 ms

BINARY SEARCH:
  Best Case  (ID at middle)      : 0.001 ms
  Average Case (random ID)       : 0.089 ms
  Worst Case (ID not found)      : 0.092 ms

PERFORMANCE IMPROVEMENT: Binary search is ~500x faster on average

HYBRID NAME SEARCH:
  Average search time : 0.002 ms
  Average insert time : 0.134 ms
================================================================
```

---

## Known Limitations

- `addProduct` uses array shifting (O(n) per insert). For high-frequency insertions a TreeMap-backed structure would be more efficient.
- Benchmark timings depend on JVM warm-up. Results may vary between runs, especially at small input sizes.
- The products dataset uses randomly generated IDs, so duplicates are possible in the raw array before sorting.
