package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import engine.Actor;
import engine.World;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LevelBuilder extends World {

	private final int TILE_SIZE = 32;

	public LevelBuilder(double width, double height) {
		setWidth(width);
		setHeight(height);
	}

	@Override
	public void act(long now) {

	}

	@Override
	public void onDimensionsInitialized() {
		ImageView bg = new ImageView(new Image("file:src/game-resources/world_sprites/Background/Background.png"));
		bg.setFitHeight(getHeight());
		bg.setFitWidth(getWidth());
		getChildren().add(bg);

		Text score = new Text();
		score.setFont(Font.loadFont("file:src/game-resources/fonts/ARCADE_N.TTF", 15));
		score.setText("COINS: ");
		score.setFill(Color.BLACK);
		score.setX(15);
		score.setY(30);
		getChildren().add(score);

//		level1();
		level2();

		//writeFile("src/world_files/level3.txt");
	}

	public void level1() {
		// level 1 testing
		makeBrick(7, 3, TILE_SIZE * 3, getHeight() - 7 * TILE_SIZE);

		Flag f = new Flag();
		f.setX(TILE_SIZE * 4.5 - f.getWidth() / 2);
		f.setY(getHeight() - 7 * TILE_SIZE - f.getHeight());
		add(f);

		MainChar mainChar = new MainChar(80); // width of actor is 80 pixels, height scales via aspect ratio
		add(mainChar);
		mainChar.setX(TILE_SIZE * 4.5 - mainChar.getWidth() / 2);
		mainChar.setY(TILE_SIZE * 5);

		// Bottom for tower 1
		makeBrick(6, 2, TILE_SIZE * 9, getHeight() - 6 * TILE_SIZE);

		Spike s1 = new Spike(TILE_SIZE, 1);
		s1.setX(TILE_SIZE * 9);
		s1.setY(getHeight() - 6 * TILE_SIZE - s1.getHeight());
		add(s1);
		Spike s2 = new Spike(TILE_SIZE, 1);
		s2.setX(TILE_SIZE * 9 + s2.getWidth());
		s2.setY(getHeight() - 6 * TILE_SIZE - s2.getHeight());
		add(s2);

		// Upper for tower 1
		makeBrick(8, 2, TILE_SIZE * 9, 0);

		Spike s3 = new Spike(TILE_SIZE, 2);
		s3.setX(TILE_SIZE * 9);
		s3.setY(8 * TILE_SIZE);
		add(s3);
		Spike s4 = new Spike(TILE_SIZE, 2);
		s4.setX(TILE_SIZE * 9 + s4.getWidth());
		s4.setY(8 * TILE_SIZE);
		add(s4);

		// chest plat
		makeBrick(1, 2, TILE_SIZE * 11, TILE_SIZE * 7);
		Chest c1 = new Chest();
		c1.setX(TILE_SIZE * 12 - c1.getWidth() / 1.5);
		c1.setY(TILE_SIZE * 7 - c1.getHeight() * 2);
		add(c1);

		// Landing Platform 1
		makeBrick(8, 4, TILE_SIZE * 12, getHeight() - 8 * TILE_SIZE);
		Coin coin = new Coin();
		coin.setX(TILE_SIZE * 14 - coin.getWidth() / 2);
		coin.setY(getHeight() - 8 * TILE_SIZE - coin.getHeight() * 2);
		add(coin);

		// Bottom for tower 2
		makeBrick(4, 2, TILE_SIZE * 21, getHeight() - 4 * TILE_SIZE);

		Spike s5 = new Spike(TILE_SIZE, 1);
		s5.setX(TILE_SIZE * 21);
		s5.setY(getHeight() - 4 * TILE_SIZE - s5.getHeight());
		add(s5);
		Spike s6 = new Spike(TILE_SIZE, 1);
		s6.setX(TILE_SIZE * 21 + s6.getWidth());
		s6.setY(getHeight() - 4 * TILE_SIZE - s6.getHeight());
		add(s6);

		// Upper for tower 2
		makeBrick(10, 2, TILE_SIZE * 21, 0);

		Spike s7 = new Spike(TILE_SIZE, 2);
		s7.setX(TILE_SIZE * 21);
		s7.setY(10 * TILE_SIZE);
		add(s7);
		Spike s8 = new Spike(TILE_SIZE, 2);
		s8.setX(TILE_SIZE * 21 + s8.getWidth());
		s8.setY(10 * TILE_SIZE);
		add(s8);

		// Landing Platform 1
		makeBrick(6, 3, TILE_SIZE * 25, getHeight() - 6 * TILE_SIZE);
	}
	
	public void level2() {
		makeBrick(20, 1, 0, 0);
		
	}

	// makes a brick with rows "r" and cols "c", with the top left corner being (x,
	// y)
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
	
	/*
	 * else if (row == r - 1) {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_05.png");
					} else if (col == 0) {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_11.png");
					} else if (col == c - 1) {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_13.png");
					} else {
						t = new Tile("file:src/game-resources/world_sprites/Tiles/Tile_12.png");
					}
	 */

	// save function
	public void writeFile(String file) {
		try {
			File f = new File(file);
			FileWriter fw = new FileWriter(f);

			if (f.createNewFile()) {
				System.out.println("File created: " + f.getName());
			} else {
				System.out.println("File already exists.");
			}

			for (Actor a : getObjects(Actor.class)) {
				String className = a.getClass() + " ";
				className = (className.split(" ")[1]).replace("main.", "");
				fw.write(className + " " + ((className.equals("Tile")) ? ((Tile) (a)).getUrl() + " " : "") + a.getX()
						+ " " + a.getY() + "\n");
			}

			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
