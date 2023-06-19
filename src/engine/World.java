package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public abstract class World extends Pane {
	private MyAnimationTimer timer;
	private boolean timerRunning;
	private Set<KeyCode> keyCodes;
	private boolean widthSet;
	private boolean heightSet;
	private boolean dimensionsInit;
	private double delay;
	
	public World() {
		widthSet = false;
		heightSet = false;
		keyCodes = new HashSet<KeyCode>();
		timerRunning = false;
		dimensionsInit = false;
		delay = 2e7; //50 fps
		
		//potential problem - if both width and height property are 
		//getting adjusted and both turn true, onDimensions runs 2x
		
		widthProperty().addListener(e -> {
			if (getWidth() > 0) widthSet = true;
			if (widthSet && heightSet && !dimensionsInit) {
				onDimensionsInitialized();
				dimensionsInit = true;
			}
		});
		
		heightProperty().addListener(e -> {
			if (getHeight() > 0) heightSet = true;
			if (widthSet && heightSet && !dimensionsInit) {
				onDimensionsInitialized();
				dimensionsInit = true;
			}
		});
		
		sceneProperty().addListener((ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) -> {
			if (newValue != null) { requestFocus(); }
		});
		
		setOnKeyPressed(e -> {
			keyCodes.add(e.getCode());
		});
		
		setOnKeyReleased(e -> {
			keyCodes.remove(e.getCode());
		});
		
		timer = new MyAnimationTimer();
		
	}
	
	/** This method is called every frame once start has been called. */
	public abstract void act(long now);
	
	/** Subclasses should override this. */
	public abstract void onDimensionsInitialized();

	/** Adds the given actor to the world and then calls the addedToWorld() method on the actor that was added. */
	public void add(Actor actor) {
		getChildren().add(actor);
		actor.addedToWorld();
	}
	
	/** Returns a list of all the actors in the world of the given class. */
	public <A extends Actor>List<A> getObjects(Class<A> cls) {
		List<A> objects = new ArrayList<A>();
		for (Node object : getChildren()) {
			if (cls.isInstance(object)) {objects.add((A) object);}
		}
		
		return objects;
	}
	
	/** Returns a list of all actors of the given class containing the given x, y */
	public <A extends Actor>List<A> getObjectsAt(double x, double y, Class<A> cls) {
		List<A> objects = getObjects(cls);
		List<A> res = new ArrayList<A>();
		
		for (A object: objects) {
			if (object.getBoundsInParent().contains(x, y)) {
				res.add(object);
			}
		}
		
		return res;
	}

	/** Returns true if the given key is pressed and false otherwise.*/
	public boolean isKeyPressed(KeyCode code) {
		return keyCodes.contains(code); 
	}
	
	/**Returns whether or not the world's timer is stopped */
	public boolean isStopped() {
		return !timerRunning;
	}
	
	/** Removes the given actor from the world.*/
	public void remove(Actor actor) {
		getChildren().remove((Node) actor);
	}
	
	/** STARTS the timer that calls the act method on the world and on each Actor in the world each frame. */
	public void start() {
		timer.start();
		timerRunning = true;
	}
	
	/** STOPS the timer that calls the act method on the world and on each Actor in the world each frame. */
	public void stop() {
		timer.stop();
		timerRunning = false;
	}
	
	class MyAnimationTimer extends AnimationTimer { //figure out getObjects()
		long oldTime = 0;
		
		public void handle(long now) {
			if (now - oldTime >= delay) {
				act(now); 
				if (getObjects(Actor.class) != null) {
					List<Actor> actors = getObjects(Actor.class);
					for (int i = actors.size() - 1; i >= 0; i--) {
						if (getChildren().contains(actors.get(i))) actors.get(i).act(now);
					}
				}
				oldTime = now;
			}
		}
	}
	
}
