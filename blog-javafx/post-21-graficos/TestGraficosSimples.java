import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class TestGraficosSimples extends Application {

	private static final int GRAFICO_ALTURA = 300;
	private static final int GRAFICO_LARGURA = 300;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage s) throws Exception {
		s.setScene(new Scene(new FlowPane(criarGraficoPizza(),
				criarGraficoLinha())));
		s.setTitle("Gráficos básicos do JavaFX");
		s.setWidth(800);
		s.setHeight(600);
		s.show();

	}

	private Node criarGraficoLinha() {
		LineChart<Number, Number> graficoLinha = new LineChart<>(new NumberAxis(), new NumberAxis());
		graficoLinha.setPrefSize(GRAFICO_LARGURA, GRAFICO_ALTURA);
		return graficoLinha;
	}

	private Node criarGraficoPizza() {
		PieChart graficoPizza = new PieChart();
		graficoPizza.getData().addAll(new PieChart.Data("Trimestre 1", 11),
				new PieChart.Data("Trimestre 2", 1),
				new PieChart.Data("Trimestre 3", 34),
				new PieChart.Data("Trimestre 5", 12));
		graficoPizza.setTitle("Lucros por Trimestre");
		graficoPizza.setPrefSize(GRAFICO_LARGURA, GRAFICO_ALTURA);
		return graficoPizza;
	}

}
