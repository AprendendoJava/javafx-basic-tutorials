/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jugvale;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.effect.Effect;
import javafx.scene.effect.FloatMap;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author william
 */
public class TestandoEfeitos extends Application {

    final String IMG_URL = TestandoEfeitos.class.getResource("/aprendendo_java.png").toExternalForm();

    final Effect[] efeitos = {
        null,
        criarEfeitoShadow(),
        criarEfeitoReflection(),
        criarEfeitoBoxBlur(),
        criarEfeitoSepia(),
        criarEfeitoGaussianBlur(),
        criarBloom(),
        criarDisplacementMap(),
        criarMotionBlur()
    };

    @Override
    public void start(Stage stage) {
        // pega o nome das classes que estão nos efeitos e transforma em uma list para o ComboBox
        ObservableList<String> nomesEfeitos = FXCollections.observableList(
                Stream.of(efeitos)
                .map(e -> e == null ? "Nenhum efeito" : e.getClass().getSimpleName())
                .collect(Collectors.toList())
        );
        ComboBox<String> cmbEfeitos = new ComboBox<>(nomesEfeitos);
        BorderPane pnlRaiz = new BorderPane();
        ImageView alvo = new ImageView(new Image(IMG_URL));
        Rectangle rect = new Rectangle(800, 600);

        rect.setFill(Color.WHITE);
        alvo.setFitHeight(300);
        alvo.setFitWidth(300);
        pnlRaiz.setTop(cmbEfeitos);
        pnlRaiz.setCenter(new StackPane(rect, alvo));

        BorderPane.setAlignment(cmbEfeitos, Pos.CENTER);
        BorderPane.setMargin(cmbEfeitos, new Insets(10));

        cmbEfeitos.getSelectionModel()
                .selectedIndexProperty().addListener((o, v, n) -> {
                    alvo.setEffect(efeitos[n.intValue()]);
                });

        stage.setWidth(800);
        stage.setHeight(600);
        stage.setScene(new Scene(pnlRaiz));
        stage.setTitle("Testando os efeitos");
        stage.show();
        cmbEfeitos.getSelectionModel().select(0);
    }

    /*
     Os métodos criados dos efeitos estão abaixos. Sinta-se livre para brincar com os efeitos
     */
    private Shadow criarEfeitoShadow() {
        return new Shadow(20, Color.LIGHTBLUE);
    }

    private Reflection criarEfeitoReflection() {
        Reflection r = new Reflection();
        r.setFraction(0.7);
        return r;
    }

    private BoxBlur criarEfeitoBoxBlur() {
        return new BoxBlur();
    }

    private SepiaTone criarEfeitoSepia() {
        return new SepiaTone(40);
    }

    private GaussianBlur criarEfeitoGaussianBlur() {
        return new GaussianBlur(Math.PI);
    }

    private Bloom criarBloom() {
        return new Bloom(0.3);
    }

    private DisplacementMap criarDisplacementMap() {
        // Exemplo pego da documentação
        int width = 220;
        int height = 100;
        FloatMap floatMap = new FloatMap();
        floatMap.setWidth(width);
        floatMap.setHeight(height);

        for (int i = 0; i < width; i++) {
            double v = (Math.sin(i / 20.0 * Math.PI) - 0.5) / 40.0;
            for (int j = 0; j < height; j++) {
                floatMap.setSamples(i, j, 0.0f, (float) v);
            }
        }

        DisplacementMap displacementMap = new DisplacementMap();
        displacementMap.setMapData(floatMap);
        return displacementMap;
    }

    private MotionBlur criarMotionBlur() {
        return new MotionBlur(45, Math.PI);
    }
}
