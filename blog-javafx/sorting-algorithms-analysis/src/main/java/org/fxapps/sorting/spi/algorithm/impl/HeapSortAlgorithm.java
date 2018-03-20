package org.fxapps.sorting.spi.algorithm.impl;

import org.fxapps.sorting.spi.algorithm.SortAlgorithm;

/**
 * 
 * An implementation of Heap Sort. Based on code from:
 * https://www.cc.gatech.edu/classes/cs3158_98_fall/heapsort.html and
 * http://www.bythemark.com/demo/Heap_BuildHeap.html
 * 
 * @author wsiqueir
 *
 */
public class HeapSortAlgorithm implements SortAlgorithm {

	public void sort(long[] data) {
		buildHeap(data);
		int n = data.length - 1;
		for (int i = 0; i < data.length; i++) {
			swap(data, 0, n);
			n--;
			heapify(data, 0, n);
		}
	}

	private static void buildHeap(long[] data) {
		for (int i = data.length / 2; i >= 0; i--) {
			heapify(data, i, data.length);
		}
	}

	private static void heapify(long[] data, int i, int n) {
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		int g = i;
		if (l < n && data[l] > data[i]) {
			g = l;
		}
		if (r < n && data[r] > data[g]) {
			g = r;
		}
		if (g != i) {
			swap(data, i, g);
			heapify(data, g, n);
		}
	}

	private static void swap(long[] data, int i, int j) {
		long temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

	public String getName() {
		return "Heap Sort";
	}

}
