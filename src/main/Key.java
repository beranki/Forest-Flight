package main;

import engine.Actor;
import javafx.scene.image.Image;

public class Key extends Actor {
	
	int frame = 0;
	
	public Key() {
		setImage(new Image("file:src/resources/world_sprites/4 Animated objects/key/key1.png"));
	}
	
	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		setImage(new Image("file:src/resources/world_sprites/4 Animated objects/key/key" + ((frame/4)%4+1) + ".png"));
		frame++;
	}
	
}
