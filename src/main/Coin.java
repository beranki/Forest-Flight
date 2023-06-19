package main;

import engine.Actor;
import javafx.scene.image.Image;

public class Coin extends Actor {

	private int frame = 0;
	
	public Coin() {
		setImage(new Image("file:src/game-resources/world_sprites/Animated_Objects/coin/coin1.png"));
	}
	
	@Override
	public void act(long now) {
		setImage(new Image("file:src/game-resources/world_sprites/Animated_Objects/coin/coin" + ((frame/4)%4+1) + ".png"));
		setFitWidth(16);
		setFitHeight(16);
		frame++;
	}
}
