package main;

import java.awt.Color;

import engine.Actor;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Spike extends Actor {
	
	private double width;
	private int direction;
	
	
	public Spike(double width, int dir) {
		// 1 is up, 2 is down, 3 is left, 4 is right
		
		if(dir == 2) {
			setImage(new Image("file:src/game-resources/world_sprites/Objects/Spikes/downSpikes.png"));
			this.width = width;
			setFitWidth(width);
			setFitHeight(width/2);
		} else if (dir == 1){
			setImage(new Image("file:src/game-resources/world_sprites/Objects/Spikes/upSpikes.png"));
			this.width = width;
			setFitWidth(width);
			setFitHeight(width/2);
		} else if (dir == 3) {
			setImage(new Image("file:src/game-resources/world_sprites/Objects/Spikes/leftSpikes.png"));
			this.width = width/2;
			setFitWidth(width/2);
			setFitHeight(width);
		} else {
			setImage(new Image("file:src/game-resources/world_sprites/Objects/Spikes/rightSpikes.png"));
			this.width = width/2;
			setFitWidth(width/2);
			setFitHeight(width);
		}
		direction = dir;
	}
	
	@Override
	public void act(long now) {
		
	
	}

	public int getDir() {
		return direction;
	}
}
