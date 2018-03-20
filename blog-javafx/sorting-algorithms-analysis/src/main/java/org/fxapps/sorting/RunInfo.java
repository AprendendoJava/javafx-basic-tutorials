package org.fxapps.sorting;

import java.util.Map;

public class RunInfo {
	
	private int n;
	
	private DataInformation dataInformation;
	
	private Map<String, Long> runData;
	
	private RunInfo(int n, DataInformation dataInformation, Map<String, Long> runData) {
		super();
		this.n = n;
		this.dataInformation = dataInformation;
		this.runData = runData;
	}

	static RunInfo create(int n, DataInformation dataInfo, Map<String, Long> runData) {
		return new RunInfo(n, dataInfo, runData);
	}

	public int getN() {
		return n;
	}

	public DataInformation getDataInformation() {
		return dataInformation;
	}

	public Map<String, Long> getRunData() {
		return runData;
	}
	
	public static enum DataInformation {
		
		SORTED("Sorted data"), UNSORTED("Unsorted data"), REVERSED("Reversed data");
		
		private String info;
		
		private DataInformation(String info) {
			this.info = info;
		}
		
		@Override
		public String toString() {
			return info;
		}

	}
	
}
