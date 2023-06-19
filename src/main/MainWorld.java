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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

public class MainWorld extends World {

	private MainChar mainChar;

	private int worldIndex = 1;


	private ArrayList<String> worldPaths = new ArrayList<String>();
	private int numLevels = 6;
	private final int MAIN_CHAR_WIDTH = 80;
	//private final int COIN_WIDTH = 35;
	private final int TILE_SIZE = 32;
	private final int SPIKE_WIDTH = 32;
	private boolean changingWrld = false;

	private boolean testing;
	
	private int escapeCounter;
	private boolean escapeDown;
	
	private int coins;
	private boolean proceeding;
	
	
	public MainWorld(double prefWidth, double prefHeight) {
		setMinWidth(prefWidth);
		setMinHeight(prefHeight);
		testing = false;
		escapeCounter = 0;
		escapeDown = false;
		coins =0;
		proceeding = true;

	}
	
	public void addCoin() {
		coins++;
	}
	
	public int getCoins() {
		return coins;
	}

	@Override
	public void act(long now) {

		if(isKeyPressed(KeyCode.ESCAPE)) {
			if(!escapeDown) {
				escapeCounter++;
				escapeDown = true;
			}
		} else {
			escapeDown = false;
		}
		
		if(escapeCounter >= 5 || (levelOver() && !changingWrld && worldIndex <= numLevels) && proceeding) {
			
			if (worldIndex == 6) {
				BossWorld w = new BossWorld(32*TILE_SIZE, 20*TILE_SIZE);
				BorderPane p = ((BorderPane)getParent());
				if (p != null) {
					proceeding = false;
					p.setCenter(w);
					w.start();
				}
			} else {
				changingWrld = true;
				worldIndex++;
				setNewLvl();
				changingWrld = false;
				escapeCounter = 0;
			}

		}
	}

	@Override
	public void onDimensionsInitialized() {

		ImageView bg = new ImageView(new Image("file:src/game-resources/world_sprites/Background/Background.png"));
		bg.setFitHeight(getMinHeight());
		bg.setFitWidth(getMinWidth());
		getChildren().add(bg);
		if(testing) {
			if(worldPaths.size() > 0)
				worldPaths.set(0, "src/world_files/testworld.txt");
			else
				worldPaths.add("src/world_files/testworld.txt");
			worldIndex = 0;
			setNewLvl();
			return;
		}
//		for(int i = 1; i <= numLevels; i++) {
//			worldPaths.add("src/world_files/level" + i + ".txt");
//		}
		
		setNewLvl();
	}

	public void makeLevel() {
		for (int i = 0; i < 5; i++) {
			Tile dirt = new Tile((i < 4) ? "file:src/game-resources/world_sprites/Tiles/Tile_12.png" : "file:src/game-resources/world_sprites/Tiles/Tile_24.png");

			dirt.setY(getMinHeight() - dirt.getHeight());
			dirt.setX(i*dirt.getWidth());

			add(dirt);
		}

		for(int i=25;i<32;i++) {
			Tile tempGrass = new Tile((i == 25) ? "file:src/game-resources/world_sprites/Tiles/Tile_01.png" : "file:src/game-resources/world_sprites/Tiles/Tile_02.png");

			tempGrass.setY(getMinHeight() - tempGrass.getHeight());
			tempGrass.setX(i * tempGrass.getWidth());

			add(tempGrass);
		}


		Tree tree = new Tree("file:src/game-resources/world_sprites/Objects/Willows/3.png");
		tree.setX(getMinWidth() - tree.getFitWidth());
		tree.setY(getMinHeight() - tree.getFitHeight() - TILE_SIZE);
		add(tree);

		List<Actor> chestContents = new ArrayList<Actor>();
		chestContents.add(new Coin());
		chestContents.add(new Powerup());

		Chest chest = new Chest();
		chest.setX(getMinWidth() - 4*TILE_SIZE);
		chest.setY(getMinHeight() - 3*TILE_SIZE);
		add(chest);

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				Tile pl = new Tile((i == 4) ? "file:src/game-resources/world_sprites/Tiles/Tile_14.png" : "file:src/game-resources/world_sprites/Tiles/Tile_12.png");

				pl.setY(getMinHeight() - pl.getHeight() * j);
				pl.setX(i* pl.getWidth());

				add(pl);
			}
		}

		for (int i = 0; i < 5; i++) {
			Tile topl = new Tile((i == 4) ? "file:src/game-resources/world_sprites/Tiles/Tile_03.png" : "file:src/game-resources/world_sprites/Tiles/Tile_02.png");
			topl.setX(i*topl.getWidth());
			topl.setY(getMinHeight() - topl.getHeight() * 7);
			add(topl);
		}

		Flag f = new Flag();
		f.setX(TILE_SIZE/2 + f.getWidth()/2);
		f.setY(getMinHeight() - 7*TILE_SIZE - f.getHeight()*2);
		add(f);

		Coin coin = new Coin();
		coin.setX(getMinWidth()/2);
		coin.setY(getMinHeight()/2);
		add(coin);

		mainChar = new MainChar(80); //width of actor is 80 pixels, height scales via aspect ratio
		add(mainChar);
		mainChar.setX(TILE_SIZE*2 + mainChar.getWidth()/2);
		mainChar.setY(getMinHeight() - 7*TILE_SIZE - mainChar.getHeight()/2);

	}

	/*=======================================================================================================*/

	public void setNewLvl() {
		for(int i = 0; i < getObjects(Actor.class).size(); i++) {
			remove(getObjects(Actor.class).get(i));
			i--;
		}
		
		if(worldIndex <= numLevels) {
			File f = (testing) ? new File("src/world_files/testworld.txt") : new File("src/world_files/level" + (worldIndex) + ".txt");

			Scanner in = null;
	        try {
	            in = new Scanner(f);
	            while(in.hasNextLine()) {
		        	String s = in.nextLine();
		        	String[] entries = s.split(" ");
		        	if(entries[0].equals("Tile")) {
		        		Tile tempGrass = new Tile(entries[1]);
		        		tempGrass.setX(Double.parseDouble(entries[2]));
		        		tempGrass.setY(Double.parseDouble(entries[3]));
		        		add(tempGrass);
		        	} else if (entries[0].equals("MainChar")) {
		        		mainChar = new MainChar(MAIN_CHAR_WIDTH);
		        		mainChar.setX(Double.parseDouble(entries[1]));
		        		mainChar.setY(Double.parseDouble(entries[2]));
		        		add(mainChar);
		        	} else if (entries[0].equals("Coin")) {
		        		Coin tempCoin = new Coin();
		        		tempCoin.setX(Double.parseDouble(entries[1]));
		        		tempCoin.setY(Double.parseDouble(entries[2]));
		        		add(tempCoin);
		        	} else if (entries[0].equals("Spike")) {
		        		int dir = Integer.parseInt(entries[3]);
		        		Spike tempSpk = new Spike(SPIKE_WIDTH, dir);
		        		if(dir == 1 || dir == 2 || dir == 4) {
		        			tempSpk.setX(Double.parseDouble(entries[1]));
			        		tempSpk.setY(Double.parseDouble(entries[2]));
		        		} else if (dir == 3) {
		        			tempSpk.setX(Double.parseDouble(entries[1]) + tempSpk.getWidth());
			        		tempSpk.setY(Double.parseDouble(entries[2]) - tempSpk.getHeight()/2);
		        		}
		        		add(tempSpk);
		        	} else if (entries[0].equals("Flag")) {
		        		Flag flag = new Flag();
		        		flag.setX(Double.parseDouble(entries[1]));
		        		flag.setY(Double.parseDouble(entries[2]));
		        		add(flag);
		        	} else if (entries[0].equals("Chest")) {
		        		
		        		Chest chest = new Chest();
		        		chest.setX(Double.parseDouble(entries[1]));
		        		chest.setY(Double.parseDouble(entries[2]));
		        		add(chest);
		        	} else if (entries[0].equals("Pointer")) {
		        		int num = Integer.parseInt(entries[3]);
		        		Pointer pt = new Pointer(40, num);
		        		pt.setX(Double.parseDouble(entries[1]));
		        		pt.setY(Double.parseDouble(entries[2]));
		        		add(pt);
		        	} 
		        	
//		        	else if (entries[0].equals("Text")) {
//		        		Text text = new Text();
//		        		text.setX(Double.parseDouble(entries[1]));
//		        		text.setY(Double.parseDouble(entries[2]));
//		        		add(text);
//		        	}
		        }
	        } catch (FileNotFoundException e) {
	            System.out.println(e.getMessage());
	        }
	        changingWrld = false;
	        Text score = new Text();
			score.setFont(Font.loadFont("file:src/game-resources/fonts/ARCADE_N.TTF", 15));
			score.setText("COINS: ");
			score.setFill(Color.BLACK);
			score.setX(15);
			score.setY(30);
			getChildren().add(score);
	        in.close();
		}
		
		
	}

	public boolean levelOver() {
		//return mainChar.getY() <= mainChar.getHeight()/2;
		//System.out.println("Level over");
		if(mainChar == null) {
			return false;
		} else {
			// hard coding 💀💀
			switch(worldIndex) {
				case 1:
					return mainChar.getX() >= getWidth() - mainChar.getWidth()/2;
				case 2:
					return mainChar.getX() >= getWidth() - mainChar.getWidth()/2;
				case 3:
					return mainChar.getX() >= getWidth() - mainChar.getWidth()/2;
				case 4:
					return mainChar.getX() >= getWidth() - mainChar.getWidth()/2;
				case 5:
					return mainChar.getY() <= 0;
				case 6:
					return mainChar.getX() >= getWidth() - mainChar.getWidth()/2;
				default:
					return false;
			}
			
		}
	}

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


			for (Actor a: getObjects(Actor.class)) {
				String className = a.getClass() + " ";
				className = (className.split(" ")[1]).replace("main.", "");
				fw.write(className + " " + ((className.equals("Tile")) ? ((Tile) (a)).getUrl() + " " : "") + a.getX() + " " + a.getY() + "\n");
			}

			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
