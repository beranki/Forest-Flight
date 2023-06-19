package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	//CONSTANTS 
	public final int TILE_SIZE = 32;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Forest Flight");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 32*TILE_SIZE, 20*TILE_SIZE);
		stage.setScene(scene);
		stage.setResizable(false);
		
		BackgroundImage bg = new BackgroundImage(new Image("file:src/game-resources/world_sprites/loading_screen.png", 32*TILE_SIZE, 20*TILE_SIZE, false, true), 
												 BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		
		root.setBackground(new Background(bg));
		
		//HANDLING THE BUTTON TO BEGIN PLAYING
		ImageView button = new ImageView(new Image("file:src/game-resources/world_sprites/Objects/stone_button.png", 99.0, 41.0, false, true));
		button.setX(scene.getWidth()/2 - 99.0/2);
		button.setY(scene.getHeight() - 41.0);
		root.getChildren().add(button);
		
		Text t = new Text(" START ");
		Font f = Font.loadFont("file:src/game-resources/fonts/ARCADE_N.TTF", 10);
		t.setFont(f);
		t.setX(button.getX() + 16.5);
		t.setY(button.getY() + 23.5);
		root.getChildren().add(t);
		
		//SETTING ONCLICK EVENTS TO TRIGGER THE MAIN WORLD
		button.setOnMouseClicked(event -> {
			root.getChildren().clear();
			MainWorld w = new MainWorld(32*TILE_SIZE, 20*TILE_SIZE);
			//BossWorld w = new BossWorld(32*TILE_SIZE, 20*TILE_SIZE);
			//LevelBuilder w = new LevelBuilder(32*TILE_SIZE, 20*TILE_SIZE);
			w.start();
			root.setCenter(w);
		});
		t.setOnMouseClicked(event -> {
			root.getChildren().clear();
			MainWorld w = new MainWorld(32*TILE_SIZE, 20*TILE_SIZE);
			//BossWorld w = new BossWorld(32*TILE_SIZE, 20*TILE_SIZE);
			//LevelBuilder w = new LevelBuilder(32*TILE_SIZE, 20*TILE_SIZE);
			w.start();
			root.setCenter(w);
		});
		
		ImageView controls = new ImageView(new Image("file:src/game-resources/world_sprites/settings.png"));
		controls.setFitWidth(3*TILE_SIZE);
		controls.setFitHeight(3*TILE_SIZE);
		controls.setX(scene.getWidth() - controls.getFitWidth());
		controls.setY(0);
		root.getChildren().add(controls);
		
		controls.setOnMouseClicked(e -> {
			final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(stage);
            VBox dialogVbox = new VBox(20);
            
            Font modalFont = Font.loadFont("file:src/game-resources/fonts/ARCADE_N.TTF", 20.0); 
            Font paraFont = Font.loadFont("file:src/game-resources/fonts/ARCADE_N.TTF", 10.0); 

            Text title = new Text("FOREST FLIGHT CONTROLS");
            
            Text controlsText = new Text(
            	    "GENERAL CONTROLS\n\n"	
            	  + "\tD - move right\n"
            	  + "\tA - move left\n"
            	  + "\tJ - jump\n"
            	  + "\tD/A/W + K - right/left/up dash\n"
            	  + "\n\n"
            	  + "TIPS\n"
            	  + "\tJ facing a wall - wall jump\n"
            	  + "\tdash to open chests and fight!");
            
            title.setFont(modalFont);
            controlsText.setFont(paraFont);
            
            dialogVbox.getChildren().addAll(title, controlsText);
            dialogVbox.setAlignment(Pos.CENTER);
            
            Scene dialogScene = new Scene(dialogVbox, 500, 500);
            dialog.setScene(dialogScene);
           
            dialogScene.setFill(Color.web("#81c483"));
            
            dialog.show();	
            
           
		});
		
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
