package org.aprendendojavafx.crud.view;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * Classe principal para rodar a aplicação
 * 
 * @author wsiqueir
 *
 */
public class App extends Application {
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		URL fxml = getClass().getResource("./contas.fxml");
		Parent parent = FXMLLoader.load(fxml);
		stage.setTitle("Contas a pagar!");
		stage.setScene(new Scene(parent));
		stage.show();
	}

}