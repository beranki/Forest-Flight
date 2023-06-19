package main;

import engine.Actor;
import javafx.scene.image.Image;

public class GeneralHitbox extends Actor {
	
	double width;
	double height;
	
	public GeneralHitbox(double w, double h, boolean transparent) {
		width = w;
		height = h;
		setImage(new Image("file:src/game-resources/character_sprites/other/HitboxOutline.png"));
		setFitWidth(width);
		setFitHeight(height);
		if(transparent)
			setOpacity(0.0);
		
	}
	
	@Override
	public void act(long now) {
		
	}
	
}
