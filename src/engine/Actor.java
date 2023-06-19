package engine;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView {

    public Actor() {

    }

    public abstract void act(long now);

    // to be overriden by subclasses
    public void addedToWorld() {

    }

    public double getHeight() {
        return getBoundsInParent().getHeight();
    }

    public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls) {
        List<A> list = new ArrayList<>();
        Bounds b = getBoundsInParent();
        
        World world = getWorld();
        for (Node n: world.getChildren()) {
        	if (b.intersects(n.getBoundsInParent()) && !n.equals(this) && cls.isInstance(n)) list.add((A)n); 
        }
        
        return list;
    }

    public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
    	List<A> list = getIntersectingObjects(cls);
    	return (list.size() > 0) ? list.get(list.size() - 1) : null;
    }

    public double getWidth() {
        return getBoundsInParent().getWidth();
    }

    public World getWorld() {
        return (World)getParent();
    }

 
    
    public void move(double dx, double dy) {
        double newX = getX() + dx;
        double newY = getY() + dy;
        setX(newX);
        setY(newY);
    }
}