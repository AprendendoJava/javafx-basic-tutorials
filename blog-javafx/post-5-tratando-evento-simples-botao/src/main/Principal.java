package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Se quisermos que essa classe trate evento, ela deve herdar de EventHandler
public class Principal extends Application implements EventHandler<ActionEvent> {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage palco) throws Exception {
		VBox raiz = new VBox(20);
		raiz.setAlignment(Pos.CENTER);
		raiz.setTranslateY(5);

		Button botao1 = new Button("Clique em mim! (Tratador externo)");
		Button botao2 = new Button("Clique em mim! (Class Anônima)");
		Button botao3 = new Button("Clique em mim! (Própria classe)");

		// usamos a classe TratadorEvento para cuidar dos eventos
		botao1.setOnAction(new TratadorEvento());
		// Criando uma instância de uma classe anônima para tratar evento
		botao2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent evento) {
				System.out.println("Evento tratado por uma classe anônima!");
			}
		});
		// o botão 3 usa essa própria classe para tratar seus eventos
		botao3.setOnAction(this);

		raiz.getChildren().addAll(botao1, botao2, botao3);

		Scene cena = new Scene(raiz, 300, 200);
		palco.setTitle("Tratando eventos");
		palco.setScene(cena);
		palco.show();

	}

	@Override
	public void handle(ActionEvent evento) {
		System.out.println("Evento tratado na própria classe!");
	}
}
