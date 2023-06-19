package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import engine.Actor;
import engine.World;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BossWorld extends World {

	private MainChar c;
	private Boss b;
	private int worldIndex = 0;
	private ArrayList<String> worldPaths = new ArrayList<String>();
	private int numLevels = 10;
	private final int MAIN_CHAR_WIDTH = 80;
	//private final int COIN_WIDTH = 35;
	private final int TILE_SIZE = 32;
	private final int SPIKE_WIDTH = 32;
	private boolean changingWrld = false;

	private boolean testing;
	private boolean dying = false;
	private int dyingFrames = 0; 

	public BossWorld(double prefWidth, double prefHeight) {
		setMinWidth(prefWidth);
		setMinHeight(prefHeight);
		testing = false;

	}

	@Override
	public void act(long now) {
		if (getChildren().contains(c) && getChildren().contains(b)) {
			
			if (c != null && b!= null && (c.getOneIntersectingObject(Beam.class) != null || c.getOneIntersectingObject(Spike.class) != null || 
				touchingBoss())) {
				
				if (!(c.isDashing() && b.isGameOver())) dying = true;
			}
			
			if (dying) {
				dyingFrames++;
			}
			
			if (dyingFrames == 15 || (b != null && b.restartGame())) {
				getChildren().clear();
				onDimensionsInitialized();
				dying = false;
				dyingFrames = 0;
			}
			
			if (b != null && b.isGameOver()) {
				
				getChildren().clear();
			}
		}
	
	}

	private boolean touchingBoss() {
		return c.getX() + c.getFitWidth()/2 > b.getX() + b.getFitWidth()/8 && c.getX() + c.getFitWidth()/2 < b.getX() + b.getFitWidth()*7/8 
			&& c.getY() + c.getFitHeight()/2 > b.getY() + b.getFitHeight()/8 && c.getY() + c.getFitHeight()/2 < b.getY() + b.getFitHeight()*7/8;
	}
	
	
	@Override
	public void onDimensionsInitialized() { 
		ImageView bg = new ImageView(new Image("file:src/game-resources/world_sprites/Background/Background.png"));
		bg.setFitHeight(getMinHeight());
		bg.setFitWidth(getMinWidth());
		getChildren().add(bg);

		Text score = new Text();
		score.setFont(Font.loadFont("file:src/game-resources/fonts/ARCADE_N.TTF", 15));
		score.setText("COINS: ");
		score.setFill(Color.BLACK);
		score.setX(15);
		score.setY(30);
		getChildren().add(score);
		
		c = new MainChar(80);
		c.setX(getWidth()/2 - c.getFitWidth()/2);
		c.setY(getHeight()/2 + c.getFitHeight()/2);
		add(c);
		
		b = new Boss();
		b.setX(getWidth()/2 - b.getFitWidth()/2);
		b.setY(b.getFitHeight()/2);
		add(b);
		
		for (int i = 0; i < 8; i++) {
			int pillarHeight = (int)(Math.random() * 4) + 5;
			makeBrick(pillarHeight, 2, TILE_SIZE + 4*TILE_SIZE*i, getHeight() - pillarHeight*TILE_SIZE);
		}
		
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j < 2; j++) {
				Spike sp = new Spike(TILE_SIZE, 1);
				sp.setX(4*TILE_SIZE*i + TILE_SIZE*j - 5*TILE_SIZE);
				sp.setY(getHeight() - sp.getFitHeight());
				add(sp);
			}
		}
		
	}
	
	
	public void makeBrick(int r, int c, double x, double y) {
		for (int row = 0; row < r; row++) {
			for (int col = 0; col < c; col++) {
				double curX = x + col * TILE_SIZE;
				double curY = y + row * TILE_SIZE;
				Tile t;
				if (row == r - 1) {
					if (col == 0 && curX > 0 && curY < getHeight() - TILE_SIZE) {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_21.png");
					} else if (col == c - 1 && curX < getWidth() - TILE_SIZE && curY < getHeight() - TILE_SIZE) {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_23.png");
					} else if (curY < getHeight() - TILE_SIZE){
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_05.png");
					} else if (curY >= getHeight() - TILE_SIZE && col == 0) {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_11.png");
					} else if (curY >= getHeight() - TILE_SIZE && col == c - 1) {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_13.png");
					} else {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_12.png");
					}
				} else if (col == 0) {
					if (row == 0 && curX != 0 && curY > 0) {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_01.png");
					} else if (curX != 0) {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_11.png");
					} else if (row == 0 && curY > 0) {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_02.png");
					} else {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_12.png");
					}

				} else if (col == c - 1) {
					if (row == 0 && curX < getWidth() - TILE_SIZE && curY > 0) {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_03.png");
					} else if (curX < getWidth() - TILE_SIZE) {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_13.png");
					} else if (row == 0 && curY > 0) {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_02.png");
					} else {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_12.png");
					}
				} else if (row == 0 && curY > 0) {
					t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_02.png");
				} else {
					t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_12.png");
				}
				t.setX(curX);
				t.setY(curY);
				add(t);
			}
		}
	}

	
	

}
