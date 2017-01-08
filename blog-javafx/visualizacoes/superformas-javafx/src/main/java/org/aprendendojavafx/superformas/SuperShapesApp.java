package org.aprendendojavafx.superformas;

import org.fxapps.drawingfx.DrawingApp;
import static java.lang.Math.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SuperShapesApp extends DrawingApp {

	int numberOfPoints = 1300;

	private double maxPhi = Math.PI * 2;
	private double m = 1, n1 = 0.3, n2 = 0.3, n3 = 0.3;

	public static void main(String[] args) {
		launch();
	}

	public void setup() {
		title = "Super Shapes";
		width = 800;
		height = 600;
		frames = 1;
		ctx.setFont(Font.font(30));
		ctx.setStroke(Color.NAVY);
	}

	public void draw() {
		ctx.setFill(Color.LIGHTBLUE);
		ctx.fillRect(0, 0, width, height);
		double[][] points = new double[numberOfPoints][2];
		for (int i = 0; i < numberOfPoints; i++) {
			double phi = toDegrees(i * (maxPhi) / numberOfPoints); 
			double r = superShape(m, n1, n2, n3, phi);
			double x = 250 * r * cos(phi) + width / 2;
			double y = 250 * r * sin(phi) + height / 2;
			points[i][0] = x;
			points[i][1] = y;

		}
		for (int i = 0; i < numberOfPoints; i++) {
			ctx.strokeOval(points[i][0], points[i][1], 2, 2);
		}
		ctx.setFill(Color.WHITE);
		String str = String.format("M=%.1f,  N1=%.1f, N2=%.1f, N3=%.1f, PHI= %s", 
				m, n1, n2, n3, maxPhi == PI * 2 ? "2PI" : "12PI");
		ctx.fillText(str, 50, height - 20);
		m += 0.20;
		if (m > 10) {
			if (n1 == 1) {
				n1 = n2 = n3 = 0.3;
			} else if (maxPhi == (PI * 2)) {
				maxPhi = PI * 12;
			} else {
				n1 = n2 = n3 = 1;
				maxPhi = PI * 2;
			}
			m = 1;
		}
	}

	private double superShape(double m, double n1, double n2, double n3, double phi) {
		double r, t1, t2, a = 1, b = 1;

		t1 = cos(m * phi / 4) / a;
		t1 = abs(t1);
		t1 = pow(t1, n2);

		t2 = sin(m * phi / 4) / b;
		t2 = abs(t2);
		t2 = pow(t2, n3);

		r = pow(t1 + t2, 1 / n1);
		return abs(r) == 0 ? 0 : 1 / r;
	}

}