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
		velX = Main.random.nextFloat() * 10;
		velY = Main.random.nextFloat() * 10;
		
	}
	
	public void update() {
		posX += velX;
		posY += velY;
		if(posX < 0 || posX > Main.WIDTH) {
			velX *= -1;
		}
		
		if(posY < 0 || posY > Main.HEIGHT) {
			velY *= -1;
		}
	}
	
}