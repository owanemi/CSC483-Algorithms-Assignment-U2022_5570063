# CSC483-Algorithms-Assignment-U20225570063

**Student:** Owanemi Osaye-William  
**ID:** U2022/5570063  
**Course:** CSC 483.1 - Algorithms Analysis and Design  
**Session:** 2025/2026, First Semester

---

## Requirements

- Java 11 or higher (just the JDK, nothing else)

Check your Java version with:
```
java -version
javac -version
```

---

## Project Structure

```
CSC483-Algorithms-Assignment-U20225570063/
├── compile.sh              - compiles everything
├── run_tests.sh            - runs all tests
├── run_benchmarks.sh       - runs both benchmark programs
├── datasets/               - sample input files committed to the repo
│   ├── sort_random_1000.txt
│   ├── sort_sorted_1000.txt
│   ├── sort_reverse_1000.txt
│   ├── sort_nearly_1000.txt
│   └── sort_dupes_1000.txt
└── src/
    ├── main/java/com/csc483/
    │   ├── assignment1/search/
    │   │   ├── Product.java
    │   │   ├── SearchEngine.java
    │   │   ├── DataGenerator.java
    │   │   └── TechMartBenchmark.java
    │   └── assignment2/sorting/
    │       ├── Sorter.java
    │       ├── InsertionSort.java
    │       ├── MergeSort.java
    │       ├── QuickSort.java
    │       ├── SortingDataGenerator.java
    │       └── SortingBenchmark.java
    └── test/java/com/csc483/
        ├── test/
        │   ├── Assert.java       - assertion helpers (replaces JUnit assertions)
        │   ├── TestRunner.java   - runs test methods by reflection
        │   └── RunAllTests.java  - entry point that runs all suites
        ├── assignment1/
        │   └── SearchEngineTest.java
        └── assignment2/
            └── SortingAlgorithmsTest.java
```

---

## Step 1: Compile

On Linux/Mac:
```bash
chmod +x compile.sh run_tests.sh run_benchmarks.sh
./compile.sh
```

On Windows (Command Prompt — enable delayed expansion first):
```bat
mkdir out
for /r src\main\java %%f in (*.java) do javac -d out "%%f"
for /r src\test\java %%f in (*.java) do javac -cp out -d out "%%f"
```

Or manually on any OS:
```bash
find src/main/java -name "*.java" | xargs javac -d out
find src/test/java -name "*.java" | xargs javac -cp out -d out
```

---

## Step 2: Run the Tests

```bash
./run_tests.sh
```

Or manually:
```bash
java -cp out com.csc483.test.RunAllTests
```

Expected output:
```bash
nemi@OWANEMI:/mnt/c/Users/HP/Desktop/CSC483-Algorithms-Assignment-U20225570063$ ./run_tests.sh
============================================================
Running: SearchEngineTest
============================================================
  PASS  testProductConstructorStoresAllFields
  PASS  testProductRejectsBlankName
  PASS  testProductRejectsNullName
  PASS  testProductRejectsNegativePrice
  PASS  testProductRejectsNegativeStock
  PASS  testProductEqualityByIdOnly
  PASS  testProductToStringContainsId
  PASS  testSequentialSearchFindsExistingProduct
  PASS  testSequentialSearchReturnsNullWhenMissing
  PASS  testSequentialSearchFindsFirst
  PASS  testSequentialSearchFindsLast
  PASS  testSequentialSearchNullArrayReturnsNull
  PASS  testSequentialSearchEmptyArrayReturnsNull
  PASS  testBinarySearchFindsMiddleElement
  PASS  testBinarySearchFindsFirstElement
  PASS  testBinarySearchFindsLastElement
  PASS  testBinarySearchReturnsNullWhenMissing
  PASS  testBinarySearchNullArrayReturnsNull
  PASS  testBinarySearchEmptyArrayReturnsNull
  PASS  testBinaryAndSequentialAgreeOnAllIds
  PASS  testSearchByNameCaseInsensitive
  PASS  testSearchByNameReturnsNullWhenMissing
  PASS  testSearchByNameNullTargetReturnsNull
  PASS  testSearchByNameNullArrayReturnsNull
  PASS  testHybridSearchFindsProduct
  PASS  testHybridSearchCaseInsensitive
  PASS  testHybridSearchReturnsNullWhenMissing
  PASS  testHybridSearchNullReturnsNull
  PASS  testAddProductMaintainsSortedOrder
  PASS  testAddProductInsertAtBeginning
  PASS  testAddProductInsertAtEnd
  PASS  testAddProductUpdatesNameIndex
  PASS  testAddProductNullThrows
  PASS  testAddProductNullArrayTreatedAsEmpty
------------------------------------------------------------
Results: 34 passed, 0 failed
============================================================

============================================================
Running: SortingAlgorithmsTest
============================================================
  PASS  testSortRandomSmallArray
  PASS  testSortAlreadySorted
  PASS  testSortReverseSorted
  PASS  testSortAllDuplicates
  PASS  testSortSingleElement
  PASS  testSortEmptyArray
  PASS  testSortTwoElements
  PASS  testSortNegativeNumbers
  PASS  testSortLargeRandom
  PASS  testSortManyDuplicates
  PASS  testSortNearlySorted
  PASS  testMatchesJavaBuiltin
  PASS  testCountersAreZeroAfterReset
  PASS  testCountersIncrementDuringSort
  PASS  testFewerSwapsOnSortedInput
  PASS  testDataGeneratorRandomHasCorrectSize
  PASS  testDataGeneratorSortedIsAscending
  PASS  testDataGeneratorReverseSortedIsDescending
  PASS  testDataGeneratorManyDuplicatesHasFewDistinctValues
  PASS  testDataGeneratorNearlySortedHasCorrectSize
------------------------------------------------------------
Results: 20 passed, 0 failed
============================================================
```

You can also run each suite individually:
```bash
java -cp out com.csc483.assignment1.SearchEngineTest
java -cp out com.csc483.assignment2.SortingAlgorithmsTest
```

---

## Step 3: Run the Benchmarks

```bash
./run_benchmarks.sh
```

Or individually:
```bash
java -cp out com.csc483.assignment1.search.TechMartBenchmark
java -cp out com.csc483.assignment2.sorting.SortingBenchmark
```

Running `TechMartBenchmark` also generates `datasets/products_100k.csv`.
---

Expected Output:
```bash
nemi@OWANEMI:/mnt/c/Users/HP/Desktop/CSC483-Algorithms-Assignment-U20225570063$ ./run_benchmarks.sh 
Running Question 1 - TechMart Search Benchmark...
================================================================
TECHMART SEARCH PERFORMANCE ANALYSIS (n = 100,000 products)
================================================================

SEQUENTIAL SEARCH:
  Best Case  (ID at position 0)  : 0.006 ms
  Average Case (random ID)       : 3.267 ms
  Worst Case (ID not found)      : 3.948 ms

BINARY SEARCH:
  Best Case  (ID at middle)      : 0.007 ms
  Average Case (random ID)       : 0.006 ms
  Worst Case (ID not found)      : 0.005 ms

PERFORMANCE IMPROVEMENT: Binary search is ~7x faster on average

HYBRID NAME SEARCH:
  Average search time : 0.007 ms
  Average insert time : 16.393 ms
================================================================

Running Question 2 - Sorting Algorithms Benchmark...

================================================================
SORTING ALGORITHMS COMPARISON - RANDOM DATA
================================================================
Size       Algorithm         Time (ms)      Comparisons        Swaps
100        InsertionSort         0.132             2350         2251
100        MergeSort             0.238              538          250
100        QuickSort             0.281              667          327
1000       InsertionSort         4.972           243329       242330
1000       MergeSort             0.489             8742         4333
1000       QuickSort             0.517            11434         5344
10000      InsertionSort        61.145         24941883     24931884
10000      MergeSort            12.354           120457        59180
10000      QuickSort             1.843           164195        77347
100000     InsertionSort      6052.713       2481498356   2481398357
100000     MergeSort            27.822          1536312       760062
100000     QuickSort            22.114          2025755      1013207

================================================================
SORTING ALGORITHMS COMPARISON - SORTED DATA
================================================================
Size       Algorithm         Time (ms)      Comparisons        Swaps
100        InsertionSort         0.002               99            0
100        MergeSort             0.011              356            0
100        QuickSort             0.012              679          100
1000       InsertionSort         0.004              999            0
1000       MergeSort             0.111             5044            0
1000       QuickSort             0.106            11068         1009
10000      InsertionSort         0.033             9999            0
10000      MergeSort             1.502            69008            0
10000      QuickSort             0.881           156677        10036
100000     InsertionSort         0.534            99999            0
100000     MergeSort            12.728           853904            0
100000     QuickSort            10.214          2015066       100050

================================================================
SORTING ALGORITHMS COMPARISON - REVERSE SORTED DATA
================================================================
Size       Algorithm         Time (ms)      Comparisons        Swaps
100        InsertionSort         0.016             5049         4950
100        MergeSort             0.017              316          316
100        QuickSort             0.013              630          328
1000       InsertionSort         1.142           500499       499500
1000       MergeSort             0.116             4932         4932
1000       QuickSort             0.135            10793         4798
10000      InsertionSort       146.014         50004999     49995000
10000      MergeSort             1.957            64608        64608
10000      QuickSort             2.821           159455        62802
100000     InsertionSort     12802.779       5000049999   4999950000
100000     MergeSort            13.450           815024       815024
100000     QuickSort            15.144          1966007       754330

================================================================
SORTING ALGORITHMS COMPARISON - NEARLY SORTED DATA
================================================================
Size       Algorithm         Time (ms)      Comparisons        Swaps
100        InsertionSort         0.004              649          550
100        MergeSort             0.014              496          170
100        QuickSort             0.017              640          192
1000       InsertionSort         0.228            61673        60674
1000       MergeSort             0.179             7918         3132
1000       QuickSort             0.200            11339         4366
10000      InsertionSort        17.417          5838229      5828230
10000      MergeSort             1.493           115548        49427
10000      QuickSort             2.246           156182        69030
100000     InsertionSort      1390.144        584441067    584341068
100000     MergeSort            14.888          1486569       662039
100000     QuickSort            17.719          2011713       922519

================================================================
SORTING ALGORITHMS COMPARISON - MANY DUPLICATES DATA
================================================================
Size       Algorithm         Time (ms)      Comparisons        Swaps
100        InsertionSort         0.009             2481         2382
100        MergeSort             0.038              533          245
100        QuickSort             0.014              855          255
1000       InsertionSort         0.802           234716       233717
1000       MergeSort             0.253             8481         3927
1000       QuickSort             0.436            53399         3092
10000      InsertionSort        43.290         22623213     22613214
10000      MergeSort             1.135           116829        54054
10000      QuickSort            20.673          5040309        27131
100000     InsertionSort      6838.005       2248996639   2248896640
100000     MergeSort            20.905          1483727       691610
100000     QuickSort          2429.743        500358609       282004

================================================================
CONCLUSIONS
================================================================
- QuickSort is fastest on average for random data
- InsertionSort is competitive only for n < 1,000
- MergeSort provides consistent performance across all data types
- QuickSort degrades on reverse-sorted data with naive pivot
  (randomised pivot used here to avoid O(n^2) worst case)
```

## How the Test Framework Works

There is no JUnit or any third-party library. The test framework is three small files inside `src/test/java/com/csc483/test/`:

- `Assert.java` provides `assertEquals`, `assertNotNull`, `assertNull`, `assertTrue`, `assertThrows`, and `assertArrayEquals`. Each throws a standard Java `AssertionError` on failure, the same way JUnit does internally.
- `TestRunner.java` uses Java reflection to find every method whose name starts with `test`, calls `setUp()` before each one, runs it, and prints PASS or FAIL with the failure message.
- `RunAllTests.java` is the entry point that creates one instance of each test class and calls `run()`.

No annotations, no external jars, nothing to install.

---

## Known Limitations

- `addProduct` uses array shifting (O(n) per insert). For write-heavy workloads a `TreeMap` would be more efficient.
- Benchmark timings vary between runs due to JVM warm-up. The numbers are most reliable at large input sizes (n = 100,000).
- The products dataset uses randomly generated IDs so duplicates are possible before the array is sorted.
