package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExplorandoClasseNode extends Application {

	// Aqui criamos o nosso nó principal. Veja que podemos modificar o método
	// criaNo para criar o nó que queremos!
	Node alvo = criaNo();

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage palco) throws Exception {

		HBox raiz = new HBox(40);
		raiz.getChildren()
				.addAll(criarPainelEsquerda(), criarPainelPrincipal());
		Scene cena = new Scene(raiz);
		palco.setScene(cena);
		palco.setWidth(600);
		palco.setHeight(400);
		palco.setTitle("Explorando a classe nó");
		palco.show();
	}

	private Node criaNo() {
		// Modifique esse método para criar o nó que você quiser e retornar! O
		// programa vai funcionar independente do nó que você cira
		Text texto = new Text("\\o/ exemplo \\o/  ");
		texto.setFont(new Font(40));

		Rectangle rect = new Rectangle(200, 150);
		rect.setFill(Color.RED);

		HBox caixinha = new HBox(20);
		caixinha.getChildren().addAll(new Rectangle(80, 60), new Circle(30),
				new Button("Um botão de teste"), new Text("um texto"));
		caixinha.setAlignment(Pos.CENTER);

		// crie seus próprios nós e retorne aqui!
		return texto;
	}

	private Node criarPainelEsquerda() {
		Accordion accrAtributosNo = new Accordion();
		TitledPane painelExpandido = criaPainelTransformacoes();
		accrAtributosNo.getPanes().addAll(painelExpandido,
				criaPainelAtributosImportantes(), criaPainelEventos());
		accrAtributosNo.setMinWidth(200);
		accrAtributosNo.setExpandedPane(painelExpandido);
		return accrAtributosNo;
	}

	private TitledPane criaPainelTransformacoes() {
		final VBox pnlBotoes = new VBox(10);
		final Slider sldEscalaX, sldEscalaY, sldRotacao;
		final Button btnIncrementaX, btnDecrementaX, btnIncrementaY, btnDecrementaY;
		pnlBotoes.setTranslateX(2);
		pnlBotoes.setTranslateY(10);
		ObservableList<Node> nos = pnlBotoes.getChildren();

		nos.addAll(new Label("Rotação:"), sldRotacao = new Slider(0, 360, 0),
				new Separator(Orientation.HORIZONTAL));

		HBox posX = new HBox(5);
		posX.getChildren().addAll(new Label("X: "),
				btnIncrementaX = new Button("+"),
				btnDecrementaX = new Button("-"));
		HBox posY = new HBox(5);
		posY.getChildren().addAll(new Label("Y: "),
				btnIncrementaY = new Button("+"),
				btnDecrementaY = new Button("-"));
		nos.addAll(new Label("Posição:"), posX, posY, new Separator(
				Orientation.HORIZONTAL));

		nos.add(new Label("Escala:"));
		HBox hbEscalaX = new HBox(5);
		hbEscalaX.getChildren().addAll(new Label("X: "),
				sldEscalaX = new Slider(1, 2, 1));
		HBox hbEscalaY = new HBox(5);
		hbEscalaY.getChildren().addAll(new Label("Y: "),
				sldEscalaY = new Slider(1, 2, 1));
		nos.addAll(hbEscalaX, hbEscalaY);
		TitledPane pnlTransformacoes = new TitledPane("Transformações",
				pnlBotoes);
		pnlTransformacoes.setExpanded(true);

		// aqui vamos "amarrar" as propriedades dos controles da tela com as
		// propriedades do nó
		sldEscalaX.valueProperty().bindBidirectional(alvo.scaleXProperty());
		sldEscalaY.valueProperty().bindBidirectional(alvo.scaleYProperty());
		sldRotacao.valueProperty().bindBidirectional(alvo.rotateProperty());

		// um listener para os botões que movimentam o nó
		EventHandler<ActionEvent> cliqueBotoesMovimento = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent evt) {
				// de acordo com o botão clicado vamos manipular nosso nó
				Object botaoClicado = evt.getSource();
				if (botaoClicado == btnDecrementaX) {
					alvo.setTranslateX(alvo.getTranslateX() - 1);
				} else if (botaoClicado == btnIncrementaX) {
					alvo.setTranslateX(alvo.getTranslateX() + 1);
				} else if (botaoClicado == btnDecrementaY) {
					alvo.setTranslateY(alvo.getTranslateY() - 1);
				} else if (botaoClicado == btnIncrementaY) {
					alvo.setTranslateY(alvo.getTranslateY() + 1);
				}
			}
		};
		btnDecrementaX.setOnAction(cliqueBotoesMovimento);
		btnIncrementaX.setOnAction(cliqueBotoesMovimento);
		btnDecrementaY.setOnAction(cliqueBotoesMovimento);
		btnIncrementaY.setOnAction(cliqueBotoesMovimento);
		return pnlTransformacoes;
	}

	private TitledPane criaPainelEventos() {
		TitledPane pnlEventos = new TitledPane();
		final TextField txtClique, txtEntrou, txtSaiu, txtArrastando, txtMover, txtPressionado, txtSolto, txtTecla;
		pnlEventos.setText("Eventos");
		GridPane pnlCampos = new GridPane();
		pnlCampos.setVgap(10);
		pnlCampos.setHgap(5);
		VBox vbPrincipal = new VBox(15);

		pnlCampos.add(new Label("Clicar"), 0, 0);
		pnlCampos.add(txtClique = new TextField("clique!"), 1, 0);
		pnlCampos.add(new Label("Mouse entrar"), 0, 1);
		pnlCampos.add(txtEntrou = new TextField("Mouse entrou"), 1, 1);
		pnlCampos.add(new Label("Mouse sair"), 0, 2);
		pnlCampos.add(txtSaiu = new TextField("Mouse saiu"), 1, 2);
		pnlCampos.add(new Label("Mouse arrastar"), 0, 3);
		pnlCampos.add(txtArrastando = new TextField("Arrastando o mouse..."),
				1, 3);
		pnlCampos.add(new Label("Mouse Mover"), 0, 4);
		pnlCampos.add(txtMover = new TextField("Mouse movendo"), 1, 4);
		pnlCampos.add(new Label("Mouse pressionado"), 0, 5);
		pnlCampos.add(txtPressionado = new TextField(
				"Botão do mouse pressionado"), 1, 5);
		pnlCampos.add(new Label("Mouse Solto"), 0, 6);
		pnlCampos.add(txtSolto = new TextField("Botão do mouse solto"), 1, 6);
		pnlCampos.add(new Label("Apertar tecla"), 0, 7);
		pnlCampos.add(txtTecla = new TextField("Tecla apertada"), 1, 7);
		vbPrincipal.getChildren().addAll(new Label("Mensagem quando: "),
				pnlCampos);
		pnlEventos.setContent(vbPrincipal);

		EventHandler<Event> eventHandler = new EventHandler<Event>() {
			@Override
			public void handle(Event evt) {
				String texto = "";
				String tipoEvento = evt.getEventType().toString();
				// o evento tem uma representação em String que eu vi no código
				// do JavaFX
				switch (tipoEvento) {
				case "MOUSE_CLICKED":
					texto = txtClique.getText();
					break;
				case "MOUSE_ENTERED":
					texto = txtEntrou.getText();
					break;
				case "MOUSE_EXITED":
					texto = txtSaiu.getText();
					break;
				case "MOUSE_DRAGGED":
					texto = txtArrastando.getText();
					break;
				case "MOUSE_MOVED":
					texto = txtMover.getText();
					break;
				case "MOUSE_RELEASED":
					texto = txtSolto.getText();
					break;
				case "MOUSE_PRESSED":
					texto = txtPressionado.getText();
					break;
				case "KEY_PRESSED":
					texto = txtTecla.getText();
					break;
				default:
					texto = "Evento desconhecido: " + tipoEvento;
					break;
				}
				System.out.println("Nó: '" + alvo.getId() + "'; Evento: "
						+ texto);
			}
		};
		// adiciona o handler genérico para todos os eventos
		alvo.addEventHandler(Event.ANY, eventHandler);
		return pnlEventos;
	}

	private TitledPane criaPainelAtributosImportantes() {
		// constantes para seleção dos efeitos. Pq você não cria os seus próprios? :)
		final String NENHUM = "Nenhum", BORRAR = "Borrar", REFLEXAO = "Reflexão", SOMBRA = "Sombra";
		TitledPane pnlAtributos = new TitledPane();
		VBox hbAtributos = new VBox(10);
		ChoiceBox<String> cbEfeitos = new ChoiceBox<>();
		CheckBox ckVisivel = new CheckBox("Visível");
		CheckBox ckDesabilitado = new CheckBox("Desabilitado");
		TextField txtId = new TextField("AlgumId");
		// efeitos que vamos usar, você pode criar os seus
		final BoxBlur efeitoBorrar = new BoxBlur(10, 3, 3);
		final Reflection efeitoReflexao = new Reflection();
		efeitoReflexao.setFraction(0.7);
		final DropShadow efeitoSombra = new DropShadow(5, 3, 3, Color.color(
				0.4, 0.5, 0.5));

		cbEfeitos.getItems().addAll(NENHUM, BORRAR, SOMBRA, REFLEXAO);
		cbEfeitos.getSelectionModel().select(0);
		ckVisivel.setSelected(true);
		hbAtributos.getChildren().addAll(new Label("Efeito"), cbEfeitos,
				new Label("ID:"), txtId, ckVisivel, ckDesabilitado);

		alvo.visibleProperty().bind(ckVisivel.selectedProperty());
		alvo.disableProperty().bind(ckDesabilitado.selectedProperty());
		alvo.idProperty().bind(txtId.textProperty());

		pnlAtributos.setText("Atributos");
		// quando mudar o item da combo, o método on changed vai ser chamado
		cbEfeitos.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {
					@Override
					public void changed(
							ObservableValue<? extends String> valor,
							String antigo, String novo) {
						switch (valor.getValue().toString()) {
						case BORRAR:
							alvo.setEffect(efeitoBorrar);
							break;
						case SOMBRA:
							alvo.setEffect(efeitoSombra);
							break;
						case REFLEXAO:
							alvo.setEffect(efeitoReflexao);
							break;
						case NENHUM:
							alvo.setEffect(null);
							break;
						default:
							break;
						}
					}
				});
		pnlAtributos.setContent(hbAtributos);
		return pnlAtributos;
	}

	private Node criarPainelPrincipal() {
		StackPane painelNoPrincipal = new StackPane();
		painelNoPrincipal.getChildren().add(alvo);
		painelNoPrincipal.setAlignment(Pos.CENTER);
		return painelNoPrincipal;
	}
}