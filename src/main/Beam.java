package main;

import engine.Actor;
import javafx.scene.image.Image;

public class Beam extends Actor {

	private int frame = 0;
	private double height;
	private double width;
	private double d;
	
	public Beam(double height, int d) {
		this.height = height;
		this.d = d;
	}
	
	@Override
	public void act(long now) {
			setImage(new Image("file:src/game-resources/boss_sprites/attack/beam" + (frame/4 % 2 + 1) + ".png"));
			setFitWidth(height/getHeight() * getWidth() + frame*Math.abs(d));
			setFitHeight(height);
			frame++; 
		
	}
}
