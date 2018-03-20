package org.fxapps.sorting.spi.algorithm.impl;

import java.util.Arrays;

import org.fxapps.sorting.Utils;
import org.fxapps.sorting.spi.algorithm.SortAlgorithm;

/**
 * 
 * An implementation of Shell Sort. Based on Wikipedia pseudo code.
 * 
 * @author wsiqueir
 *
 */
public class ShellSortAlgorithm implements SortAlgorithm {

	private final int[] gaps = { 701, 301, 132, 57, 23, 10, 4, 1 };

	public void sort(long[] data) {
		long temp; 
		int j;
		for (int gap : gaps) {
			for (int i = gap; i < data.length; i++) {
				temp = data[i];
				for (j = i; j >= gap && data[j - gap] > temp; j -= gap) {
					data[j] = data[j - gap];
				}
				data[j] = temp;
			}
		}
	}
	
	public static void main(String[] args) {
		long[] array = Utils.generateAndShuffle(10);
		System.out.println(Arrays.toString(array));
		new ShellSortAlgorithm().sort(array);
		System.out.println(Arrays.toString(array));
	}

	public String getName() {
		return "Shell Sort";
	}

}
