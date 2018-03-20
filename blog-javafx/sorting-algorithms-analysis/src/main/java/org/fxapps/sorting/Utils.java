package org.fxapps.sorting;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.beans.property.BooleanProperty;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

public class Utils {
	
	private Utils() {}
	
	public synchronized static long[] generateAndShuffle(long n) {
		long[] array = new long[(int)n];
		ThreadLocalRandom random = ThreadLocalRandom.current();
		Arrays.parallelSetAll(array, i -> i + 1);
		for (int i = 0; i < n; i++) {
			int ri = random.nextInt((int)n);
			long temp = array[i];
			array[i] = array[ri];
			array[ri] = temp;
		}
		return array;
	}
	
	public synchronized static void reverseArray(long[] array) {
		for (int i = 0; i != array.length / 2; i++) {
			int j = array.length - 1 - i;
			long temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
	}
	
	public static void showErrorDialog(String content) {
		showErrorDialog("Error...", content);
	}

	public static void showErrorDialog(String title, String content) {
		Alert dialog = new Alert(Alert.AlertType.ERROR);
		dialog.setTitle(title);
		dialog.setHeaderText(null);
		dialog.setResizable(true);
		dialog.setContentText(content);
		dialog.showAndWait();
	}
	public static <T extends Object> void doBlockingAsyncWork(Supplier<T> action, Consumer<T> success,
			BooleanProperty locker) {
		Task<T> tarefaCargaPg = new Task<T>() {
			@Override
			protected T call() throws Exception {
				locker.set(true);
				return action.get();
			}

			@Override
			protected void succeeded() {
				locker.set(false);
				success.accept(getValue());
			}

			@Override
			protected void failed() {
				locker.set(false);
				getException().printStackTrace();
			}
		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();
	}


}
