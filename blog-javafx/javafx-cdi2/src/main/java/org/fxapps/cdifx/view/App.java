package org.fxapps.cdifx.view;

import java.io.IOException;
import java.net.URL;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.fxapps.cdifx.conf.StartupScene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App {

	@Inject FXMLLoader fxmlLoader;

	public void start(@Observes @StartupScene Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource("./hello-world.fxml");
			Parent fxmlParent = fxmlLoader.load(arquivoFXML.openStream());
			stage.setScene(new Scene(fxmlParent, 300, 100));
			stage.setTitle("Hello World FXML and JavaFX");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
