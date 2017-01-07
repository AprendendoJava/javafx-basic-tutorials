package org.aprendendojavafx.pixels;

class Blob {
	
	double posX, posY;
	double velX, velY;
	double raio;
	
	public Blob(double x, double y) {
		super();
		this.posX = x;
		this.posY = y;
		this.raio = 200;
		velX = Metaballs.random.nextFloat() * 10;
		velY = Metaballs.random.nextFloat() * 10;
		
	}
	
	public void update() {
		posX += velX;
		posY += velY;
		if(posX < 0 || posX > Metaballs.width) {
			velX *= -1;
		}
		
		if(posY < 0 || posY > Metaballs.height) {
			velY *= -1;
		}
	}
	
}