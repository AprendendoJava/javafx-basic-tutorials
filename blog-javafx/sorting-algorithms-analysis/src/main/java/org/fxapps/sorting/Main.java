package org.fxapps.sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.fxapps.sorting.RunInfo.DataInformation;
import org.fxapps.sorting.spi.algorithm.SortAlgorithm;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

	private SortingAlgorithmsRunner runner;
	private TreeView<String> treeView;
	private Map<DataInformation, LineChart<String, Number>> charts = new HashMap<>();
	private ProgressIndicator progressIndicator;

	public static void main(String[] args) throws InterruptedException {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		TabPane tabs = new TabPane();
		runner = new SortingAlgorithmsRunner();
		treeView = new TreeView<>(new TreeItem<>("All tests"));
		tabs.setSide(Side.LEFT);
		tabs.getTabs().add(new Tab("Control Pane", buildControlPane()));
		tabs.getTabs().add(new Tab("Data", treeView));
		progressIndicator = new ProgressIndicator();
		progressIndicator.setMaxSize(300, 300);
		progressIndicator.setVisible(false);
		Arrays.stream(DataInformation.values()).forEach(e -> tabs.getTabs().add(new Tab(e + " chart", buildChart(e))));
		tabs.getTabs().forEach(t -> t.setClosable(false));
		tabs.disableProperty().bind(progressIndicator.visibleProperty());
		stage.setScene(new Scene(new StackPane(progressIndicator, tabs), 1200, 600));
		stage.show();
		stage.setTitle("Testing sorting algorithms");
	}

	private Node buildControlPane() {
		FlowPane fpAlgorithms = new FlowPane(20, 20);
		List<SortAlgorithm> algos = SortingAlgorithmsService.get().services();
		algos.stream().map(a -> {
			ToggleButton tbAlgo = new ToggleButton(a.getName());
			tbAlgo.setUserData(a);
			tbAlgo.setFont(Font.font(25));
			tbAlgo.setSelected(true);
			return tbAlgo;
		}).forEach(fpAlgorithms.getChildren()::add);
		TextField txtN = new TextField("100, 1000, 5000, 10000, 25000, 50000, 100000");
		txtN.setPrefSize(500, 30);
		Button btnRun = new Button("RUN!");
		btnRun.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.EXTRA_BOLD, 40));
		Label lblTitle = new Label("Sorting Algorithms");
		lblTitle.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.EXTRA_BOLD, 50));
		Label lblN = new Label("List of N (comma separated");
		lblN.setFont(Font.font(20));
		VBox vbControlPane = new VBox(20, lblTitle, fpAlgorithms, new Separator(Orientation.HORIZONTAL),
				new HBox(20, lblN, txtN), new Separator(Orientation.HORIZONTAL), btnRun);
		vbControlPane.setLayoutX(10);
		vbControlPane.setLayoutY(5);
		vbControlPane.setAlignment(Pos.CENTER);
		btnRun.setOnAction(e -> {
			int ns[] = getNs(txtN);
			List<SortAlgorithm> selectedAlgos = fpAlgorithms.getChildren().stream().map(n -> (ToggleButton) n)
					.filter(b -> b.isSelected()).map(b -> (SortAlgorithm) b.getUserData()).collect(Collectors.toList());
			Utils.doBlockingAsyncWork(() -> runner.run(ns, selectedAlgos), this::updateDataView,
					progressIndicator.visibleProperty());
		});
		return vbControlPane;
	}

	private int[] getNs(TextField txtN) {
		try {
			return Arrays.stream(txtN.getText().replaceAll(" ", "").split("\\,")).mapToInt(Integer::parseInt).toArray();
		} catch (Exception e) {
			e.printStackTrace();
			Utils.showErrorDialog("Error in the input N");
			return new int[0];
		}
	}

	private void updateDataView(List<RunInfo> runInfos) {
		runInfos.sort(Comparator.comparingInt(RunInfo::getN));
		updateTreeView(runInfos);
		updateCharts(runInfos);
	}

	private void updateCharts(List<RunInfo> runInfos) {
		Map<DataInformation, List<RunInfo>> groupedRunInfo = runInfos.stream()
				.collect(Collectors.groupingBy(RunInfo::getDataInformation));
		charts.values().forEach(c ->  c.getData().clear());
		groupedRunInfo.forEach((i, infos) -> {
			Map<String, Series<String, Number>> seriesByAlgorithm = new HashMap<>();
			for (RunInfo info : infos) {
				info.getRunData().forEach((a, t) -> {
					seriesByAlgorithm.computeIfAbsent(a, str -> {
						Series<String, Number> series = new Series<>();
						series.setName(a);
						return series;
					});
					String nstr = String.valueOf(info.getN());
					seriesByAlgorithm.get(a).getData().add(new Data<>(nstr, t));
				});
			}
			seriesByAlgorithm.values().forEach(charts.get(i).getData()::add);
		});
	}

	private void updateTreeView(List<RunInfo> runInfos) {
		treeView.getRoot().getChildren().clear();
		Map<Integer, List<RunInfo>> groupedRunInfo = runInfos.stream().collect(Collectors.groupingBy(RunInfo::getN));
		groupedRunInfo.forEach((n, i) -> {
			TreeItem<String> itemN = new TreeItem<>("n = " + n);
			for (RunInfo runInfo : i) {
				TreeItem<String> dataOrder = new TreeItem<>(runInfo.getDataInformation().toString());
				long worstValue = -1, bestValue = -1;
				TreeItem<String> worstItem = new TreeItem<>(), bestItem = new TreeItem<>();
				for (Entry<String, Long> e : runInfo.getRunData().entrySet()) {
					long t = e.getValue();
					String a = e.getKey();
					TreeItem<String> cell = new TreeItem<>(a + ": " + t + "ms");
					if (worstValue == -1 && bestValue == -1) {
						worstValue = bestValue = t;
						worstItem = bestItem = cell;
					}
					if (t >= worstValue) {
						worstItem.setGraphic(null);
						cell.setGraphic(worstIndicator());
						worstValue = t;
						if(bestItem == worstItem) {
							bestItem.setGraphic(bestIndicator());
						}
						worstItem = cell;
					} 
					if (t <= bestValue) {
						bestItem.setGraphic(null);
						cell.setGraphic(bestIndicator());
						bestValue = t;
						if(bestItem == worstItem) {
							bestItem.setGraphic(worstIndicator());
						}
						bestItem = cell;
					}
					dataOrder.getChildren().add(cell);
				}
				itemN.getChildren().add(dataOrder);
			}
			treeView.getRoot().getChildren().add(itemN);
		});
		treeView.getRoot().setExpanded(true);
	}

	private Node worstIndicator() {
		return new Circle(4, Color.RED);
	}

	private Node bestIndicator() {
		return new Circle(4, Color.BLUE);
	}

	private Chart buildChart(DataInformation dataInformation) {
		LineChart<String, Number> chart;
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("n");
		yAxis.setLabel("t");
		chart = new LineChart<>(xAxis, yAxis);
		chart.setPrefSize(800, 600);
		chart.setVerticalGridLinesVisible(false);
		chart.setTitle("Tests with " + dataInformation.toString());
		charts.put(dataInformation, chart);
		return chart;
	}

}