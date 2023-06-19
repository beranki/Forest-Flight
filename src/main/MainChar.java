package main;

import java.awt.image.BufferedImage;

import java.util.List;


import engine.Actor;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;


public class MainChar extends Actor {
	private double ORIG_X;
	private double ORIG_Y;
	private int moving; // -1, 1, -2, 2 -> left right down up
	private double pastX;
	//private double pastY;
	private int idleFrameC, movingFrameC, attackFrameC;
	private boolean attack;
	private boolean dash;
	private int mostRecentDir;
	private double ACTOR_WIDTH;
	private double ACTOR_HEIGHT;
	private int xdash_velocity;
	private int ydash_velocity;
	private int DASH_ACCELERATION;
	private int WALK_SPEED;

	private boolean jump;
	private boolean onLastJumpPress;
	
	private boolean wallJump;
	private int wallJumpCounter;
	private int wallJumpDir; // -1 means started on left wall, 1 means right wall

	private double JUMP_VELOCITY;
	private double GRAVITY_CONSTANT;
	private double dy;

	private boolean onTile;
	private final int TILE_SIZE = 32;

	private boolean dashReady;
	private int lives;
	

	private ColorAdjust regular_coloreffect;
	private ColorAdjust cooldown_coloreffect;
	private ColorAdjust wallJump_coloreffect;

	private int coin_off_center;
	private boolean dead;
	private int deathFrame;
	private boolean first = true;

	private PixelReader reader;
	HitboxMarker marker;
	
	private int diagonalDir; // 1 is top right, -1 is top left, 0 is none
	
	GeneralHitbox topHitbox;
	GeneralHitbox midleftHitbox;
	GeneralHitbox midrightHitbox;
	GeneralHitbox bottomHitbox;
	
	
	Flag f;
	Flag f2;

	public MainChar(double width) {
		setImage(mirrorImage(new Image("file:src/game-resources/character_sprites/idle/idle1.png")));
		ACTOR_WIDTH = width;
		ACTOR_HEIGHT = width/getImage().getWidth() * getImage().getHeight();

		WALK_SPEED = 10;

		xdash_velocity = 30;
		ydash_velocity = 24;
		DASH_ACCELERATION = -3;

		JUMP_VELOCITY = 12;
		GRAVITY_CONSTANT = -0.98;
		dy = 0;

		moving = 0;
		mostRecentDir = 0;
		pastX = getX();
		idleFrameC = 1;
		movingFrameC = 1;
		attackFrameC = 1;

		jump = false;
		attack = false;
		dash = false;
		onTile = false;

		dashReady = false;

		regular_coloreffect = new ColorAdjust();
		regular_coloreffect.setHue(0);
		cooldown_coloreffect = new ColorAdjust();
		cooldown_coloreffect.setHue(-0.15);
		wallJump_coloreffect = new ColorAdjust();
		wallJump_coloreffect.setHue(0.2);

		coin_off_center = 100;


		deathFrame = 1;
		dead = false;
		
		wallJump = false;
		wallJumpCounter = 0;
		onLastJumpPress = false;
		
		wallJumpDir = 0;
	}

	@Override
	public void act(long now) {
		if(first) {
			ORIG_X = getX();
			ORIG_Y = getY();
			first = false;
			
			marker = new HitboxMarker(true);
			getWorld().add(marker);
			
			topHitbox = new GeneralHitbox(ACTOR_WIDTH/3.1, ACTOR_HEIGHT/2.3, true);
			midleftHitbox = new GeneralHitbox(ACTOR_WIDTH/2.1, ACTOR_HEIGHT/3.6, true);
			midrightHitbox = new GeneralHitbox(ACTOR_WIDTH/7, ACTOR_HEIGHT/5.2, true);
			bottomHitbox = new GeneralHitbox(ACTOR_WIDTH/2, ACTOR_HEIGHT/4.9, true);
			getWorld().add(topHitbox);
			getWorld().add(midleftHitbox);
			getWorld().add(midrightHitbox);
			getWorld().add(bottomHitbox);
			
			if (getWorld().getClass().equals(MainWorld.class)) {
				if (((MainWorld) getWorld()).getCoins() > 0) {
					
					for(int i=0;i<((MainWorld) getWorld()).getCoins();i++) {
						Coin c = new Coin();
						
						c.setX(coin_off_center + c.getFitWidth()/2);
						c.setY(15);
						getWorld().add(c);
						coin_off_center += 16;
						
					}
					
				}
			}
		}
		if(!dead) {
			
			Image img = null;
			
			if(wallJump) {
				wallJumpCounter++;
				move(wallJumpDir * -12, 0);
				
				if(wallJumpDir == 1) {
					
					if(getTileInPlayerXPath(getX()+4) != null){
						wallJumpCounter = 100;
					}
				} else {
					if(getTileInPlayerXPath(getX() + 72) != null){
						wallJumpCounter = 100;
					}
				}
				
				if(wallJumpCounter > 10) {
					wallJumpCounter = 0;
					wallJump = false;
					wallJumpDir = 0;
				}
				
			}
			
			if (attack) {
				attackFrameC++;
				if (((attackFrameC/4) % 6 + 1) >= 6) {
					attackFrameC = 1;
					attack = false;
				}

				if (mostRecentDir == 1) {
					img = mirrorImage(new Image("file:src/game-resources/character_sprites/attack/attack" + ((attackFrameC/4) % 6 + 1) + ".png"));

				} if (mostRecentDir == -1) {
					img = new Image("file:src/game-resources/character_sprites/attack/attack" + ((attackFrameC/4) % 6 + 1) + ".png");
				}

				
			} else if (dash) {
				List<Chest> chests = getWorld().getObjectsAt(getX() + 60, getY() + 60, Chest.class);
				if (chests.size() > 0) {
					
					chests.get(0).open();
				}
				if(moving == 2) {
					Image updash_image = new Image("file:src/game-resources/character_sprites/dash/spearUp.png");
					img = (mostRecentDir == 1) ? mirrorImage(updash_image) : updash_image;
					if(getTileInPlayerYPath(getY() + 12 - ydash_velocity) != null)
						ydash_velocity = 0;
					
					move(0, -ydash_velocity);
					ydash_velocity += DASH_ACCELERATION;
					
					
				} else {
					
					Tile tileInPlayerPath;
					Image dash_image = new Image("file:src/game-resources/character_sprites/attack/attack5.png");
					Image idle_image = new Image("file:src/game-resources/character_sprites/idle/idle" + ((idleFrameC / 4) % 4 + 1) + ".png");

					if (mostRecentDir == 1) {
					    tileInPlayerPath = getTileInPlayerXPath(getX() + ACTOR_WIDTH + TILE_SIZE/2);
					    if (tileInPlayerPath != null && getX() + ACTOR_WIDTH + xdash_velocity >= tileInPlayerPath.getX()) {
					        if(xdash_velocity != 30)move(tileInPlayerPath.getX() - getX() - ACTOR_WIDTH, 0);
					        xdash_velocity = 0;
					        img = mirrorImage(idle_image);
					        pastX = getX();
					        
					    } else {
					        move(mostRecentDir * xdash_velocity, 0);
					        xdash_velocity += DASH_ACCELERATION - diagonalDir;
					        img = mirrorImage(dash_image);
					        
					    }
					    if(diagonalDir==1) {
					    	if(getTileInPlayerYPath(getY() + 12 - ydash_velocity) != null)
								ydash_velocity = 0;
					    	move(0, -ydash_velocity);
							ydash_velocity += DASH_ACCELERATION - diagonalDir;
							img = mirrorImage(new Image("file:src/game-resources/character_sprites/dash/upDiagDash.png"));
					    }

					} else {
					    tileInPlayerPath = getTileInPlayerXPath(getX() - TILE_SIZE/2);
					    if (tileInPlayerPath != null && getX() - xdash_velocity <= tileInPlayerPath.getX() + TILE_SIZE) {
					    	if(xdash_velocity != 30)move((tileInPlayerPath.getX() + TILE_SIZE) - getX(), 0);
					        xdash_velocity = 0;
					        img = idle_image;
					        pastX = getX();
					    } else {
					        move(mostRecentDir * xdash_velocity, 0);
					        xdash_velocity += DASH_ACCELERATION + diagonalDir;
					        img = dash_image;
					    }
					    if(diagonalDir==-1) {
					    	if(getTileInPlayerYPath(getY() + 12 - ydash_velocity) != null)
								ydash_velocity = 0;
					    	move(0, -ydash_velocity);
							ydash_velocity += DASH_ACCELERATION + diagonalDir;
							
							img = new Image("file:src/game-resources/character_sprites/dash/upDiagDash.png");
					    }
					}
				}
				if (xdash_velocity <= 6 || ydash_velocity <= 0) {
					xdash_velocity = 30;
					ydash_velocity = 24;
					DASH_ACCELERATION = -3;
					dash = false;
					diagonalDir = 0;
				}

			} else {
				if (moving == 0 || moving == 2 || moving == -2) {
					img = (new Image("file:src/game-resources/character_sprites/idle/idle" + ((idleFrameC/4) % 4 + 1) + ".png"));

					idleFrameC++;
					movingFrameC = 1;
					attackFrameC = 1;

					if (mostRecentDir == 1) {
						img = mirrorImage(new Image("file:src/game-resources/character_sprites/idle/idle" + ((idleFrameC/4) % 4 + 1) + ".png"));
					} else if (mostRecentDir == -1) {
						img = (new Image("file:src/game-resources/character_sprites/idle/idle" + ((idleFrameC/4) % 4 + 1) + ".png"));
					}
				} else {
					movingFrameC++;
					idleFrameC = 1;
					attackFrameC = 1;

					if (moving == 1) {
						
						img = mirrorImage(new Image("file:src/game-resources/character_sprites/walk/walk" + ((movingFrameC/4) % 6 + 1) + ".png"));
					} else if (moving == -1){
						img = (new Image("file:src/game-resources/character_sprites/walk/walk" + ((movingFrameC/4) % 6 + 1) + ".png"));
					}
				}

				if (getX() < pastX) {
					moving = -1;
					mostRecentDir = -1;
				} else if (getX() > pastX) {
					moving = 1;
					mostRecentDir = 1;
				} else {
					moving = 0;
				}

				pastX = getX();
				diagonalDir = 0;
				
				if (getWorld().isKeyPressed(KeyCode.A) && getWorld().isKeyPressed(KeyCode.D)) {
					move(0,0);
					moving = 0;

				} else if(getWorld().isKeyPressed(KeyCode.A)) {

					
					if(!wallJump) {
						// (scaling is to account for player image being larger than it looks)
						if(getTileInPlayerXPath(getX() - WALK_SPEED + 12) == null) {
							move(-WALK_SPEED, 0);
							moving = -1;
						} else {
							
							if(getTileInPlayerXPath(getX() - WALK_SPEED + 12).getX() - getX() + 22 < 0) {
								move(getTileInPlayerXPath(getX() - WALK_SPEED + 12).getX() - getX() + 22, 0);
							}
						}
					}
					
					if(getWorld().isKeyPressed(KeyCode.W))
						diagonalDir = -1;
					
					mostRecentDir = -1;
				} else if(getWorld().isKeyPressed(KeyCode.D)) {
					
					if(!wallJump) {
						if(getTileInPlayerXPath(getX()+ 6 + img.getWidth() + WALK_SPEED) == null) {
							
							move(WALK_SPEED, 0);
							moving = 1;
						} else {
							
							if(getTileInPlayerXPath(getX()+ 6 + img.getWidth() + WALK_SPEED).getX() - getX() - ACTOR_WIDTH +8 > 0) {
								move(getTileInPlayerXPath(getX()+ 6 + img.getWidth() + WALK_SPEED).getX() - getX() - ACTOR_WIDTH+8, 0);		
							} 
						}
					}
					
					if(getWorld().isKeyPressed(KeyCode.W)) {
						
						diagonalDir = 1;
					} 
						

					mostRecentDir = 1;
				} else if(getWorld().isKeyPressed(KeyCode.W)) {
					moving = 2;
				} else if(getWorld().isKeyPressed(KeyCode.S)) {
					moving = -2;
				}
				
				if(getWorld().isKeyPressed(KeyCode.J)) {
					if(!jump && onTile) {
						jump = true;
						dy -= JUMP_VELOCITY;
						onLastJumpPress = true;
					} else {
						if(getTileInPlayerXPath(getX()+ 6 + img.getWidth() + WALK_SPEED) != null) {
							if(getTileInPlayerXPath(getX()+ 6 + img.getWidth() + WALK_SPEED).getX() - getX() - ACTOR_WIDTH +8 <= 0) {
								if(!onLastJumpPress && !wallJump) {
									wallJump = true;
									wallJumpDir = mostRecentDir;
									
									dy = -JUMP_VELOCITY;
									
									jump = true;
									
									
								}
								
								
							} 
						} else if(getTileInPlayerXPath(getX() - WALK_SPEED + 12) != null) {
							if(getTileInPlayerXPath(getX() - WALK_SPEED + 12).getX() - getX() + 22 >= 0) {
								if(!onLastJumpPress && !wallJump) {
									
									wallJump = true;
									wallJumpDir = mostRecentDir;
									
									dy = -JUMP_VELOCITY;
									
									jump = true;
									
									
								}
								
								
							} 
						} 
						
					}
					
				} else {
					onLastJumpPress = false;
				}


				if (getWorld().isKeyPressed(KeyCode.K) && !dash && dashReady) {
					if(wallJump) {
						wallJump = false;
						wallJumpCounter = 0;
					}
						
					dash = true;
					dashReady = false;
				}

				getWorld().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				    @Override
				    public void handle(MouseEvent mouseEvent) {
				    	if (!attack && attackFrameC == 1) attack = true;
				    }
				});

			}
			// gravity
		
			if(img != null) {
		
				
				marker.setX(getX()+34);
				marker.setY(getY()+6);
				
				
		
				// check for head collision
				if(dy < 0 && getTileInPlayerYPath(getY() + 12 + dy) != null) {
					dy = 0;
				}
					
				// check for fall death
				if(getY() >= getWorld().getHeight() + TILE_SIZE * 4) {
					dead = true;
					
				}
				
				if(dash) {
					dy = 0;
				} else {
					
					
					dy -= GRAVITY_CONSTANT;

					double dist = getWorld().getHeight();
					
					
					
					double leftFootX = getLeftFootX(img, mostRecentDir);
					double rightFootX = getRightFootX(img, mostRecentDir);
					
					List<Tile> t = getWorld().getObjectsAt(getX()+ leftFootX, getY() + getHeight() + dy, Tile.class);
					if(t.size()>0) {
						Tile t0 = t.get(0);
						dist = t0.getY() - (getY() + getHeight());
						onTile = true;
					} else {
						onTile = false;
					}
					if(onTile == false) {
						List<Tile> t2 = getWorld().getObjectsAt(getX()+ rightFootX, getY() + getHeight() + dy, Tile.class);
						if(t2.size()>0) {
							Tile t0 = t2.get(0);
							dist = t0.getY() - (getY() + getHeight());
							onTile = true;
							
						} else {
							onTile = false;
						}
					}
					
				
				
//					f.setX(getX() + leftFootX);
//					f.setY(getHeight() + getY());
//					
//					f2.setX(getX() + rightFootX);
//					f2.setY(getHeight() + getY());
					
					move(0, Math.min(dist, dy));

					if (onTile) {
						dashReady =true;
						jump = false;
						dy = 0;
						wallJump = false;
					}

					if(!dashReady){
						setEffect(cooldown_coloreffect);
					} else {
						setEffect(regular_coloreffect);
					}
					if(wallJumpCounter > 0 && wallJumpCounter < 6) {
						setEffect(wallJump_coloreffect);
					}
					
					
				}

			} else {
				jump = false;
				dy = 0;
			}
			
			setHitboxes();
			
			
			if(wallJumpCounter > 0) {
				Image wallPushImage = new Image("file:src/game-resources/character_sprites/attack/attack5.png");
				
				img = (mostRecentDir == -1) ? wallPushImage : mirrorImage(wallPushImage);
			}
			
			setImage(img);
			setFitHeight(ACTOR_HEIGHT);
			setFitWidth(ACTOR_WIDTH);
			
			checkSpikes();
			checkBeams();

			if (getIntersectingObjects(Coin.class).size() > 0) {
				for (Coin c : getIntersectingObjects(Coin.class)) {
					
					c.setX(coin_off_center + c.getFitWidth()/2);
					c.setY(15);
					coin_off_center += c.getFitWidth();
					((MainWorld) getWorld()).addCoin();
					
				}
			}
		} else {
			setEffect(regular_coloreffect);
			if(((deathFrame/5)%10+1) <= 5) {
				Image deathImg = new Image("file:src/game-resources/character_sprites/death/death" + ((deathFrame/5)%10+1) + ".png");
				
				if(mostRecentDir == -1) {
					setImage(deathImg);
				} else {
					setImage(mirrorImage(deathImg));
				}
				deathFrame++;
			} else {
				setImage(mirrorImage(new Image("file:src/game-resources/character_sprites/idle/idle1.png")));
				dead = false;
				setX(ORIG_X);
				setY(ORIG_Y);
				deathFrame = 1;
				pastX = ORIG_X;
				resetAttributes();
				
			}
		}
		
	}
	
	public void resetAttributes() {
		dash = false;
		dy = 0;
		diagonalDir = 0;
		xdash_velocity = 30;
		ydash_velocity = 24;
		wallJump = false;
		wallJumpCounter = 0;
	}
	
	public void setHitboxes() {
		if(dash && moving == 2) {
			topHitbox.setX((mostRecentDir==1) ? getX()+22 : getX() + 33);
			topHitbox.setY(getY()+8);
			
			midleftHitbox.setX((mostRecentDir==1) ? getX()+18 : getX() + 25);
			midleftHitbox.setY(getY()+43);

			midrightHitbox.setX((mostRecentDir==1) ? getX()+26 : getX() + 30);
			midrightHitbox.setY(getY()+45);
			
			bottomHitbox.setX((mostRecentDir==1) ? getX()+16 : getX() + 23);
			bottomHitbox.setY(getY()+65);
		} else {
			topHitbox.setX((mostRecentDir==1) ? getX()+20 : getX() + 35);
			topHitbox.setY(getY()+8);
			
			midleftHitbox.setX((mostRecentDir==1) ? getX()+7 : getX() + 34);
			midleftHitbox.setY(getY()+43);

			midrightHitbox.setX((mostRecentDir==1) ? getX()+45 : getX() + 23);
			midrightHitbox.setY(getY()+45);
			
			bottomHitbox.setX((mostRecentDir==1) ? getX()+16 : getX() + 25);
			bottomHitbox.setY(getY()+65);
		}
		
	}

	public void checkSpikes() {
		
		List<Spike> spikes1 = topHitbox.getIntersectingObjects(Spike.class);
		List<Spike> spikes2 = midleftHitbox.getIntersectingObjects(Spike.class);
		List<Spike> spikes3 = midrightHitbox.getIntersectingObjects(Spike.class);
		List<Spike> spikes4 = bottomHitbox.getIntersectingObjects(Spike.class);
		if(spikes1.size() + spikes2.size() + spikes3.size() + spikes4.size() != 0) {
			dead = true;
		}
	}
	
	public void checkBeams() {
		List<Beam> beams1 = topHitbox.getIntersectingObjects(Beam.class);
		List<Beam> beams2 = midleftHitbox.getIntersectingObjects(Beam.class);
		List<Beam> beams3 = midrightHitbox.getIntersectingObjects(Beam.class);
		List<Beam> beams4 = bottomHitbox.getIntersectingObjects(Beam.class);
		if (beams1.size() + beams2.size() + beams3.size() + beams4.size() != 0) {
			dead = true;
		}
	}
	

	public int directionMoving() {
		return moving;
	}
	
	public boolean isDashing() {
		return dash;
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

	private double getLeftFootX(Image img, int lastDir) {
		reader = img.getPixelReader();
		
		for(int i=0; i<(int)(img.getWidth()); i++) {
			if(reader.getColor(i, (int)(img.getHeight()-1)).getRed() !=0) {

				return i + ((lastDir==1) ? 2 : 4);
			}
		}

		return 0;
	}
	private double getRightFootX(Image img, int lastDir) {
		reader = img.getPixelReader();
		
		for(int i=(int)(img.getWidth())-1; i>=0; i--) {
			if(reader.getColor(i, (int)(img.getHeight()-1)).getRed() !=0) {

				return i + ((lastDir==1) ? 6 : 8);
			}
		
		}
		return 0;
	}
	
	private Tile getTileInPlayerYPath(double y) {
		if(getWorld().getObjectsAt(getX() + 34, y, Tile.class).size() != 0) {
			return getWorld().getObjectsAt(getX() + 34, y, Tile.class).get(0);
		}
		return null;
			
	}

	private Tile getTileInPlayerXPath(double x) {
		// wall detection - feet
		if (getWorld().getObjectsAt(x, getY() + ACTOR_HEIGHT-1, Tile.class).size() != 0) {
			return getWorld().getObjectsAt(x, getY() + ACTOR_HEIGHT-1, Tile.class).get(0);
		}
		//head
		if(getWorld().getObjectsAt(x, getY()+20, Tile.class).size() != 0) {
			return getWorld().getObjectsAt(x, getY()+20, Tile.class).get(0);
		}
		//body
		if(getWorld().getObjectsAt(x, getY()+TILE_SIZE, Tile.class).size() != 0) {
			return getWorld().getObjectsAt(x, getY()+TILE_SIZE, Tile.class).get(0);
		}
		return null;
	}

//	private Tile getTileInDashPath(double x, int mostRecentDir, double xdash_velocity) {
//		if(mostRecentDir == 1) {
//			for(int i = x; )
//		}
//
//	}

}
