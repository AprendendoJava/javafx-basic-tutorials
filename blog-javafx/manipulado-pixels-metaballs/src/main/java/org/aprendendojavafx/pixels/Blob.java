package org.aprendendojavafx.pixels;

import java.util.Random;

class Blob {
	
	Random random = new Random();
	
	double posX, posY;
	double velX, velY;
	double raio;
	
	public Blob(double x, double y) {
		super();
		this.posX = x;
		this.posY = y;
		this.raio = 200;
		velX = random.nextFloat() * 10;
		velY = random.nextFloat() * 10;
		
	}
	
	public void update() {
		posX += velX;
		posY += velY;
		if(posX < 0 || posX > DrawingApp.height) {
			velX *= -1;
		}
		
		if(posY < 0 || posY > DrawingApp.height) {
			velY *= -1;
		}
	}
	
}