package main;

import engine.Actor;
import javafx.scene.image.Image;

public class Powerup extends Actor {
	
	int frame = 0;
	
	public Powerup() {
		setImage(new Image("file:src/resources/world_sprites/Animated_Objects/rune/rune1.png"));
	}
	
	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		setImage(new Image("file:src/resources/world_sprites/Animated_Objects/rune/rune" + ((frame/4)%4+1) + ".png"));
		frame++;
	}
	
}
