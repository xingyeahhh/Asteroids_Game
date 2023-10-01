package Body;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class Alien extends Character {
	private long lastShotTime=0;
	private boolean alive;
	private Timeline blinkTimeline;
	   public Alien(int x, int y) {
	        super(createUfoShape(), x, y);
	        this.alive = true;
	        startUfoBlinking();
	    }
	
	   
	private static Polygon createUfoShape() {
	        Polygon ufo = new Polygon(
	            -15, 5,
	            -10, -1,
	            10, -1,
	            15, 5,
	            10, 8,
	            -10, 8
	        );
	        ufo.setFill(Color.GREY);
	        return ufo;
	    }

	
    public void handleCollisions(Ship ship, List<Asteroid> asteroids) {
        if (collide(ship)) {
            ship.setAlive(false);
        }

        for (Asteroid asteroid : asteroids) {
            if (collide(asteroid)) {
            	asteroid.setAlive(false);
            }
        }
    }
    
    public void randomMovement() {
        double changeX = Math.random() * 2 - 1;
        double changeY = Math.random() * 2 - 1;
        changeX *= 0.1;
        changeY *= 0.1;
        setMovement(getMovement().add(changeX, changeY));
        setMovement(getMovement().normalize().multiply(1));
    }
    
    
    // Move freely and shoot bullets around
    public List<Projectile> moveAndShoot(long now) {
        randomMovement();
        move();
        return shootAround();
    }
    
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
    
    //New method: Aliens automatically fires bullets around
    private List<Projectile> shootAround() {
        List<Projectile> projectiles = new ArrayList<>();
        long now = System.currentTimeMillis();

        // Check if the distance from the last transmission has exceeded a certain time (such as 3000 milliseconds)
        if (now - lastShotTime >= 3000) {
            lastShotTime = now;

            List<Integer> angles = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                angles.add(i);
            }
            Collections.shuffle(angles);

            //Shoot bullets in five random directions
            for (int i = 0; i < 5; i++) {
                int angleIndex = angles.get(i);
                Projectile projectile = new Projectile((int) getCharacter().getTranslateX(), (int) getCharacter().getTranslateY(), this);
                projectile.getCharacter().setFill(Color.GREY); // set projectile colour
                projectile.getCharacter().setRotate(getCharacter().getRotate() + angleIndex * 45);
                projectile.accelerate();
                projectile.setMovement(projectile.getMovement().normalize().multiply(2));
                projectiles.add(projectile);
            }
        }

        return projectiles;
    }
    
    //for shining aliens methods
    private void startUfoBlinking() {
        blinkTimeline = new Timeline();
        KeyValue keyValue1 = new KeyValue(getCharacter().fillProperty(), Color.SKYBLUE);
        KeyValue keyValue2 = new KeyValue(getCharacter().fillProperty(), Color.LIGHTGREEN);
        KeyFrame keyFrame1 = new KeyFrame(Duration.ZERO, keyValue1);
        KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.5), keyValue2);
        KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(1), keyValue1);
        blinkTimeline.getKeyFrames().addAll(keyFrame1, keyFrame2, keyFrame3);
        blinkTimeline.setAutoReverse(true);
        blinkTimeline.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline.play();
    }
}
