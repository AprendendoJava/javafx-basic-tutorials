package org.aprendendojavafx.pixels;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	public static final float WIDTH = 600;
	public static final float HEIGHT = 400;

	int COLOR_MODES = 7;

	public static Random random = new Random();
	Blob[] blobs = new Blob[12];

	public static void main(String[] args) {
		launch();
	}

	int selectedColorMode = 1;
	boolean inverter = false;

	private Canvas canvas;

	@Override
	public void start(Stage stage) throws Exception {
		canvas = new Canvas(WIDTH, HEIGHT);

		for (int i = 0; i < blobs.length; i++) {
			blobs[i] = new Blob(random.nextFloat() * WIDTH, random.nextFloat() * HEIGHT);
		}

		canvas.setOnMouseClicked(e -> {
			if (e.isControlDown())
				inverter = !inverter;
			else if (selectedColorMode > COLOR_MODES)
				selectedColorMode = 1;
			else
				selectedColorMode++;
		});

		stage.setTitle("MetaBalls example with JavaFX");
		stage.show();
		stage.setScene(new Scene(new StackPane(canvas), WIDTH, HEIGHT));
		KeyFrame frame = new KeyFrame(Duration.millis(100), e -> draw());
		Timeline timeline = new Timeline(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	private void draw() {
		GraphicsContext ctx = canvas.getGraphicsContext2D();
		ctx.clearRect(0, 0, WIDTH, HEIGHT);
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				int total = 0;
				for (Blob blob : blobs) {
					double d = distance(x, y, blob.posX, blob.posY);
					total += 15 * (blob.raio / d);
				}
				if (total > 255)
					total = 255;
				Color color = selectColor(total);
				ctx.getPixelWriter().setColor(x, y, color);
			}
		}

		for (int i = 0; i < blobs.length; i++) {
			blobs[i].update();
		}
	}

	private Color selectColor(int total) {
		int inverso = 255;
		if (inverter) {
			inverso = 255 % total;
		}
		Color color = Color.rgb(total, total, total);
		switch (selectedColorMode) {
		case (1):
			color = Color.rgb(total, total, total);
			break;
		case (2):
			color = Color.rgb(total, total, inverso);
			break;
		case (3):
			color = Color.rgb(total, inverso, total);
			break;
		case (4):
			color = Color.rgb(total, inverso, inverso);
			break;
		case (5):
			color = Color.rgb(inverso, total, total);
			break;
		case (6):
			color = Color.rgb(inverso, total, inverso);
			break;
		case (7):
			color = Color.rgb(inverso, inverso, total);
		}
		return color;
	}

	private double distance(double x, double y, double x2, double y2) {
		return sqrt(pow(x2 - x, 2) + pow(y2 - y, 2));
	}

}