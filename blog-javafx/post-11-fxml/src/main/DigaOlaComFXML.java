package main;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DigaOlaComFXML extends Application {

	public static void main(String[] args) {
		launch(); 
	}

	@Override
	public void start(Stage palco) throws Exception {
		URL arquivoFXML = getClass().getResource(
				"./ola-mundo.fxml");
		Parent fxmlParent = (Parent) FXMLLoader.load(arquivoFXML);
		palco.setScene(new Scene(fxmlParent, 300, 100));
		palco.setTitle("Olá mundo com FXML");
		palco.show();
	}
}
