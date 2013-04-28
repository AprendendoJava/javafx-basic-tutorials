package main;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControlesSimples extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage palco) throws Exception {
		VBox raiz = new VBox(10); // 1
		raiz.setAlignment(Pos.CENTER); // 2

		Label rotuloDemo = new Label("Sou um rótulo de texto!"); // 3
		rotuloDemo.setTooltip(new Tooltip(
				"Esse é um rótulo para mostrar textos de forma simples")); // 4

		TextField campoTexto = new TextField("Digite algo"); // 5
		campoTexto.setTooltip(new Tooltip(
				"Campo de texto para entrada de uma só linha "));

		TextArea areaTexto = new TextArea("Digite algo com várias linhas"); // 6
		areaTexto.setTooltip(new Tooltip(
				"Campo de texto para entrada de múltiplas linhas"));

		Separator separadorHorizontal = new Separator(); // 7
		Separator separadorVertical = new Separator(Orientation.VERTICAL); // 8
		Slider deslizante = new Slider(); // 9
		deslizante.setShowTickLabels(true); // 10
		deslizante.setShowTickMarks(true); // 11
		deslizante
				.setTooltip(new Tooltip(
						"O controle deslizante tem um valor numérico de acordo com sua posição"));

		raiz.getChildren().addAll(rotuloDemo, campoTexto, areaTexto,
				separadorVertical, separadorHorizontal, deslizante);

		Scene cena = new Scene(raiz, 300, 400);
		palco.setTitle("Controles Básicos I");
		palco.setScene(cena);
		palco.show();
	}
}