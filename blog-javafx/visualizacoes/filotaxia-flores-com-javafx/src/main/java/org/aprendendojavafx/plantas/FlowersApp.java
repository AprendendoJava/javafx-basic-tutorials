package org.aprendendojavafx.plantas;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import org.fxapps.drawingfx.DrawingApp;

import javafx.scene.paint.Color;

public class FlowersApp extends DrawingApp {

	Flower[] flowers = new Flower[20];

	public static void main(String[] args) {
		launch();
	}

	public void setup() {
		title = "Flowers App";
		width = 1200;
		height = 800;
		for (int i = 0; i < flowers.length; i++) {
			flowers[i] = new Flower(random.nextInt(width), random.nextInt(height));
		}
		frames = 300;
		background = Color.LIGHTYELLOW;
	}

	public void draw() {
		for (int i = 0; i < flowers.length; i++) {
			Flower p = flowers[i];
			p.update();
			p.show();
			if (p.grown) {
				flowers[i] = new Flower(random.nextInt(width), random.nextInt(height));
			}
		}
	}

	public class Flower {
		int posX, posY, n, duration, size, c = 1;
		double angle = 137.5, x, y;
		Color color, stroke;
		boolean grown = false;

		public Flower(int posX, int posY) {
			this.posX = posX;
			this.posY = posY;
			this.color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			this.stroke = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			size = random.nextInt(10) + 5;
			duration = random.nextInt(1500) + 100;
		}

		public void update() {
			double a = n * angle;
			double r = c * sqrt(n);
			x = r * cos(a) + posX;
			y = r * sin(a) + posY;
			if (!grown) {
				n++;
			}
			if (n > duration) {
				grown = true;
			}
		}

		public void show() {
			ctx.setFill(color);
			ctx.setStroke(stroke);
			ctx.strokeOval(x, y, size, size);
			ctx.fillOval(x, y, size, size);
		}
	}
}