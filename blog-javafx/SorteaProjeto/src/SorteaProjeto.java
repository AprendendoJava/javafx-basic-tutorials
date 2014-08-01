/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
// TODO: Refatorar chamadas de métodos duplicadas...

/**
 *
 * @author william
 */
public class SorteaProjeto extends Application {

    final String INICIAL = "#";
    // TODO: COlocar esse valor na view...
    final int MAX = 7;
    // pode ser modificado para sortear qq outra coisa que não seja número...
    List<Object> paraSortear = geraListaNumericaSorteio(MAX);
    List<String> todasSorteadas = new ArrayList<>();
    Random geradorNumeros = new Random();
    SimpleStringProperty textoSorteado = new SimpleStringProperty(INICIAL);
    int i = 0;
    // "engine" de sorteio
    AnimationTimer timerPrincipal;
    // indica se está tocando a nossa "engine"
    SimpleBooleanProperty tocando = new SimpleBooleanProperty(false);
    SimpleBooleanProperty listaVazia = new SimpleBooleanProperty(false);

    // música do Sílvio Santos
    final private String AUDIO_URL = getClass().getResource("/recursos/ssantos.mp3").toString();
    AudioClip clip = new AudioClip(AUDIO_URL);

    final private String IMG_FUNDO = getClass().getResource("/recursos/logo4.jpg").toString();
    private final int ALTURA_APP = 600;
    private final int LARGURA_APP = 800;

    @Override
    public void start(Stage stage) {
        BorderPane raiz = new BorderPane();
        Text txtSorteado = new Text();
        Text txtSorteadas = new Text();
        TextField txtMax = new TextField();
        Text txtParaSortear = new Text(coletaParaSortear());
        Button btnComecar = new Button("VAI");
        Button btnParar = new Button("PARA");
        Button btnLimpar = new Button("LIMPA");
        HBox caixaBaixo = new HBox(20);
        VBox caixaCima = new VBox(10);
        ImageView imgFundo = new ImageView(IMG_FUNDO);

        txtSorteado.setFont(new Font(350));
        txtSorteado.textProperty().bind(textoSorteado);
        txtSorteado.setEffect(new InnerShadow(100, Color.BLACK));
        txtSorteado.setFill(Color.RED);
        
        txtMax.setPromptText("Número máximo para sortear");

        caixaCima.getChildren().addAll(txtMax, txtParaSortear, txtSorteadas);
        caixaBaixo.getChildren().addAll(btnComecar, btnParar, btnLimpar);
        raiz.setTop(caixaCima);
        raiz.setCenter(txtSorteado);
        raiz.setBottom(caixaBaixo);
        caixaBaixo.setAlignment(Pos.CENTER);

        // botões só estão habilitados de acordo com a timeline e com a lista
        btnComecar.disableProperty().bind(tocando.or(listaVazia));
        btnLimpar.disableProperty().bind(tocando);
        btnParar.disableProperty().bind(tocando.not());

        // toca a música pra sempre
        clip.setCycleCount(-1);

        imgFundo.setFitHeight(ALTURA_APP);
        imgFundo.setFitWidth(LARGURA_APP);
        imgFundo.setOpacity(0.4);
        imgFundo.setEffect(new SepiaTone(0.5));
        
        FadeTransition animacaoAposSorteio = new FadeTransition(new Duration(100));
        animacaoAposSorteio.setAutoReverse(true);
        animacaoAposSorteio.setCycleCount(10);
        animacaoAposSorteio.setFromValue(1);
        animacaoAposSorteio.setToValue(0);
        animacaoAposSorteio.setNode(txtSorteado);

        stage.setScene(new Scene(new StackPane(imgFundo, raiz)));
        stage.setTitle("Sorteador");
        stage.setWidth(LARGURA_APP);
        stage.setHeight(ALTURA_APP);
        stage.show();

        btnComecar.setOnAction(e -> {
            timerPrincipal.start();
            tocando.set(true);
            clip.play();
        });
        // muito código aqui... melhorar
        btnParar.setOnAction(e -> {
            timerPrincipal.stop();
            tocando.set(false);
            clip.stop();
            String textoSorteadoAtual = txtSorteado.getText();
            // remove o valor já sorteado dos números para sortear
            paraSortear.remove(textoSorteadoAtual);
            // atualiza a lista de textos sorteados
            todasSorteadas.add(textoSorteadoAtual);
            // atualiza os textos da tela com valores para sortear e já sorteados
            txtSorteadas.setText(coletaSorteados());
            txtParaSortear.setText(coletaParaSortear());
            // se a lista esvaziou, configura nosso booleano, aí desabilita o botão de start
            listaVazia.set(paraSortear.isEmpty());
            // toca animação pós efeito
            animacaoAposSorteio.play();
        });
        btnLimpar.setOnAction(e -> {
            todasSorteadas.clear();
            txtSorteadas.setText("");
            textoSorteado.set(INICIAL);
            int maxEntrado = Integer.parseInt(txtMax.getText());
            paraSortear = geraListaNumericaSorteio(maxEntrado);
            listaVazia.set(paraSortear.isEmpty());
            txtParaSortear.setText(coletaParaSortear());
        });
        // Engine de tudo..
        timerPrincipal = new AnimationTimer() {

            @Override
            public void handle(long l) {
                // verifica se a lista está vazia. Se estiver, nada é sorteado...
                if ((++i % 5) == 0) {
                    int n = geradorNumeros.nextInt(paraSortear.size());
                    String valor = String.valueOf(paraSortear.get(n));
                    textoSorteado.setValue(valor);
                }
            }
        };
    }

    private List<Object> geraListaNumericaSorteio(long max) {
        return LongStream.rangeClosed(1, max)
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
    }

    private String coletaSorteados() {
        return todasSorteadas.stream().collect(Collectors.joining(",", "Sorteados: ", ""));
    }

    private String coletaParaSortear() {
        return paraSortear.stream().map(String::valueOf).collect(Collectors.joining(",", "Para Sortear: ", ""));
    }
}