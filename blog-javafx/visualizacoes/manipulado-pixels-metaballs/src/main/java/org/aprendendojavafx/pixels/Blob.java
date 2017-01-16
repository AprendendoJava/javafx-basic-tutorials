package org.aprendendojavafx.pixels;

import org.fxapps.drawingfx.DrawingApp;

class Blob {
	
	double posX, posY;
	double velX, velY;
	double raio;
	
	private DrawingApp app;
	
	public Blob(double x, double y, DrawingApp app) {
		super();
		this.posX = x;
		this.posY = y;
		this.app = app;
		this.raio = 200;
		velX = app.random.nextFloat() * 10;
		velY = app.random.nextFloat() * 10;
		
	}
	
	public void update() {
		posX += velX;
		posY += velY;
		if(posX < 0 || posX > app.width) {
			velX *= -1;
		}
		
		if(posY < 0 || posY > app.height) {
			velY *= -1;
		}
	}
	
}