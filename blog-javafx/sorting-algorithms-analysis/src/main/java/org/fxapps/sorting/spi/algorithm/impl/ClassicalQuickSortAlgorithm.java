package org.fxapps.sorting.spi.algorithm.impl;

import java.util.Arrays;

import org.fxapps.sorting.Utils;
import org.fxapps.sorting.spi.algorithm.SortAlgorithm;

/**
 * 
 * Classical quicksort implementation took from:
 * http://www.algolist.net/Algorithms/Sorting/Quicksort for quicksort)
 * 
 * @author wsiqueir
 *
 */
public class ClassicalQuickSortAlgorithm implements SortAlgorithm {

	@Override
	public String getName() {
		return "Classical QuickSort";
	}

	@Override
	public void sort(long[] data) {
		quicksort(data, 0, data.length - 1);

	}

	int partition(long data[], int left, int right) {
		int i = left, j = right;
		long pivot = data[(left + right) / 2];
		while (i <= j) {
			while (data[i] < pivot)
				i++;
			while (data[j] > pivot)
				j--;
			if (i <= j) {
				swap(data, i, j);
				i++;
				j--;
			}
		}
		return i;
	}

	void quicksort(long data[], int l, int r) {
		int index = partition(data, l, r);
		if (l < index - 1)
			quicksort(data, l, index - 1);
		if (index < r)
			quicksort(data, index, r);
	}

	private static void swap(long[] data, int i, int j) {
		long temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

	public static void main(String[] args) {
		// 100, 1000, 5000, 10000, 25000, 50000, 100000
		long[] data = Utils.generateAndShuffle(25000);
		Arrays.sort(data);
		// Utils.reverseArray(data);
		new ClassicalQuickSortAlgorithm().sort(data);
		System.out.println(Arrays.toString(data));
	}

}
