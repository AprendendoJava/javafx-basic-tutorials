package org.aprendendojavafx.fisica;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FisicaBasicaApp extends Application {

	private static final double W = 800;
	private static final double H = 600;
	private GraphicsContext gc;
	private List<Bola> bolas;
	// forças
	private Point2D gravidade;
	private Point2D vento;
	private double coeficienteFriccao = -1;
	private Point2D posicaoMouse;

	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Canvas canvas = new Canvas(W, H);
		gc = canvas.getGraphicsContext2D();
		
		Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> executar(gc)));
		tl.setCycleCount(Timeline.INDEFINITE);
		tl.play();
		
		canvas.setOnMouseClicked(e -> {
			if(bolas.stream().filter(b -> b.selecionada).count() > 0) {
				bolas.forEach(b -> b.selecionada = false);
				return;
			}
			bolas.stream().filter(this::colideComMouse).findFirst().ifPresent(b -> b.selecionada = true);
		});
		
		canvas.setOnMouseMoved(e -> posicaoMouse = new Point2D(e.getX(), e.getY()));
		
		stage.setScene(new Scene(new VBox(10, canvas, adicionaControles())));
		stage.show();
		inicializa();
		stage.setTitle("Brincando com Física e JavaFX");
		
	}
	
	private boolean colideComMouse(Bola b){
		return b.localizacao.add(b.tamanho / 2, b.tamanho / 2).distance(posicaoMouse) < b.tamanho;
	}

	private Node adicionaControles() {
		Label lblNovoCorpo = new Label("Novo Corpo");
		lblNovoCorpo.setFont(Font.font("", FontWeight.BOLD, 18));
		
		Button btnReiniciar = new Button("Reiniciar App");
		Button btnNovoCorpo = new Button("Adicionar Corpo");
		
		ToggleButton tbGravidade = new ToggleButton("Habilitar Gravidade");
		ToggleButton tbVento = new ToggleButton("Habilitar Vento");
		ToggleButton tbFriccao = new ToggleButton("Habilitar Atrito");
		
		Spinner<Double> spGravidadeY = new Spinner<>(0.0, 0.9, 0.1, 0.1);
		Spinner<Double> spConstanteFriccao = new Spinner<>(0.01, 0.2, 0.01, 0.01);
		Spinner<Double> spVentoX = new Spinner<>(-0.9, 0.9, 0.1, 0.1);

		Spinner<Integer> spMassaCorpo = new Spinner<>(1, 10, 5);
		ColorPicker cpCorCorpo = new ColorPicker();
		ColorPicker cpCorLinha = new ColorPicker();
		cpCorCorpo.setPromptText("Cor Corpo");
		cpCorCorpo.setPromptText("Cor Linha");
		
		btnReiniciar.setOnAction(e -> {
			tbFriccao.setSelected(false);
			tbGravidade.setSelected(false);
			tbVento.setSelected(false);
			inicializa();
		});
		
		btnNovoCorpo.setOnAction(e -> {
			Bola nova = new Bola(spMassaCorpo.getValue(), cpCorCorpo.getValue(), cpCorLinha.getValue());
			bolas.add(nova);
		});
		
		spGravidadeY.disableProperty().bind(tbGravidade.selectedProperty().not());
		spVentoX.disableProperty().bind(tbVento.selectedProperty().not());
		spConstanteFriccao.disableProperty().bind(tbFriccao.selectedProperty().not());

		spGravidadeY.valueProperty().addListener(l ->  gravidade = new Point2D(0, spGravidadeY.getValue()));
		spVentoX.valueProperty().addListener(l -> vento = new Point2D(spVentoX.getValue(), 0));
		spConstanteFriccao.valueProperty().addListener(l -> coeficienteFriccao = spConstanteFriccao.getValue());
		
		tbGravidade.selectedProperty().addListener((o, old, n) -> gravidade = n ? new Point2D(0, spGravidadeY.getValue()) : Point2D.ZERO);
		tbVento.selectedProperty().addListener((o, old, n) -> vento = n ? new Point2D(spVentoX.getValue(), 0) : Point2D.ZERO);
		tbFriccao.selectedProperty().addListener((o, old, n) -> coeficienteFriccao = n ? spConstanteFriccao.getValue(): -1);
	
		GridPane gpControles = new GridPane();
		gpControles.setVgap(10);
		gpControles.setHgap(5);
		
		
		gpControles.add(tbGravidade, 0, 0);
		gpControles.add(spGravidadeY, 1, 0);
		
		gpControles.add(tbVento, 0, 1);
		gpControles.add(spVentoX, 1, 1);
		
		gpControles.add(tbFriccao, 0, 2);
		gpControles.add(spConstanteFriccao, 1, 2);
		
		gpControles.add(new Separator(Orientation.VERTICAL), 2, 0, 1, 3);

		gpControles.add(lblNovoCorpo, 3, 0, 2, 1);
		gpControles.add(new Label("Cores"), 3, 1);
		gpControles.add(cpCorCorpo, 4, 1);
		gpControles.add(cpCorLinha, 5, 1);
		
		gpControles.add(new Label("Massa"), 3, 2);
		gpControles.add(spMassaCorpo, 4, 2);
		
		gpControles.add(btnNovoCorpo, 5, 2);
		
		gpControles.add(new Separator(), 0, 3, 6, 1);
		
		gpControles.add(btnReiniciar, 1, 4);
		
		return gpControles;
	}

	private void inicializa() {
		bolas = new ArrayList<>();
		bolas.add(new Bola(1, Color.DARKBLUE, Color.LIGHTBLUE));
		coeficienteFriccao = -1;
		gravidade = Point2D.ZERO;
		vento = Point2D.ZERO;
	}

	private void executar(GraphicsContext gc) {
		gc.setFill(Color.LIGHTGRAY);
		gc.fillRect(0, 0, W, H);
		
		for (Bola bola : bolas) {
			bola.atualizar();
			bola.desenhar();
			// a bola selecionada não respeita as forças
			if(bola.selecionada) {
				bola.localizacao = posicaoMouse.subtract(bola.tamanho / 2, bola.tamanho / 2);
				continue;
			}
			
			if (coeficienteFriccao != -1) {
				Point2D pos = bola.velocidade;
				Point2D friccao = pos.multiply(-1).normalize().multiply(coeficienteFriccao);
				bola.aplicaForca(friccao);
			}
			bola.aplicaForca(gravidade.multiply(bola.massa));
			bola.aplicaForca(vento);

			
		}
	}
	
	public class Bola {
		int massa, tamanho;
		Point2D localizacao, velocidade, aceleracao;
		private Color cor;
		private Color corLinha;
		boolean selecionada =  false;
		
		public Bola(int massa, Color cor, Color corLinha) {
			this.massa = massa;
			this.cor = cor;
			this.corLinha = corLinha;
			this.tamanho = massa * 15;
			localizacao = new Point2D(W / 2, H / 2);
			aceleracao = new Point2D(0, 0);
			velocidade = new Point2D(0, 0);
		}
		
		public void aplicaForca(Point2D forca) {
			double forcaX  = forca.getX() / massa;
			double forcaY  = forca.getY() / massa;
			aceleracao = aceleracao.add(forcaX, forcaY);
		}
		
		public void atualizar() {
			velocidade = velocidade.add(aceleracao); 
			localizacao = localizacao.add(velocidade);
			aceleracao = aceleracao.multiply(0);
			if(localizacao.getX() + tamanho > W ) {
				velocidade = new Point2D(velocidade.getX() *-1, velocidade.getY());
				localizacao = new Point2D(W - tamanho, localizacao.getY());
			} 
			if(localizacao.getX() < 0){
				velocidade = new Point2D(velocidade.getX() *-1, velocidade.getY());
				localizacao = new Point2D(0, localizacao.getY());
			}
			if(localizacao.getY() + tamanho > H) {
				velocidade = new Point2D(velocidade.getX(), velocidade.getY() *-1);
				localizacao = new Point2D(localizacao.getX(), H - tamanho);
			}
			if(localizacao.getY() < 0) {
				velocidade = new Point2D(velocidade.getX(), velocidade.getY() *-1);
				localizacao = new Point2D(localizacao.getX(), 0);
			}
		}
		
		public void desenhar() {
			gc.setLineWidth(2);
			gc.setFill(cor);
			gc.setStroke(corLinha);
			gc.fillOval(localizacao.getX(), localizacao.getY(),tamanho, tamanho);
			gc.strokeOval(localizacao.getX(), localizacao.getY(), tamanho, tamanho);
		}
	}

}