package com.csc483.assignment2;

import com.csc483.assignment2.sorting.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SortingAlgorithmsTest {

    static Stream<Sorter> sorters() {
        return Stream.of(new InsertionSort(), new MergeSort(), new QuickSort());
    }

    // Utility: checks that an array is sorted in non-descending order
    static void assertSorted(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            assertTrue(a[i] <= a[i + 1],
                    "Not sorted at index " + i + ": " + a[i] + " > " + a[i + 1]);
        }
    }

    // ------------------------------------------------------------------ Correctness
    @ParameterizedTest
    @MethodSource("sorters")
    void sort_randomArray(Sorter sorter) {
        int[] data = {5, 3, 8, 1, 9, 2, 7, 4, 6, 0};
        sorter.sort(data);
        assertSorted(data);
    }

    @ParameterizedTest
    @MethodSource("sorters")
    void sort_alreadySorted(Sorter sorter) {
        int[] data = {1, 2, 3, 4, 5};
        sorter.sort(data);
        assertSorted(data);
    }

    @ParameterizedTest
    @MethodSource("sorters")
    void sort_reverseSorted(Sorter sorter) {
        int[] data = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        sorter.sort(data);
        assertSorted(data);
    }

    @ParameterizedTest
    @MethodSource("sorters")
    void sort_allDuplicates(Sorter sorter) {
        int[] data = {5, 5, 5, 5, 5};
        sorter.sort(data);
        assertSorted(data);
    }

    @ParameterizedTest
    @MethodSource("sorters")
    void sort_singleElement(Sorter sorter) {
        int[] data = {42};
        sorter.sort(data);
        assertSorted(data);
    }

    @ParameterizedTest
    @MethodSource("sorters")
    void sort_emptyArray(Sorter sorter) {
        int[] data = {};
        sorter.sort(data);
        assertSorted(data); // no assertion triggered, just must not throw
    }

    @ParameterizedTest
    @MethodSource("sorters")
    void sort_twoElements(Sorter sorter) {
        int[] data = {9, 1};
        sorter.sort(data);
        assertSorted(data);
    }

    @ParameterizedTest
    @MethodSource("sorters")
    void sort_negativeNumbers(Sorter sorter) {
        int[] data = {-3, -1, -7, 0, 2, -5};
        sorter.sort(data);
        assertSorted(data);
    }

    @ParameterizedTest
    @MethodSource("sorters")
    void sort_largeRandom(Sorter sorter) {
        int[] data = SortingDataGenerator.random(10_000);
        sorter.sort(data);
        assertSorted(data);
    }

    @ParameterizedTest
    @MethodSource("sorters")
    void sort_manyDuplicates(Sorter sorter) {
        int[] data = SortingDataGenerator.manyDuplicates(5_000);
        sorter.sort(data);
        assertSorted(data);
    }

    // Result must match Arrays.sort as the reference
    @ParameterizedTest
    @MethodSource("sorters")
    void sort_matchesJavaBuiltin(Sorter sorter) {
        int[] original = SortingDataGenerator.random(500);
        int[] expected = Arrays.copyOf(original, original.length);
        int[] actual   = Arrays.copyOf(original, original.length);

        Arrays.sort(expected);
        sorter.sort(actual);

        assertArrayEquals(expected, actual);
    }

    // ------------------------------------------------------------------ Counter behaviour
    @ParameterizedTest
    @MethodSource("sorters")
    void counters_areZeroAfterReset(Sorter sorter) {
        int[] data = {3, 1, 2};
        sorter.sort(data);
        sorter.reset();
        assertEquals(0, sorter.comparisons());
        assertEquals(0, sorter.swaps());
    }

    @ParameterizedTest
    @MethodSource("sorters")
    void counters_incrementDuringSort(Sorter sorter) {
        int[] data = {5, 3, 8, 1, 9};
        sorter.reset();
        sorter.sort(data);
        assertTrue(sorter.comparisons() > 0,
                sorter.name() + " should record at least one comparison");
    }

    @ParameterizedTest
    @MethodSource("sorters")
    void counters_fewerSwapsOnSortedInput(Sorter sorter) {
        int[] random = SortingDataGenerator.random(1_000);
        int[] sorted = SortingDataGenerator.sorted(1_000);

        sorter.reset();
        sorter.sort(Arrays.copyOf(random, random.length));
        long swapsRandom = sorter.swaps();

        sorter.reset();
        sorter.sort(Arrays.copyOf(sorted, sorted.length));
        long swapsSorted = sorter.swaps();

        assertTrue(swapsSorted <= swapsRandom,
                sorter.name() + ": sorted input should not require more swaps than random");
    }

    // ------------------------------------------------------------------ Data generator
    @Test
    void dataGenerator_randomHasCorrectSize() {
        assertEquals(500, SortingDataGenerator.random(500).length);
    }

    @Test
    void dataGenerator_sortedIsAscending() {
        int[] data = SortingDataGenerator.sorted(100);
        assertSorted(data);
    }

    @Test
    void dataGenerator_reverseSortedIsDescending() {
        int[] data = SortingDataGenerator.reverseSorted(100);
        for (int i = 0; i < data.length - 1; i++) {
            assertTrue(data[i] >= data[i + 1]);
        }
    }

    @Test
    void dataGenerator_manyDuplicatesHasAtMost10DistinctValues() {
        int[] data = SortingDataGenerator.manyDuplicates(1_000);
        long distinct = Arrays.stream(data).distinct().count();
        assertTrue(distinct <= 10);
    }
}
