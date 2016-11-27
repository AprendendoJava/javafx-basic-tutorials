package org.aprendendojavafx.timer;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	final String SOM_TICK = "/sons/tick.mp3";
	final String SOM_GRANADA = "/sons/granada.mp3";
	
	final String COMECAR = "Começar";
	final String PARAR = "PARAR";

	private Slider sldValor;
	private Label lblValor;
	private Button btnAcao;

	// temporiza o tempo do slider
	private Timeline temporizador;

	int tempoFaltante;
	private MediaPlayer mpTick;
	private MediaPlayer mpGranada;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage palco) throws Exception {
		inicializacoes();
		VBox vbRaiz = constroiInterface();
		// não pode alterar o slider se já estiver tocando
		sldValor.disableProperty().bind(
				temporizador.statusProperty().isEqualTo(Status.RUNNING));
		ajustaLabel();
		
		// ajustes finais
		palco.setScene(new Scene(vbRaiz));
		palco.setWidth(600);
		palco.setHeight(400);
		palco.setTitle("Temporizador (parado)");
		palco.show();
		
		// deve existir forma melhor e fazer isso
		lblValor.textProperty().addListener(i -> {
			if(btnAcao.getText().equals(COMECAR)) {
				palco.setTitle("Temporizador (parado)");
			} else {
				palco.setTitle("Temporizador (" + lblValor.getText() + ")");
			}
		});
	}

	private void inicializacoes() {
		temporizador = new Timeline();
		temporizador.setCycleCount(Animation.INDEFINITE);
		
		String arquivoTick = Main.class.getResource(SOM_TICK).toString();
		String arquivoGranada = Main.class.getResource(SOM_GRANADA).toString();
		
		mpTick = new MediaPlayer(new Media(arquivoTick));
		// para evitar flood no play, só para qunado parar de tocar a mídia de vez
		mpTick.setOnEndOfMedia(() -> {
			mpTick.stop();
		});
		mpGranada = new MediaPlayer(new Media(arquivoGranada));
	}

	private VBox constroiInterface() {
		// inicializando nossos elementos de interface ativos
		lblValor = new Label();
		sldValor = new Slider(0, 30 * 60, (30 * 60) / 2);
		btnAcao = new Button(COMECAR);

		// configura as marquinhas do slider
		sldValor.setMajorTickUnit(100);
		sldValor.setBlockIncrement(1f);
		sldValor.setSnapToTicks(true);
		sldValor.setShowTickMarks(true);

		// configuraÇão do label
		lblValor.setFont(Font.font("", FontWeight.BOLD, 90));

		// o valor do label muda se mexermos o slider
		sldValor.valueProperty().addListener(l -> ajustaLabel());

		VBox vbRaiz = new VBox(50, lblValor, sldValor, btnAcao);
		// alinha os elementos ao centro
		vbRaiz.setAlignment(Pos.CENTER);

		// registra o listener do botão
		btnAcao.setOnAction(e -> acao());
		return vbRaiz;
	}

	private void ajustaLabel() {
		// só pode ajudar assim se for ajuste manual do slider
		if(sldValor.isDisable() == false) {
			int segundos = sldValor.valueProperty().intValue();
			atualizaLabelTempo(segundos);
		}
	}

	public void acao() {
		if (btnAcao.getText().equals(COMECAR)) {
			btnAcao.setText(PARAR);
			int segundos = sldValor.valueProperty().intValue();
			final KeyFrame frameSegundos = new KeyFrame(Duration.seconds(1),
					e -> atualizaValores());
			temporizador.getKeyFrames().setAll(frameSegundos);
			temporizador.playFrom(Duration.seconds(segundos));
			tempoFaltante = segundos;
		} else {
			btnAcao.setText(COMECAR);
			temporizador.stop();
		}
	}

	public void atualizaValores() {
		if(tempoFaltante < 1) {
			temporizador.stop();
			btnAcao.setText(COMECAR);
			mpGranada.stop();
			mpGranada.play();
			return;
		}
		tempoFaltante--;
		mpTick.play();
		atualizaLabelTempo(tempoFaltante);
		sldValor.setValue(tempoFaltante);
	}

	private void atualizaLabelTempo(int segundos) {
		String txtMinutos = String.format("%02d", segundos / 60);
		String txtSegundos = String.format("%02d", segundos % 60);
		lblValor.setText(txtMinutos + ":" + txtSegundos);
	}

}