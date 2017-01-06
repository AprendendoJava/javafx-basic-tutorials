package org.aprendendojavafx.pixels;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class DrawingApp extends Application {

	public int frames = 10;
	public String title = "My App";
	
	public static float width = 600;
	public static float height = 400;
	
	public static Random random = new Random();

	Canvas canvas = new Canvas();
	GraphicsContext ctx = canvas.getGraphicsContext2D();

	@Override
	public void start(Stage stage) throws Exception {
		setup();
		canvas.setHeight(height);
		canvas.setWidth(width);
		StackPane raiz = new StackPane(canvas);
		stage.setTitle(title);
		stage.setScene(new Scene(raiz, width, height));
		stage.show();

		KeyFrame frame = new KeyFrame(Duration.millis(1000 / frames), e -> draw());
		Timeline timeline = new Timeline(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	abstract void setup();

	abstract void draw();
}