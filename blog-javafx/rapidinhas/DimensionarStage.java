import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DimensionarStage extends Application {

	private static final double MAX_ALTURA = 500;
	private static final double MAX_LARGURA = 600;
	private static final double MIN_LARGURA = 350;
	private static final double MIN_ALTURA = 70;
	private static final double PASSO = 10;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {

		Button btnAumentar = new Button("Aumentar");
		Button btnDim = new Button("Diminuir");
		Button btnMax = new Button("Maximizar");
		Button btnFechar = new Button("Fechar");

		HBox hbBotoes = new HBox(btnAumentar, btnDim, btnMax, btnFechar);
		hbBotoes.setSpacing(20);

		// permite trabalhar com os tamanhos do Stage
		btnAumentar.setOnAction(e -> {
			double w = stage.getWidth();
			double h = stage.getHeight();
			if (w < MAX_LARGURA) {
				stage.setWidth(w + PASSO);
			}
			if (h < MAX_ALTURA) {
				stage.setHeight(h + PASSO);
			}
		});
		btnDim.setOnAction(e -> {
			double w = stage.getWidth();
			double h = stage.getHeight();
			if (w > MIN_LARGURA) {
				stage.setWidth(w - PASSO);
			}
			if (h > MIN_ALTURA) {
				stage.setHeight(h - PASSO);
			}
		});
		btnMax.setOnAction(e -> stage.setMaximized(true));
		btnFechar.setOnAction(e -> stage.close());
		// Desabilita se o Stage estiver maximizado
		btnMax.disableProperty().bind(stage.maximizedProperty());
		stage.setTitle("Dimensionando o Stage");
		stage.setScene(new Scene(hbBotoes));
		stage.show();
	}

}
