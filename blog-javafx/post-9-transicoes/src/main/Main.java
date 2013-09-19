package main;

import main.Main.FabricaTransicao.Transicoes;
import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	// A transição atualmente selecionada
	private static Transition transicaoAtual;
	private static Text alvo;
	// o grupo de botões das transações
	private static ToggleGroup botoesTransicao;
	private static Slider sldTempo;
	private static BorderPane raiz;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage palco) throws Exception {
		raiz = new BorderPane();

		HBox hbSuperior = criaPainelSuperior();
		BorderPane.setMargin(hbSuperior, new Insets(20));
		raiz.setTop(hbSuperior);

		HBox hbInferior = criaPainelInferior();
		BorderPane.setMargin(hbInferior, new Insets(10));
		raiz.setBottom(hbInferior);

		raiz.setCenter(alvo);
		criaNoAlvo();

		Scene cena = new Scene(raiz, 800, 600);
		palco.setTitle("Testando Transições do JavaFX");
		palco.setScene(cena);
		palco.show();
	}

	private void criaNoAlvo() {
		// configurar coisas do texto alvo...
		alvo = new Text("** Transições **");
		alvo.setFont(new Font(60));
		// efeitinsss
		Reflection efeito = new Reflection();
		efeito.setFraction(0.7);
		alvo.setEffect(efeito);
		alvo.setFill(Color.RED);
		raiz.setCenter(alvo);

	}

private HBox criaPainelSuperior() {
	HBox hbTopo = new HBox(10);
	hbTopo.setSpacing(10);
	hbTopo.setAlignment(Pos.CENTER);
	Transicoes[] transicoes = Transicoes.values();
	// grupo para todas as transições
	botoesTransicao = new ToggleGroup();
	for (int i = 0; i < transicoes.length; i++) {
		Transicoes t = transicoes[i];
		ToggleButton tb = new ToggleButton(t.name());
		tb.setUserData(t);
		if (i == 0) {
			tb.setSelected(true);
		}
		tb.setToggleGroup(botoesTransicao);
		hbTopo.getChildren().add(tb);
	}
	return hbTopo;
}

	private HBox criaPainelInferior() {
		HBox hbBotoes = new HBox();
		final Button btnParar = new Button("Parar");
		final Button btnTocar = new Button("Tocar");
		final Button btnPausar = new Button("Pausar");
		final Button btnAjusta = new Button("Ajustar");
		sldTempo = new Slider(1, 10, 5);
		sldTempo.setShowTickLabels(true);
		sldTempo.setShowTickMarks(true);
		sldTempo.setMinorTickCount(1);
		sldTempo.setMajorTickUnit(1);
		hbBotoes.setAlignment(Pos.CENTER);
		hbBotoes.setSpacing(20);
		hbBotoes.getChildren().addAll(btnTocar, btnPausar, btnParar, btnAjusta,
				new Label("Duração (s):"), sldTempo);

		// setando as ações. Muito código gasto aqui! Poderíamos ter só uma ação
		// e setar a mesma em todos os botões ou usar os lambdas no Java 8
		btnParar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				transicaoAtual.stop();
			}
		});
btnTocar.setOnAction(new EventHandler<ActionEvent>() {
	@Override
	public void handle(ActionEvent e) {
		// antes de tocar, pegamos a mais nova transição selecionada
		Transicoes t = (Transicoes) botoesTransicao.getSelectedToggle()
				.getUserData();
		transicaoAtual = FabricaTransicao.fazerTransicao(t,
				sldTempo.getValue(), alvo);
		// lógicas de habilitação dos botões, temos que setar todas as
		// vezes pq trocamos as transições
		btnParar.disableProperty().bind(
				transicaoAtual.statusProperty().isNotEqualTo(
						Status.RUNNING));
		btnTocar.disableProperty().bind(
				transicaoAtual.statusProperty().isEqualTo(
						Status.RUNNING));
		btnPausar.disableProperty().bind(
				transicaoAtual.statusProperty().isNotEqualTo(
						Status.RUNNING));
		btnAjusta.disableProperty().bind(
				transicaoAtual.statusProperty().isEqualTo(
						Status.RUNNING));
		sldTempo.disableProperty().bind(
				transicaoAtual.statusProperty().isEqualTo(
						Status.RUNNING));
		System.out.println("Tocando transição " + t);
		transicaoAtual.play();
	}
});
		btnPausar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				transicaoAtual.pause();
			}
		});
		btnAjusta.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				criaNoAlvo();
			}
		});
		return hbBotoes;
	}

	/**
	 * Usamos essa classe para criar nossa transição e focar na view lá em cima.
	 * Aqui fica a lógica das transições
	 * 
	 * @author william
	 * 
	 */
public static class FabricaTransicao {

	public static enum Transicoes {
		FADE, TRANSLATE, SCALE, FILL, ROTATE
	}

	public static Transition fazerTransicao(Transicoes transicao,
			double duracaoSegundos, Node alvo) {
		Duration duracao = new Duration(duracaoSegundos * 1000);
		Transition t = null;

		switch (transicao) {
		case FADE:
			FadeTransition fadeTransition = new FadeTransition();
			fadeTransition.setFromValue(1);
			fadeTransition.setToValue(0);
			fadeTransition.setDuration(duracao);
			fadeTransition.setNode(alvo);
			t = fadeTransition;
			break;
		case FILL:
			FillTransition fillTransition = new FillTransition();
			fillTransition.setFromValue(Color.RED);
			fillTransition.setToValue(Color.DARKGREEN);
			fillTransition.setDuration(duracao);
			fillTransition.setShape((Shape) alvo);
			t = fillTransition;
			break;
		case ROTATE:
			RotateTransition rotateTransition = new RotateTransition();
			rotateTransition.setByAngle(360);
			rotateTransition.setDuration(duracao);
			rotateTransition.setNode(alvo);
			t = rotateTransition;
			break;
		case SCALE:
			ScaleTransition scaleTransition = new ScaleTransition();
			scaleTransition.setFromX(1);
			scaleTransition.setFromY(1);
			scaleTransition.setToX(4);
			scaleTransition.setToY(4);
			scaleTransition.setDuration(duracao);
			scaleTransition.setNode(alvo);
			t = scaleTransition;
			break;
		case TRANSLATE:
			TranslateTransition translateTransition = new TranslateTransition();
			translateTransition.setToX(600);
			translateTransition.setToY(250);
			translateTransition.setDuration(duracao);
			translateTransition.setNode(alvo);
			t = translateTransition;
			break;
		}
		t.setAutoReverse(true);
		t.setCycleCount(2);
		return t;
	}
	}
}
