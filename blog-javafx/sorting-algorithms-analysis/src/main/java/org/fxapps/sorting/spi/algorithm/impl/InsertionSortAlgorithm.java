package org.fxapps.sorting.spi.algorithm.impl;

import java.util.Arrays;

import org.fxapps.sorting.Utils;
import org.fxapps.sorting.spi.algorithm.SortAlgorithm;

/**
 * 
 * An implementation of Insertion Sort. Based on Wikipedia pseudo code.
 * 
 * @author wsiqueir
 *
 */
public class InsertionSortAlgorithm implements SortAlgorithm {

	public void sort(long[] data) {
		for (int i = 1; i < data.length; i++) {
			for (int j = i; j > 0 && data[j] < data[j - 1]; j--) {
				long temp = data[j - 1];
				data[j - 1] = data[j];
				data[j] = temp;
			}
		}
	}
	
	public static void main(String[] args) {
		long[] data = Utils.generateAndShuffle(100);
		new InsertionSortAlgorithm().sort(data);
		System.out.println(Arrays.toString(data));
	}

	public String getName() {
		return "Insertion Sort";
	}

}
