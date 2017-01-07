package org.aprendendojavafx.pixels;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static java.lang.Math.*;

public class Vermelhao extends Application {

	private static final float WIDTH = 800;
	private static final float HEIGHT = 600;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		System.out.println(sqrt(pow(0 - 400, 2) + pow(0 - 300, 2)));
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext ctx = canvas.getGraphicsContext2D();
		final PixelWriter pixelWriter = ctx.getPixelWriter();
		double maiorDistancia = sqrt(pow(0 - 800, 2) + pow(0 - 600, 2)) / 2;
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				double d = Math.sqrt(Math.pow((WIDTH / 2) - i, 2) + Math.pow((HEIGHT / 2) - j, 2));
				d = d / maiorDistancia;
				pixelWriter.setColor(i, j, Color.color(1 - d, 0, 0));
			}
		}
		stage.setTitle("Manipulando Pixel a Pixel de um canvas");
		stage.show();
		stage.setScene(new Scene(new StackPane(canvas), WIDTH, HEIGHT));
	}

}