package org.fxapps.sorting.spi.algorithm.impl;

import java.util.Arrays;

import org.fxapps.sorting.spi.algorithm.SortAlgorithm;

/**
 * 
 * Uses the built in quick sort from java.util.Arrays.
 * 
 * @author wsiqueir
 *
 */
public class BuiltinQuickSortAlgorithm implements SortAlgorithm {

	public void sort(long[] data) {
		Arrays.sort(data);
	}

	public String getName() {
		return "Built-in Quick Sort";
	}

}
