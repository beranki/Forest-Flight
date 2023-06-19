package main;

import java.util.ArrayList;
import java.util.List;

import engine.Actor;
import javafx.scene.image.Image;

public class Chest extends Actor {

	private int frame = 0;
	private boolean isOpen = false;
	private ArrayList<Coin> contents;
	private int floatContentFrames;
	private Actor selectedObj;
	
	public Chest() {
		setImage(new Image("file:src/game-resources/world_sprites/Animated_Objects/chest/chest1.png"));
		contents = new ArrayList<Coin>();
		contents.add(0, new Coin());
		
	}
	
	
	
	@Override
	public void act(long now) {
		if (isOpen) {
			Image m  = new Image("file:src/game-resources/world_sprites/Animated_Objects/chest/chest" + Math.min(frame/4, 4) + ".png");
			setImage(m);
			frame++;
			
			if (contents.size() > 0) {
					selectedObj = contents.get(0);
					if (!getWorld().getChildren().contains(selectedObj)) {
						getWorld().add(selectedObj);
						selectedObj.setX(this.getX() + getWidth()/2);
						selectedObj.setY(this.getY());
					}
					
//					if (getOneIntersectingObject(MainChar.class) == null && contents.contains(selectedObj)) {
//						selectedObj.move(0, -1);
						
//						floatContentFrames++;
//						if (floatContentFrames >= 20 || getOneIntersectingObject(MainChar.class) != null) {
//							
//							contents.remove(selectedObj);
//							selectedObj = null;
//							floatContentFrames = 0;
//							
//						}
//					}
			}
		
		}
		
		
		setFitWidth(64);
		setFitHeight(64);
	}
	
	public void open() {
		isOpen = true;
	}
}
