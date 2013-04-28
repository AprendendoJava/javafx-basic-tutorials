package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

	String cores[] = { "Blue", "Black", "Red", "White", "Green", "Yellow",
			"Gray", "Pink", "Salmon" };

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage palco) throws Exception {
		StackPane raiz = new StackPane();

		HBox opcoes = new HBox(10);
		opcoes.setAlignment(Pos.CENTER);

		final Rectangle retangulo = new Rectangle(300, 100);

		ComboBox<String> cbCorCena = new ComboBox<>(); // 1
		ComboBox<String> cbCorRetangulo = new ComboBox<>();

		cbCorCena.getItems().addAll(cores); // 2
		cbCorRetangulo.getItems().addAll(cores);

		opcoes.getChildren().addAll(cbCorCena, cbCorRetangulo);

		raiz.getChildren().addAll(retangulo, opcoes);

		final Scene cena = new Scene(raiz, 450, 250);
		palco.setTitle("Uso de ComboBox");
		palco.setScene(cena);
		palco.show();
		// 3
		cbCorCena.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {
					@Override
					public void changed(
							ObservableValue<? extends String> valorObservado,
							String valorAntigo, String valorNovo) {
						cena.setFill(Color.valueOf(valorNovo));
					}

				});
		cbCorRetangulo.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {
					@Override
					public void changed(
							ObservableValue<? extends String> valorObservado,
							String valorAntigo, String valorNovo) {
						// 4
						retangulo.setFill(Color.valueOf(valorNovo));
					}

				});
		cbCorCena.getSelectionModel().select(0); // 5
		cbCorRetangulo.getSelectionModel().select(1);
	}
}