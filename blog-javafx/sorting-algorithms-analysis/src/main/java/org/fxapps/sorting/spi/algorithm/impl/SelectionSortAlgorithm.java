package org.fxapps.sorting.spi.algorithm.impl;

import org.fxapps.sorting.spi.algorithm.SortAlgorithm;


/**
 * 
 * An implementation of Selection Sort. Based on Wikipedia pseudo code.
 * 
 * @author wsiqueir
 *
 */
public class SelectionSortAlgorithm implements SortAlgorithm {

	public void sort(long[] data) {
		int min;
		for (int i = 0; i < data.length; i++) {
			min = i;
			for (int j = i; j < data.length; j++) {
				if (data[j] < data[min]) {
					min = j;
				}
			}
			long newMin = data[min];
			data[min] = data[i];
			data[i] = newMin;
		}
	}

	public String getName() {
		return "Selection Sort";
	}

}
