package main;

import engine.Actor;
import javafx.scene.image.Image;

public class Arrow extends Actor {
	
	private int TILE_SIZE = 32;
	
	public Arrow() {
	
	}
	
	@Override
	public void act(long now) {
		Image img = new Image("file:src/game-resources/boss_sprites/attack/attackArrow.png");
		setImage(img);
		setFitWidth(2*TILE_SIZE);
		setFitHeight(getWidth()*img.getHeight()/img.getWidth());
	}
	
	public double getWidth() {
		return 2*TILE_SIZE;
	}
	
	public double getHeight() {
		return 2*TILE_SIZE*super.getHeight()/super.getWidth();
	}
	
}
