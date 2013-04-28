package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage palco) throws Exception {
		StackPane raiz = new StackPane();

		TabPane painelLayouts = new TabPane();

		Tab tabGridPane = new Tab("Grid Pane");
		GridPane gridPane = new GridPane();
		gridPane.add(new Label("0 X 0"), 0, 0);
		gridPane.add(new Label("0 X 1"), 0, 1);
		gridPane.add(new Label("1 X 0"), 1, 0);
		gridPane.add(new Label("1 X 1"), 1, 1);
		gridPane.setVgap(20);
		gridPane.setHgap(20);
		gridPane.setTranslateX(120);
		gridPane.setTranslateY(30);
		tabGridPane.setContent(gridPane);

		Tab tabBorderPane = new Tab("Border Pane");
		BorderPane borderPane = new BorderPane();
		Label lblTop, lblEsquerda, lblBaixo, lblDireita, lblCentro;

		borderPane.setTop(lblTop = new Label("Topo"));
		borderPane.setLeft(lblEsquerda = new Label("Esquerda"));
		borderPane.setBottom(lblBaixo = new Label("Baixo"));
		borderPane.setRight(lblDireita = new Label("Direita"));
		borderPane.setCenter(lblCentro = new Label("Centro"));
		BorderPane.setAlignment(lblTop, Pos.CENTER);
		BorderPane.setAlignment(lblEsquerda, Pos.CENTER);
		BorderPane.setAlignment(lblBaixo, Pos.CENTER);
		BorderPane.setAlignment(lblDireita, Pos.CENTER);
		BorderPane.setAlignment(lblCentro, Pos.CENTER);
		tabBorderPane.setContent(borderPane);

		Tab tabFlowPane = new Tab("Flow Pane");
		FlowPane flowPane = new FlowPane();
		// define a posição dos elementos: centro, centro para a direita, centro
		// para esquerda, topo para esquerda, etc. Tente outros valores para se
		// testar!
		flowPane.setAlignment(Pos.CENTER_RIGHT);
		for (int i = 0; i < 10; i++) {
			flowPane.getChildren().add(new Label("Label " + i));
		}
		tabFlowPane.setContent(flowPane);

		painelLayouts.getTabs().addAll(tabGridPane, tabBorderPane, tabFlowPane);

		raiz.getChildren().add(painelLayouts);
		Scene cena = new Scene(raiz, 350, 150);
		palco.setTitle("Gerenciadores de Layout II");
		palco.setScene(cena);
		palco.show();

	}
}
