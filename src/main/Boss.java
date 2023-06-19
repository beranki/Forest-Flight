package main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import engine.Actor;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;

public class Boss extends Actor {
	
	private int TILE_SIZE = 32;
	private int frame;
	private int movementCounterFrames;
	private boolean continueMove;
	private MainChar mainChar;
	private int startingDir;
	private Beam b_horiz;
	private int attackFrames;
	private int beamShootCounter;
	private int finalFollowFrames;
	private boolean attackable;
	private int attackableFrames;
	private Arrow arrow;
	private boolean gameOver;
	private boolean restart;
	
	ColorAdjust warningHue;
	
	public Boss() {
		setImage(new Image("file:src/game-resources/boss_sprites/idle/idle1.png"));
		startingDir = Math.random() > 0.5 ? 1 : -1;
		
		gameOver = false;
		restart = false;
		attackable = false;
		continueMove = true;
		
		frame = 0;
		
		attackFrames = 0;
		beamShootCounter = 0;
		finalFollowFrames = 0;
		attackableFrames = 0;
		movementCounterFrames = 0;
		
		warningHue = new ColorAdjust();
		warningHue.setHue(0.3);
	
	}
	
    public void addedToWorld() {
		mainChar = getWorld().getObjects(MainChar.class).get(0);

    }
	
	@Override
	public void act(long now) {
		if (frame < 60) {
			idle(60, -1);
		} else if (frame < 120) {
			moveDirection(60, 3*startingDir, 0.5);
		} else {
			attackOne(1);
		}
		
		frame++;
	}
	
	
	public boolean moveDirection(int frames, double dirX, double dirY) {
		Image img = new Image("file:src/game-resources/boss_sprites/walk/walk" + (movementCounterFrames/4%4 + 1) + ".png");
		
		if (frames == movementCounterFrames) {
			movementCounterFrames = 0;
			continueMove = false;
			return true;
		}
	
		if (dirX == 1) {
			img = mirrorImage(img);
		}
		
		movementCounterFrames++;
		move(dirX *((movementCounterFrames < frames/2 ? movementCounterFrames : (frames-movementCounterFrames))/5), 
			 dirY *((movementCounterFrames < frames/2 ? movementCounterFrames : (frames-movementCounterFrames))/5));
		
		setFitWidth(4*TILE_SIZE);
		setFitHeight(getFitWidth() * img.getHeight()/img.getWidth());
		setImage(img);
		
		return false;
		
	}
	
	public boolean idle(int frames, int direction) {
		movementCounterFrames++;
		if (movementCounterFrames == frames) {
			movementCounterFrames = 0;
			continueMove = false;
		} else {
			Image img = (new Image("file:src/game-resources/boss_sprites/idle/idle" + (movementCounterFrames/4 % 4 + 1) + ".png"));
			if (direction == 1) img = mirrorImage(img);
			setFitWidth(4*TILE_SIZE);
			setFitHeight(getFitWidth() * img.getHeight()/img.getWidth());
			setImage(img);
			continueMove = true;
		}
		
		return !continueMove;

	}
	
	private Image mirrorImage(Image img) {
		BufferedImage image = SwingFXUtils.fromFXImage(img, null);
		int width = image.getWidth();
		int height = image.getHeight();
		
		BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				flipped.setRGB((width - 1) - x, y, image.getRGB(x, y));
			}
		}
		
		return SwingFXUtils.toFXImage(flipped, null);
	}
	
	public void attackOne(int direction) {
		/*ATTACK ONE FRAME AND DX/DY CONSTANTS */
		int frames = 60;
		int MOVEMENT_DX = 4;
		int MOVEMENT_DY = 3;
		int trailFrames = 120;
		int returnToSideFrames = trailFrames + 60;
		int beamShootOne = 90;
		int numBeamShoots = 10;
		int HORIZ_BEAM_FRAME_COUNTER = 16;
		int VERT_BEAM_FRAME_COUNTER = 12;
		int beam_dx = 4;
		int beam_dy = 8;
		int temp_beam_dy = beam_dy;
		int temp_beam_dx = beam_dx;
		int finalFollowFramesMax = 100;
		int ATTACKABLE_FRAMES = 160;
		
		movementCounterFrames++;
						
		if (movementCounterFrames < trailFrames) {
			double angle = Math.atan((getY() - mainChar.getY())/(getX() - mainChar.getX()));
			double mult = (movementCounterFrames < trailFrames/2 ? movementCounterFrames : (trailFrames-movementCounterFrames)) / (trailFrames/6);
			double dx = -MOVEMENT_DX*((getX() - mainChar.getX())/Math.abs(getX() - mainChar.getX()))*Math.cos(angle)*mult;
			double dy = MOVEMENT_DY*Math.sin(angle)*(getX() >= mainChar.getX() ? -1 : 1)*mult;
			move(dx, dy);
			
			Image img = new Image("file:src/game-resources/boss_sprites/walk/walk" + ((movementCounterFrames/4)%4+1) + ".png");
			if (dx > 0) {
				img = mirrorImage(img);
			}	
			setFitWidth(4*TILE_SIZE);
			setFitHeight(getFitWidth() * img.getHeight()/img.getWidth());
			setImage(img);

		} else if (movementCounterFrames < returnToSideFrames && (getX() < getWorld().getWidth())) {
			
			moveDirection(returnToSideFrames - trailFrames, -2, 0);
		} else if (beamShootCounter < numBeamShoots) {
		
			Image img = new Image("file:src/game-resources/boss_sprites/attack/attack" + ((attackFrames/4) % 4 + 1) + ".png");
		
			if (attackFrames == HORIZ_BEAM_FRAME_COUNTER) {
				temp_beam_dx = (Math.random() < 0.5) ? 1 : -beam_dx;
				b_horiz = new Beam(12.5, temp_beam_dx); //pixel height of beam on sprite is 12.5 pixels
				b_horiz.setX(0);
				b_horiz.setY(mainChar.getY() + mainChar.getHeight()/2 + (int)(Math.random() * mainChar.getHeight()/2));
				getWorld().add(b_horiz);
				
			} 
			
			setFitWidth(4*TILE_SIZE);
			setFitHeight(getFitWidth() * img.getHeight()/img.getWidth());
			setImage(img);
			
			attackFrames++;
			
			if (b_horiz != null && ((temp_beam_dx > 0 && b_horiz.getX() + b_horiz.getFitWidth() >= getWorld().getWidth()))) {
				getWorld().remove(b_horiz);
				b_horiz = null;
				beamShootCounter++;
				attackFrames = 0;
			}
		} else if (beamShootCounter == numBeamShoots && finalFollowFrames <= finalFollowFramesMax && getX() + getFitWidth()/2 > getWorld().getWidth()/2){
			move(-Math.min(MOVEMENT_DX*MOVEMENT_DX*(finalFollowFramesMax - finalFollowFrames)/finalFollowFramesMax, getX() - getWorld().getWidth()/2), MOVEMENT_DY*-0.25);
			finalFollowFrames++;
			
			Image img = new Image("file:src/game-resources/boss_sprites/walk/walk" + ((movementCounterFrames/4)%4+1) + ".png");
			setFitWidth(4*TILE_SIZE);
			setFitHeight(getFitWidth() * img.getHeight()/img.getWidth());
			setImage(img);
			
			
			
			if (getX() + getFitWidth()/2 <= getWorld().getWidth()/2 || finalFollowFrames == finalFollowFramesMax) {
				attackable = true;
				
			}
		} else if (attackable && attackableFrames < ATTACKABLE_FRAMES) {
			setEffect(warningHue);
			attackableFrames++;
			
			Image img = new Image("file:src/game-resources/boss_sprites/idle/idle" + ((movementCounterFrames/4)%4+1) + ".png");
			setFitWidth(4*TILE_SIZE);
			setFitHeight(getFitWidth() * img.getHeight()/img.getWidth());
			setImage(img);
			
			if (!getWorld().getChildren().contains(arrow)) {
				arrow = new Arrow();
				arrow.setX(getX() + arrow.getFitWidth()/2 + getFitWidth()/4);
				arrow.setY(getY() - arrow.getFitHeight() - getFitHeight()/4);
				getWorld().add(arrow);
			}
			
			if (attackableFrames/8 % 2 == 0) {
				arrow.move(0, 1);
			} else {
				arrow.move(0, -1);
			}
			
			
			
			if (getOneIntersectingObject(MainChar.class) != null) {
				if (mainChar.isDashing()) {
					getWorld().remove(arrow);
					attackable = false;
					movementCounterFrames = 0;
					continueMove = true;
					attackFrames = 0;
					beamShootCounter = 0;
					finalFollowFrames = 0;
					attackableFrames = 0;
					gameOver = true;
				}
			} else {
				if (attackableFrames == ATTACKABLE_FRAMES) {
					restart = true;
				}
			}
		}

	}
	
	public boolean isAttackable() {
		return attackable;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public boolean restartGame() {
		return restart;
	}
}
