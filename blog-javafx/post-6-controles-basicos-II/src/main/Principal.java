package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Principal extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage palco) throws Exception {
		VBox raiz = new VBox(10);
		raiz.setTranslateX(10);
		raiz.setTranslateY(20);

		Label lblTitulo = new Label("Pesquisa sobre Programação");
		lblTitulo.setUnderline(true); // 1

		final TextField txtNome = new TextField();
		HBox hbNome = new HBox(10); // 2
		hbNome.getChildren().addAll(new Label("Nome"), txtNome);

		HBox hbSo = new HBox(20);
		ToggleButton tbLinux = new ToggleButton("Linux"); // 3
		tbLinux.setSelected(true);
		ToggleButton tbWindows = new ToggleButton("Windows");
		ToggleButton tbMac = new ToggleButton("Mac");
		final ToggleGroup tgSo = new ToggleGroup(); // 4
		tgSo.getToggles().addAll(tbLinux, tbWindows, tbMac); // 5
		hbSo.getChildren().addAll(new Label("Sistema Operacional utilizado"),
				tbLinux, tbWindows, tbMac);

		final ToggleGroup tgLinguagem = new ToggleGroup();
		HBox hbLinguagens = new HBox(20);
		RadioButton rbJava = new RadioButton("Java"); // 6
		rbJava.setSelected(true);
		RadioButton rbC = new RadioButton("C");
		RadioButton rbPython = new RadioButton("Python");
		tgLinguagem.getToggles().addAll(rbJava, rbC, rbPython);
		hbLinguagens.getChildren().addAll(
				new Label("Linguagem de programação Predileta:"), rbJava, rbC,
				rbPython);

		final CheckBox chkFrequencia = new CheckBox("Programa todo dia?"); // 7
		final CheckBox chkGosto = new CheckBox("Gosta de programação?");
		chkGosto.setAllowIndeterminate(true); // 8
		chkGosto.setIndeterminate(true);

		Button btnSubmeter = new Button("Submeter pequisa");
		btnSubmeter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent evt) {

				System.out.println("\t\tResultado da pesquisa para \""
						+ txtNome.getText() + "\"\n");
				// Podemos não ter um SO selecionado
				ToggleButton tbSo = (ToggleButton) tgSo.getSelectedToggle(); // 9
				System.out.print("Sistema Operacional predileto: ");
				System.out.println(tbSo == null ? "Não selecionado." : tbSo
						.getText());

				// Deve ter uma linguagem selecionada
				RadioButton rbLinguagem = (RadioButton) tgLinguagem
						.getSelectedToggle();
				System.out.println("Linguagem de programação: "
						+ rbLinguagem.getText());
				// 10
				System.out.println((chkFrequencia.isSelected() == true ? "P"
						: "Não p") + "rograma todo dia.");

				System.out.print("Gosta de programação: ");
				if (chkGosto.isSelected()) {
					System.out.println("Sim.");
					// 11
				} else if (chkGosto.isIndeterminate()) {
					System.out.println("Não respondido.");
				} else {
					System.out.println("Não.");
				}
				System.out.println("\n\n");
			}
		});

		raiz.getChildren().addAll(lblTitulo, hbNome, hbSo, hbLinguagens,
				chkFrequencia, chkGosto, btnSubmeter);

		Scene cena = new Scene(raiz, 450, 250);
		palco.setTitle("Tratando eventos");
		palco.setScene(cena);
		palco.show();
	}
}