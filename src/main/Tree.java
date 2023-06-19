package main;

import engine.Actor;
import javafx.scene.image.Image;

public class Tree extends Actor {
	String url;
	public Tree(String url) {
		this.url = url;
		setImage(new Image(url));
		setFitWidth(1.5*getImage().getWidth());
		setFitHeight(1.5*getImage().getHeight());
	}
	
	public void act(long now) {

	}
}
