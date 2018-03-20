package org.fxapps.sorting.spi.algorithm.impl;

import org.fxapps.sorting.spi.algorithm.SortAlgorithm;

/**
 * 
 * An implementation of BubbleSort. Based on Wikipedia pseudo code.
 * 
 * @author wsiqueir
 *
 */
public class BubbleSortAlgorithm implements SortAlgorithm {

	public void sort(long[] data) {
		boolean noSwap;
		for (int i = 0; i < data.length; i++) {
			noSwap = true;
			for (int j = data.length - 1; j > i; j--) {
				if (data[j - 1] > data[j]) {
					long temp = data[j - 1];
					data[j - 1] = data[j];
					data[j] = temp;
					noSwap = false;
				}
			}
			if (noSwap) {
				break;
			}
		}
	}

	public String getName() {
		return "Bubble Sort";
	}

}
