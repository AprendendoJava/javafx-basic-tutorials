package org.fxapps.repasse;

import org.fxapps.drawingfx.DrawingApp;

import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ValueBall {

	private String label, value;
	private int x, y, r;
	
	private Point2D startBoundary, endBoundary;

	private GraphicsContext gc;
	private Color textColor;
	private Color bg;
	private int newR;
	private DrawingApp app;
	private boolean selected;

	
	public ValueBall(int x, int y, int r, Color bg, Color textColor, String label, String value, DrawingApp app) {
		this(x, y, r, new Point2D(0, 0), new Point2D(app.width, app.height), bg, textColor, label, value, app);
	}
	
	public ValueBall(int x, int y, int r, Point2D startBoundary, Point2D endBoundary,  Color bg, Color textColor, String label, String value, DrawingApp app) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.startBoundary = startBoundary;
		this.endBoundary = endBoundary;
		this.bg = bg;
		this.textColor = textColor;
		this.label = label;
		this.value = value;
		this.app = app;
		this.gc = app.ctx;
		this.newR = r;
	}

	public void update() {
		if(r < newR) {
			r++;
		} else if(r > newR) {
			r--;
		} 
	}

	public void show() {
		gc.setFill(bg);
		gc.fillOval(x, y, r, r);
		if(selected) {
			gc.setLineWidth(2);
		} else {
			gc.setLineWidth(1);
		}
		gc.setStroke(bg.darker());
		gc.strokeOval(x, y, r, r);
		// it is dead!
		if(r > 0) {
			int labelSize = r / 25 + 10, valueSize = r / 25 + 8;
			if(selected) {
				labelSize +=5;
				valueSize+=5;
			}
			gc.setFill(textColor);
			gc.setStroke(textColor.darker());
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.BOTTOM);
			
			gc.setFont(Font.font(null, FontWeight.BOLD, labelSize));
			gc.fillText(label, x + r / 2, y + r / 2  );
			if(selected) {
				gc.strokeText(label, x + r / 2, y + r / 2  );
			}
			gc.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, valueSize));
			gc.fillText(value, x +  r /2, y + r / 2 + labelSize);
			if(selected) {
				gc.strokeText(value, x +  r /2, y + r / 2 + labelSize);
			}
		}
	}

	public boolean intersect(ValueBall v2){
		double minDist = this.newR / 2  + v2.newR / 2;
		int vx = this.getX() + this.newR / 2;
		int vy = this.getY() + this.newR / 2;
		int v2x = v2.getX() + v2.newR / 2;
		int v2y = v2.getY() + v2.newR / 2;
		return app.distance(vx, vy, v2x, v2y) < minDist;
	}
	
	public boolean intersect(double v2x, double v2y){
		double minDist = this.newR / 2 ;
		int vx = this.getX() + this.newR / 2;
		int vy = this.getY() + this.newR / 2;
		return app.distance(vx, vy, v2x, v2y) < minDist;
	}

	public int getNewR() {
		return newR;
	}

	public void setNewR(int newR) {
		this.newR = newR;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}
	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Point2D getStartBoundary() {
		return startBoundary;
	}

	public void setStartBoundary(Point2D startBoundary) {
		this.startBoundary = startBoundary;
	}

	public Point2D getEndBoundary() {
		return endBoundary;
	}

	public void setEndBoundary(Point2D endBoundary) {
		this.endBoundary = endBoundary;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
}
