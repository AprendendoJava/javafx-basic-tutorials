package org.fxapps.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.fxapps.sorting.RunInfo.DataInformation;
import org.fxapps.sorting.spi.algorithm.SortAlgorithm;

public class SortingAlgorithmsRunner {

	private List<SortAlgorithm> services;

	public List<RunInfo> run(int[] ns, List<SortAlgorithm> services) {
		List<Callable<RunInfo>> tasks = new ArrayList<>();
		ExecutorService executor;
		this.services = services;  
		executor = Executors.newFixedThreadPool(3 * ns.length);
		for (int n : ns) {
			long[] array = Utils.generateAndShuffle(n);
			tasks.add(() -> {
				long[] cp = Arrays.copyOf(array, array.length);
				return RunInfo.create(n, DataInformation.UNSORTED, runSortAlgorithms(cp, "Unsorted" + n));
			});
			Arrays.parallelSort(array);
			tasks.add(() -> {
				long[] cp = Arrays.copyOf(array, array.length);
				return RunInfo.create(n, DataInformation.SORTED, runSortAlgorithms(cp, "Sorted" + n));
			});
			Utils.reverseArray(array);
			tasks.add(() -> {
				long[] cp = Arrays.copyOf(array, array.length);
				return RunInfo.create(n, DataInformation.REVERSED, runSortAlgorithms(cp, "Reversed" + n));
			});
		}
		System.out.println("\n**** RESULTS ****\n");
		List<RunInfo> results;
		try {
			results = executor.invokeAll(tasks).stream().map(t -> {
				try {
					return t.get();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}).peek(info -> {
				System.out.printf("* Tests using a %s with %d elements\n", info.getDataInformation(), info.getN());
				System.out.println("--");
				info.getRunData().forEach((k2, e2) -> {
					System.out.println(k2 + "\t\t" + e2 + "ms");
				});
				System.out.println();
			}).collect(Collectors.toList());
		} catch (InterruptedException e) {
			e.printStackTrace();
			results = Collections.emptyList();
		}finally {
			executor.shutdown();
		}
		return results;
	}

	private Map<String, Long> runSortAlgorithms(long[] d, String runName) {
		Map<String, Long> sortAlgorithmsStatistics = new HashMap<>();
		// parallel is having a very weird behavior here
		services.stream().forEach(s -> {
			long currentTime, resultTime;
			long[] copy = Arrays.copyOf(d, d.length);
			currentTime = System.currentTimeMillis();
			s.sort(copy);
			resultTime = System.currentTimeMillis() - currentTime;
			sortAlgorithmsStatistics.put(s.getName(), resultTime);
			System.out.println(runName + ": " + s.getName() + " took " + resultTime + "ms");
		});
		return sortAlgorithmsStatistics;
	}
}
