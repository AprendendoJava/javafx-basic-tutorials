package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application { // 1

	public static void main(String[] args) {
		launch(); // 2
	}

	@Override
	public void start(Stage palco) throws Exception { // 3
		StackPane raiz = new StackPane(); // 4
		Label lblMensagem = new Label(); // 5

		lblMensagem.setText("Estou aprendendo JavaFX!"); // 6
		raiz.getChildren().add(lblMensagem); // 7

		Scene cena = new Scene(raiz, 250, 100); // 8
		palco.setTitle("Aprendendo JavaFX"); // 9
		palco.setScene(cena); // 10
		palco.show(); // 11

	}
}
