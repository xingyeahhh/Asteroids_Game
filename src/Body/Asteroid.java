package Body;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Asteroid extends Character {

    public enum Size {
        LARGE, MEDIUM, SMALL
    }

    private double rotationalMovement;
    private boolean alive;
    private Size size;

    public Asteroid(int x, int y, Size size) {
        super(new PolygonFactory().createPolygon(), x, y); //Ëæ»úÉú³É
        this.alive = true;
        this.size = size;

        Random rnd = new Random();

        super.getCharacter().setRotate(rnd.nextInt(360));

        int accelerationAmount;
        switch (size) {
            case LARGE:
                accelerationAmount = 3 + rnd.nextInt(2);
                super.getCharacter().setScaleX(3);
                super.getCharacter().setScaleY(3);
                break;
            case MEDIUM:
                accelerationAmount = 8 + rnd.nextInt(4);
                super.getCharacter().setScaleX(1.5);
                super.getCharacter().setScaleY(1.5);
                break;
            case SMALL:
                accelerationAmount = 15 + rnd.nextInt(15);
                super.getCharacter().setScaleX(1);
                super.getCharacter().setScaleY(1);
                break;
            default:
                accelerationAmount = 1 + rnd.nextInt(10);
        }

        for (int i = 0; i < accelerationAmount; i++) {
            accelerate();
        }

        this.rotationalMovement = 1.5 - rnd.nextDouble();
    }
    

    @Override
    public void move() {
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotationalMovement);
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
    
    public List<Asteroid> handleCollision() {
        List<Asteroid> newAsteroids = new ArrayList<>();
        if (size == Size.LARGE) {
            for (int i = 0; i < 2; i++) {
                newAsteroids.add(new Asteroid((int) getCharacter().getTranslateX(), (int) getCharacter().getTranslateY(), Size.MEDIUM));
            }
        } else if (size == Size.MEDIUM) {
            for (int i = 0; i < 2; i++) {
                newAsteroids.add(new Asteroid((int) getCharacter().getTranslateX(), (int) getCharacter().getTranslateY(), Size.SMALL));
            }
        }
        return newAsteroids;
    }
}