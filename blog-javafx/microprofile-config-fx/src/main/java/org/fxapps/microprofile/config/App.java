package org.fxapps.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

public class App extends Application {
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		final Config config = ConfigProvider.getConfig();
		TableView<Pair<String, String>> tableView = new TableView<>();
		Label lblProperty = new Label();
		TableColumn<Pair<String, String>, String> cKey = new TableColumn<>("Name");
		TableColumn<Pair<String, String>, String> cValue = new TableColumn<>("Value");
		cKey.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey()));
		cValue.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));
		cValue.setPrefWidth(500);
		tableView.getColumns().add(cKey);
		tableView.getColumns().add(cValue);
		config.getPropertyNames().forEach(p -> {
			String value = config.getValue(p, String.class);
			tableView.getItems().add(new Pair<>(p, value));
		});
		String myCustomProperty = config.getValue("my.custom.property",String.class);
		lblProperty.setText("Value for my.custom.property: " + myCustomProperty);
		stage.setTitle("Default Properties");
		stage.setScene(new Scene(new VBox(10, tableView, lblProperty), 800, 400));
		stage.show();
	}

}