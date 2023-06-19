package main;

import engine.Actor;
import javafx.scene.image.Image;

public class Tile extends Actor {
	
	private String url;

	public Tile(String url) {
		this.url = url;
		setImage(new Image(url));
	}
	
	@Override
	public void act(long now) {
		
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setImage(String url) {
		this.url = url;
		setImage(new Image(url));
	}
}
