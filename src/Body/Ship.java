package Body;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Ship extends Character {
	private Polygon tailFlame;
	private boolean alive;
	private boolean invincible;
	private int trailCounter = 0;
    public Ship(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);//¿^≥–∏∏Óê
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
    public Polygon getTailFlame() {
        return tailFlame;
    }
    
    //for hyperspace jump function
    public void setPosition(int x, int y) {
        getCharacter().setTranslateX(x);
        getCharacter().setTranslateY(y);
    }
    
    public void setOpacity(double opacity) {
        // Assuming the ship's graphic object is an ImageView
        super.setOpacity(opacity);
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }
    
    public void addTrail(Pane pane) {
        double x = getCharacter().getTranslateX();
        double y = getCharacter().getTranslateY();
        Trail trail = new Trail(x, y, 0.5, 0.5); // 3 is the radius of the trail
        pane.getChildren().add(trail);
    }
    
    public void bringToFront() {
    	getCharacter().toFront();
    }
    
    public void moveWithTrail(Pane pane) {
        super.move(); // Call the move method from the Character class

        trailCounter++; // Increment the trail counter

        // Add a trail only when trailCounter is within a certain range
        if (trailCounter % 5 < 5) {
            double x = getCharacter().getTranslateX()+2.5;
            double y = getCharacter().getTranslateY()+0.5;
            Trail trail = new Trail(x, y, 0.7, 2.5); // 2 is the radius of the trail
            trail.fadeOut(pane,0.5); // Make the trail disappear after 0.5 seconds
            pane.getChildren().add(trail);
            getCharacter().toFront();  // Bring the ship to the front
        }
    }
    

    
}