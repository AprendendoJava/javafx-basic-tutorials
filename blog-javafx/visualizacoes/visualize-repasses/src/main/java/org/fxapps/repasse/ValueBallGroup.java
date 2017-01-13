package org.fxapps.repasse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.fxapps.drawingfx.DrawingApp;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ValueBallGroup {
	
	static Color[][] COLORS = {
		{ Color.DARKBLUE, Color.WHITESMOKE},
		{ Color.LIGHTGREEN, Color.DARKGREEN},
		{ Color.GOLD, Color.BLACK},
		{ Color.LIGHTPINK, Color.DARKRED},
		{ Color.LIGHTCYAN, Color.DARKGREEN },
		{Color.BLUEVIOLET, Color.DARKBLUE },
		{ Color.LIGHTSALMON, Color.DARKRED},
		{ Color.LIGHTSKYBLUE, Color.DARKBLUE},
		{ Color.LIGHTYELLOW, Color.DARKGREY},
		{ Color.LIGHTPINK, Color.DARKMAGENTA},
		{ Color.ANTIQUEWHITE, Color.DARKSLATEGRAY},
		{ Color.STEELBLUE, Color.CORAL},
		{ Color.GHOSTWHITE, Color.BLACK},
		{ Color.MAROON, Color.SILVER},
		{ Color.SILVER, Color.DARKSLATEGRAY},
		{ Color.ANTIQUEWHITE, Color.DARKOLIVEGREEN},
		{ Color.DARKBLUE, Color.BURLYWOOD},
		{ Color.LIGHTGREY, Color.DARKGREY},
		{ Color.DARKGREEN, Color.LIGHTGREEN},
		{ Color.DARKRED, Color.LIGHTPINK},
		{ Color.FLORALWHITE, Color.DARKBLUE},
		{ Color.DARKGREEN, Color.FLORALWHITE},
		{ Color.LIGHTBLUE, Color.CORNFLOWERBLUE},
		{ Color.BLUEVIOLET, Color.LIGHTSTEELBLUE},
		{ Color.ORANGERED, Color.LIGHTYELLOW},
		{ Color.DODGERBLUE, Color.LIGHTGREEN},
		{ Color.LIGHTCYAN, Color.DARKGOLDENROD},
		{ Color.DARKRED, Color.LIGHTCORAL}
	};
	
	static Map<String, Color[]> INDEX_MAPS = new HashMap<>();
	static AtomicInteger colorIndexCount = new AtomicInteger(0);
	
	List<ValueBall> valueBalls;
	
	private String id;

	private int x, y, width, height;

	private GraphicsContext gc;

	private Color backgroundColor;

	private Double totalValue;

	private Function<Double, String> convertValues;

	private Color textColor;

	private Map<Object, Double> data;

	private DrawingApp app;
	
	public ValueBallGroup(String id, Color backgroundColor, Color textColor, int x, int y, int width, int height, Map<Object, Double> data, Function<Double, String> convertValues, DrawingApp app) {
		this.id = id;
		this.backgroundColor = backgroundColor;
		this.textColor = textColor;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.data = data;
		this.convertValues = convertValues;
		this.app = app;
		gc = app.ctx;
		this.valueBalls = new ArrayList<>();
		generateValueBalls();
	}

	private void generateValueBalls() {
		Point2D startBoundary = new Point2D(x, y);
		Point2D endBoundary = new Point2D(x + width, y + height);
		totalValue = data.values().stream().mapToDouble(v -> v).sum();
		data.entrySet().forEach(e -> {
			Color[] colors = colorsForData(e.getKey().toString());
			Double s = app.map(e.getValue(), 0, totalValue, 50, 300);
			int r = s.intValue();
			int valueBallX = x + (int) ((endBoundary.getX() - startBoundary.getX()) / 2) - r / 2;
			int valueBallY = y + (int) ((endBoundary.getY() - startBoundary.getY()) / 2) ;
			ValueBall vb = new ValueBall(valueBallX, valueBallY,  r, colors[0], colors[1], e.getKey().toString(), convertValues.apply(e.getValue()), app);
			vb.setStartBoundary(startBoundary);
			vb.setEndBoundary(endBoundary);
			valueBalls.add(vb);
		});
		
		Collections.sort(valueBalls, (v1, v2) -> {
			return v2.getNewR() - v1.getNewR();
		});
		int nextY = 0;
		for (ValueBall vb : valueBalls) {
			nextY = nextY + vb.getNewR() / 2 + 40;
			vb.setY(nextY);
		}
	}
	
	public void update() {
		for (ValueBall valueBall : valueBalls) {
			valueBall.update();
		}
	}
	
	public void show() {
		gc.setFill(backgroundColor);
		gc.fillRect(x, y, width, height);
		for (ValueBall valueBall : valueBalls) {
			valueBall.show();
		}
		gc.setFill(textColor);
		gc.setStroke(textColor);
		gc.setFont(Font.font(null,  FontWeight.SEMI_BOLD, FontPosture.ITALIC, 15));
		gc.setTextAlign(TextAlignment.CENTER);
		gc.fillText("Total " + convertValues.apply(totalValue), x + width / 2, y + height - 20);
		gc.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 60));
		gc.strokeText(id, x + width / 2, 70);
	}
	

	public static Color[] colorsForData(String dataKey) {
		Color[] colors = INDEX_MAPS.computeIfAbsent(dataKey, c -> { 
			int i = colorIndexCount.getAndIncrement();
			if (i >= COLORS.length){
				i = 0;
				colorIndexCount.set(0);
			}
			return COLORS[i]; 
		});
		return colors;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
