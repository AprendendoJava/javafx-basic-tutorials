package org.fxapps.sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.fxapps.sorting.spi.algorithm.SortAlgorithm;

public class SortingAlgorithmsService {

	private static ServiceLoader<SortAlgorithm> loader;
	private static SortingAlgorithmsService service;
	private ArrayList<SortAlgorithm> algorithms;
	
	private SortingAlgorithmsService() {
		loader = ServiceLoader.load(SortAlgorithm.class);
	}

	
	public List<SortAlgorithm> services() {
		if(algorithms == null) {
			algorithms = new ArrayList<>();
			loader.iterator().forEachRemaining(algorithms::add);
		}
		return algorithms;
	}

	public static synchronized SortingAlgorithmsService get() {
		if (service == null) {
			service = new SortingAlgorithmsService();
		}
		return service;
	}

}
