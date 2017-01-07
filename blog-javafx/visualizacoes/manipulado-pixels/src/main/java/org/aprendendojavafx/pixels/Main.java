package org.aprendendojavafx.pixels;

import java.util.Random;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		canvas.setOnMouseMoved(e -> desenhaPixels(canvas, e));
		canvas.setCursor(Cursor.NONE);
		stage.setScene(new Scene(new StackPane(canvas), WIDTH, HEIGHT));
		stage.setTitle("Manipulando Pixel a Pixel de um canvas");
		stage.show();
	}

	private void desenhaPixels(Canvas canvas, MouseEvent e) {
		GraphicsContext ctx = canvas.getGraphicsContext2D();
		final PixelWriter pixelWriter = ctx.getPixelWriter();
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				pixelWriter.setColor(i, j, corAleatoria());
			}
		}
		// depois de manipular os pixels vc ainda pode mexer no canvas!
		ctx.setFill(corAleatoria());
		ctx.fillOval(e.getSceneX(), e.getSceneY(), 50, 50);
		
	}

	private Color corAleatoria() {
		final Random random = new Random();
		float r = random.nextFloat();
		float g = random.nextFloat();
		float b = random.nextFloat();
		Color color = Color.color(r, g, b);
		return color;
	}

}