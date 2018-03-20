package org.fxapps.sorting.spi.algorithm.impl;

import java.util.Arrays;

import org.fxapps.sorting.spi.algorithm.SortAlgorithm;

/**
 * 
 * Uses the built in parallel merge sort from java.util.Arrays.
 * 
 * @author wsiqueir
 *
 */
public class BuiltinParallelMergeSortAlgorithm implements SortAlgorithm {

	public void sort(long[] data) {
		Arrays.parallelSort(data);
	}

	public String getName() {
		return "Built-in Parallel Merge Sort";
	}

}
