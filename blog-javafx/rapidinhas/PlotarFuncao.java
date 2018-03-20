
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleFunction;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PlotarFuncao extends Application {

	static List<NamedFunction> functions = Arrays.asList(
			NamedFunction.of("1", n -> 1d),
			NamedFunction.of("log n", n -> log2(n)), 
			NamedFunction.of("n", n -> n),
			NamedFunction.of("n log n", n -> n * log2(n)), 
			NamedFunction.of("n^2", n -> Math.pow(n, 2)),
			NamedFunction.of("n^3", n -> Math.pow(n, 3)),
			NamedFunction.of("2^n", n -> Math.pow(2, n))
		);

	LineChart<String, Number> chart;
	List<ToggleButton> cbSeries;

	SimpleIntegerProperty si = new SimpleIntegerProperty(10);
	private CheckBox cbBase2;

	private Slider sldCut;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		buildChart();
		HBox hbSeries = buildConfigBar();
		VBox rootPane = new VBox(chart, hbSeries);
		stage.setScene(new Scene(rootPane, 1200, 800));
		stage.show();
		runTests();
	}

	private HBox buildConfigBar() {
		HBox hbSeries = new HBox(5);
		cbSeries = new ArrayList<>();
		functions.stream().forEach(f -> {
			ToggleButton cb = new ToggleButton(f.getName());
			cb.setUserData(f);
			cb.setSelected(true);
			cb.selectedProperty().addListener(s -> runTests());
			cbSeries.add(cb);
		});
		sldCut = new Slider(1, si.get(), 1);
		sldCut.valueProperty().addListener(c -> runTests());
		si.addListener((obs, o, n) -> {
			sldCut.setMax(n.doubleValue());
			sldCut.setValue(1d);
			runTests();
		});
		TextField txtN = new TextField("10");
		txtN.textProperty().addListener((obs, o, n) -> {
			n = n.trim();
			if (!n.matches("\\d*"))
				txtN.setText(n.replaceAll("[^\\d]", ""));
			if (n.equals("") || Integer.parseInt(n) < 1)
				txtN.setText("1");
		});
		txtN.setOnAction(e -> si.set(Integer.parseInt(txtN.getText())));
		cbBase2 = new CheckBox("Use 2n");
		cbBase2.selectedProperty().addListener(c -> runTests());
		hbSeries.setAlignment(Pos.CENTER);
		hbSeries.getChildren().addAll(new Label("N:"), txtN, new Label("Select starting value"), sldCut, cbBase2,
				new Label("Axis: "));
		hbSeries.getChildren().addAll(cbSeries);
		return hbSeries;
	}

	private void buildChart() {
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("n");
		yAxis.setLabel("f(n)");
		yAxis.setTickLabelFormatter(new StringConverter<Number>() {

			private DecimalFormat decimalFormat = new DecimalFormat("#E0");

			@Override
			public String toString(Number n) {
				if(n.intValue() < 10000) {
					return String.valueOf(n.intValue());
				} else {
					return decimalFormat.format(n);
				}
			}

			@Override
			public Number fromString(String s) {
				return 0;
			}
		});
		chart = new LineChart<>(xAxis, yAxis);
		chart.setPrefSize(800, 600);
		chart.setCreateSymbols(false);
		chart.setAnimated(false);
		chart.setVerticalGridLinesVisible(false);
	}

	private void runTests() {
		System.out.println("Running tests...");
		chart.getData().clear();
		cbSeries.stream().filter(ToggleButton::isSelected).forEach(tb -> {
			NamedFunction f = (NamedFunction) tb.getUserData();
			Series<String, Number> s = new Series<>();
			s.setName(f.getName());
			int min = (int) sldCut.getValue();
			for (int i = min; i <= si.get(); i++) {
				int v = (int) (cbBase2.isSelected() ? i + i : i);
				String vStr = String.valueOf(v);
				Double fv = f.getFunction().apply(Double.parseDouble(vStr));
				if (!Double.isFinite(fv)) {
					showAlertMessage(f, v);
					return;
				}
				Data<String, Number> data = new Data<>(vStr, fv);
				s.getData().add(data);
			}
			chart.getData().add(s);
		});
	}

	private void showAlertMessage(NamedFunction f, int v) {
		Alert a = new Alert(AlertType.ERROR);
		a.setTitle("Value exploded! Stopping the chart");
		a.setContentText("Value exploded for n=" + v + " and f(v)=" + f.getName());
		a.showAndWait();
	}

	public static class NamedFunction {
		private String name;
		private DoubleFunction<Double> function;

		public NamedFunction(String name, DoubleFunction<Double> function) {
			super();
			this.name = name;
			this.function = function;
		}
		
		public static NamedFunction of(String name, DoubleFunction<Double> function) {
			return new NamedFunction(name, function);
		}
		
		public String getName() {
			return name;
		}

		public DoubleFunction<Double> getFunction() {
			return function;
		}

		@Override
		public String toString() {
			return getName();
		}
	}
	
	private static Double log2(Double x) {
		return Math.log(x) / Math.log(2d);
	}

}
