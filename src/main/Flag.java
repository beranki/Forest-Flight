package main;

import engine.Actor;
import javafx.scene.image.Image;

public class Flag extends Actor {
	
	int frame = 0;
	private int TILE_SIZE = 32;
	
	public Flag() {
		setImage(new Image("file:src/game-resources/world_sprites/Animated_Objects/flag/flag1.png"));
	}
	
	@Override
	public void act(long now) {
		Image img = new Image("file:src/game-resources/world_sprites/Animated_Objects/flag/flag" + ((frame/3)%4+1) + ".png");
		setImage(img);
		setFitWidth(2*TILE_SIZE);
		setFitHeight(getWidth()*img.getHeight()/img.getWidth());
		// commented out section is what i use for checking hitbox points pls dont delete - nick
		
//		setFitWidth(12);
//		setFitHeight(12);
		frame++;
	}
	
	public double getWidth() {
		return 2*TILE_SIZE;
	}
	
	public double getHeight() {
		return 2*TILE_SIZE*super.getHeight()/super.getWidth();
	}
	
}
