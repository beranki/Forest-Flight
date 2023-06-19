package main;

import engine.Actor;
import javafx.scene.image.Image;

public class Pointer extends Actor {

	public Pointer(int height, int num) {
		setImage(new Image("file:src/game-resources/world_sprites/Objects/Pointers/" + num + ".png"));
		double h = getHeight();
		double ratio = h/height;
		setFitHeight(height);
		setFitWidth(getWidth()/ratio);
	}
	
	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}
}
