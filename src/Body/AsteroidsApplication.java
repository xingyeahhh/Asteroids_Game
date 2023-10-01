package Body;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import Body.Asteroid.Size;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AsteroidsApplication extends Application {
	
	public final static int WIDTH = 900;
    public final static int HEIGHT = 600;
    private final Pane pane= new Pane();
    
    private long lastShotTime=System.currentTimeMillis();;
    private long lastAlienSpawnTime = System.currentTimeMillis();
	private long lastHyperspaceJumpTime = System.currentTimeMillis();
	private long currentTime_jump;
    
    private int lastPointsMultiple = 0; //A flag to check if the player has earned 10000 points
   
    private boolean gameOver = false;
    private boolean collision_hyperspace=true;
    
    private AtomicInteger lives = new AtomicInteger(3);
    private AtomicInteger level = new AtomicInteger(1);
    private AtomicInteger level_pre = new AtomicInteger(0);
    private AtomicInteger points = new AtomicInteger();
    
    private Ship ship;
    private List<Asteroid> asteroids = new ArrayList<>();
    private List<Projectile> projectiles = new ArrayList<>();
	private List<Alien> alienList = new ArrayList<>();
	
    

    
    //This methods-Avoid duplicate additions
    private void safelyAddNodeToPane(Node node, Pane pane) {
        if (!pane.getChildren().contains(node)) {
            pane.getChildren().add(node);
        }
    }
    
    //This methods-Change the number of asteroids according to the current level
    private List<Asteroid> generateAsteroidsForLevel(AtomicInteger level) {
        int numberOfAsteroids = level.get();
        System.out.print("Test Infor: using generateAsteroidsForLevel methods");
        for (int i = 0; i < numberOfAsteroids; i++) {
        	System.out.print("Test Infor:in the for loop");
            Random random = new Random();
            Size[] sizes = Size.values();
            Size randomSize = sizes[random.nextInt(sizes.length)];
            int x = random.nextInt(AsteroidsApplication.WIDTH);
            int y = random.nextInt(AsteroidsApplication.HEIGHT);

            Asteroid asteroid = new Asteroid(x, y, randomSize);

            // Checks if the newly spawned asteroids collides with the spaceship and respawns if so
            while (asteroid.collide(ship)) {
                asteroid = new Asteroid(x, y, randomSize);                
            }
            System.out.print("Test Infor:adding asteroids");
            asteroids.add(asteroid);
        }
        return asteroids;
    }
    
    //This methods-Control the style size and duration of the text prompt when the game is in progress
    private void showTemporaryMessage(Pane pane, String message, double x, double y, Duration duration) {
    	Text text = new Text(x, y, message);
        // for words style
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        text.setFill(Color.YELLOW);
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(1);

        // for words'colour changing
        KeyValue keyValue1 = new KeyValue(text.fillProperty(), Color.YELLOW);
        KeyValue keyValue2 = new KeyValue(text.fillProperty(), Color.ORANGE);
        KeyValue keyValue3 = new KeyValue(text.fillProperty(), Color.ORANGE);
        KeyFrame keyFrame1 = new KeyFrame(Duration.ZERO, keyValue1);
        KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(1), keyValue2);
        KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(1), keyValue3);
        Timeline timeline = new Timeline(keyFrame1, keyFrame2,keyFrame3);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        pane.getChildren().add(text);
        PauseTransition pause = new PauseTransition(duration);
        pause.setOnFinished(e -> {
            pane.getChildren().remove(text);
            timeline.stop();
        });
        pause.play();
    }
    
    //This methods-Control the explosion effect when the spaceship crashes
    private void createExplosion(Ship ship, Pane pane) {
    	Random random = new Random();
        int numFragments = 60;
        for (int i = 0; i < numFragments; i++) {
        	// Create polygons of different shapes and sizes
            double[] tops = new double[7];
            for (int j = 0; j < tops.length; j++) {
                tops[j] = 5 + random.nextDouble() * 15;
            }
            Polygon fragment = new Polygon(tops);

         // Sets a random color for the polygon (between orange and dark red)
            int red = 255;
            int green = random.nextInt(128);
            int blue = random.nextInt(64);
            Color color = Color.rgb(red, green, blue);
            fragment.setFill(color);

            fragment.setTranslateX(ship.getCharacter().getTranslateX());
            fragment.setTranslateY(ship.getCharacter().getTranslateY());
            pane.getChildren().add(fragment);

            double angle = Math.toRadians(i * (360 / numFragments));
            double velocityX = 100 * Math.cos(angle);
            double velocityY = 100 * Math.sin(angle);

            TranslateTransition transition = new TranslateTransition(Duration.seconds(4), fragment);
            transition.setByX(velocityX);
            transition.setByY(velocityY);
            transition.setInterpolator(Interpolator.LINEAR);
            transition.setOnFinished(e -> pane.getChildren().remove(fragment));
            transition.play();
        }
    }
    
    //This methods-Control the explosion effect when the asteroids crashes
    private void createExplosion2(Asteroid asteroid, Pane pane) {
    	Random random = new Random();
        int numFragments = 40;
        for (int i = 0; i < numFragments; i++) {
        	// 创建不同形状和大小的多边形
            double[] tops = new double[7];
            for (int j = 0; j < tops.length; j++) {
                tops[j] = 5 + random.nextDouble() * 15;
            }
            Polygon fragment = new Polygon(tops);

        
            // Create polygons of different shapes and sizes
            int red = random.nextInt(256);
            int green = 128 + random.nextInt(128);
            int blue = random.nextInt(128);
            Color color = Color.rgb(red, green, blue);
            fragment.setFill(color);

            fragment.setTranslateX(asteroid.getCharacter().getTranslateX());
            fragment.setTranslateY(asteroid.getCharacter().getTranslateY());
            pane.getChildren().add(fragment);

            double angle = Math.toRadians(i * (360 / numFragments));
            double velocityX = 100 * Math.cos(angle);
            double velocityY = 100 * Math.sin(angle);

            TranslateTransition transition = new TranslateTransition(Duration.seconds(3), fragment);
            transition.setByX(velocityX);
            transition.setByY(velocityY);
            transition.setInterpolator(Interpolator.LINEAR);
            transition.setOnFinished(e -> pane.getChildren().remove(fragment));
            transition.play();
        }
    }
    
    //This methods-Control the explosion effect when the alien crashes
    private void createExplosion3(Alien alien, Pane pane) {
    	Random random = new Random();
        int numFragments = 40;
        for (int i = 0; i < numFragments; i++) {
        	// Create polygons of different shapes and sizes
            double[] tops = new double[6];
            for (int j = 0; j < tops.length; j++) {
                tops[j] = 5 + random.nextDouble() * 15;
            }
            Polygon fragment = new Polygon(tops);

        
            // Set a random color (between blue and purple) for the polygon
            int red = random.nextInt(128); // Limit red values to lower range
            int green = random.nextInt(128); // Limit green values to lower range
            int blue = 128 + random.nextInt(128); // Blue values are in the upper range
            Color color = Color.rgb(red, green, blue);
            fragment.setFill(color);

            fragment.setTranslateX(alien.getCharacter().getTranslateX());
            fragment.setTranslateY(alien.getCharacter().getTranslateY());
            pane.getChildren().add(fragment);

            double angle = Math.toRadians(i * (360 / numFragments));
            double velocityX = 100 * Math.cos(angle);
            double velocityY = 100 * Math.sin(angle);

            TranslateTransition transition = new TranslateTransition(Duration.seconds(3), fragment);
            transition.setByX(velocityX);
            transition.setByY(velocityY);
            transition.setInterpolator(Interpolator.LINEAR);
            transition.setOnFinished(e -> pane.getChildren().remove(fragment));
            transition.play();
        }
    }
    
    //This methods-run a set of judgements after ships crashes, about explosion effect,safe position check, invincible time and effect
    private void resetShip(Ship ship, List<Asteroid> asteroids, List<Alien> alienList,Pane pane) {
    	lives.decrementAndGet();
        if (lives.get() <= 0) {
            gameOver = true;
        }
        createExplosion(ship, pane); //add explosion effect
    	ship.setOpacity(0);
    	if (!gameOver) {
    	PauseTransition pause = new PauseTransition(Duration.seconds(1));
    	pause.setOnFinished(e -> {
    		
    	//safe position check
        Random random = new Random();
        int newX, newY;
        boolean safePosition;

        do {
            newX = random.nextInt(AsteroidsApplication.WIDTH);
            newY = random.nextInt(AsteroidsApplication.HEIGHT);
            ship.setPosition(newX, newY);
            safePosition = true;

            for (Asteroid asteroid : asteroids) {
                if (ship.collide(asteroid)) {
                    safePosition = false;
                    break;
                }
            }

            for (Alien alien : alienList) {
                if (ship.collide(alien)) {
                    safePosition = false;
                    break;
                }
            }
        } while(!safePosition);

        ship.setOpacity(1);
        ship.setMovement(new Point2D(0, 0));//stop the ship
        ship.getCharacter().setRotate(-90); // dont rotate the ship
        ship.setInvincible(true);
        
        // Change the ship's color to orange while invincible
        ship.getCharacter().toFront(); 
        ship.getCharacter().setFill(Color.ORANGE);

        // Flash the ship's color between orange and red while invincible
        KeyValue keyValue1 = new KeyValue(ship.getCharacter().fillProperty(), Color.YELLOW, Interpolator.LINEAR);
        KeyValue keyValue2 = new KeyValue(ship.getCharacter().fillProperty(), Color.ORANGE, Interpolator.LINEAR);
        KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.5), keyValue1);
        KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(1), keyValue2);
        KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(1.5), keyValue1);
        KeyFrame keyFrame4 = new KeyFrame(Duration.seconds(2), keyValue2);

        Timeline timeline = new Timeline(keyFrame1, keyFrame2, keyFrame3, keyFrame4);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // Make the ship invincible for 3-5 seconds
        PauseTransition pause2 = new PauseTransition(Duration.seconds(3 + new Random().nextInt(2)));
        pause2.setOnFinished(f -> {
            ship.setInvincible(false);
            ship.getCharacter().setFill(Color.BLACK); // Change the ship's color back to black
            timeline.stop(); // Stop the flashing animation
        });
        pause2.play();
    	});
        pause.play();
    	}
    }
   
    
    //First-show the Menu Screen
    @Override
    public void start(Stage stage) throws Exception {
        new MenuScreen().show(stage);
    }
    
    //Second-show the Main Game Screen
    public void start2(Stage stage) throws Exception {
    	//create a pane
    	pane.setPrefSize(WIDTH, HEIGHT);
        Scene scene = new Scene(pane);//Create a scene to show the pane
        stage.setTitle("Asteroids!");
        stage.setScene(scene);
        
        
    	//for recording the information and show them to players
    	//for creating a graphical user interface (GUI)
        Text text = new Text(10, 20, "Points: 0");
        Text livesText = new Text(10, 40, "Lives: " + lives);
        Text levelText = new Text(10, 60, "Present Level: " + level_pre);
        pane.getChildren().add(livesText);
        pane.getChildren().add(text);
        pane.getChildren().add(levelText);
        
        
        //to update the text at a consistent rate
        //for showing text part
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                text.setText("Points: " + points);
                livesText.setText("Lives: " + lives);
                levelText.setText("Present Level: " + level_pre);
            }
        }.start();
    
        
        //create a boolean map, maps keys of type KeyCode to values of type Boolean
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        scene.setOnKeyPressed(event -> { //press the key
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> { //release the key
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });
        
        
        //ship's original location and degree
        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);
        ship.getCharacter().setRotate(-90);
        //add ship and return a shape
        pane.getChildren().add(ship.getCharacter());

        
        //represent movement,It is used in subsequent codes to indicate the direction and distance of objects moving in the scene
        Point2D movement = new Point2D(1, 0);
                
  
        //to update the game at a consistent rate
        //for showing game part
        new AnimationTimer() {
            @Override
            public void handle(long now) {
            	//Firstly, we need to determine if the player has already gameover
            	if (gameOver) {
                    // show the end of the game
                    Text gameOverText = new Text(WIDTH / 2 - 150, HEIGHT / 2, "GAME OVER");
                    gameOverText.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
                    gameOverText.setFill(Color.RED);
                    gameOverText.setStroke(Color.BLACK);
                    gameOverText.setStrokeWidth(1);
                    pane.getChildren().add(gameOverText);
                    stop();
                    
                    // Wait for 3 seconds before showing the GameOverScreen
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> {
                        pane.getChildren().remove(gameOverText); // Remove the "Game Over" text from the pane
                        showGameOverScreen gameOverScreen = new showGameOverScreen(points);
                        gameOverScreen.show(stage);
                    });
                    pause.play();
                    stop();
                }
            	
            	
            	//Secondly, we determine whether the player will enter the next level based on the number of asteroids (whether they are empty)
            	if (asteroids.isEmpty()) {
            		if(level.get()==1) {
            			Random rnd = new Random(); // random position
            			Size[] sizes = Size.values();
            			//Size randomSize = sizes[rnd.nextInt(sizes.length)];
            			Asteroid asteroid = new Asteroid(rnd.nextInt(800), rnd.nextInt(500), sizes[0]);
            			asteroids.add(asteroid);
            			level.incrementAndGet();
            			
            	    }else if (level.get() > 1){
            	    	showTemporaryMessage(pane, "Level Up!", WIDTH / 2 - 50, HEIGHT *0.4, Duration.seconds(3));
                	    List<Asteroid> newAsteroids = generateAsteroidsForLevel(level);
                	    asteroids.addAll(newAsteroids);
                	    System.out.print(level);
                	    level.incrementAndGet();        	    
            	    }
            		level_pre.incrementAndGet();
            		//Delete existing Asteroid child nodes before adding new ones
                    asteroids.forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
                    //Use the safelyAddNodeToPan method to add Asteroid child nodes to avoid duplicate additions
                    asteroids.forEach(asteroid -> safelyAddNodeToPane(asteroid.getCharacter(), pane));
            	}
            	
            	
            	
            	//master the ship to run
            	//when keycode.left is not exist then return false
            	//pressedKeys.getOrDefault(KeyCode.LEFT, false) checks if the KeyCode.LEFT key is currently pressed. If it is, it returns true, which causes the ship object to rotate counterclockwise. 
                if(pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                if(pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                	ship.turnRight();
                }
                
                if(pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();                
                }

                //ship,asteroids, projectiles to move this moment
                //ship.move();
                ship.moveWithTrail(pane);
                asteroids.forEach(asteroid -> asteroid.move());
                projectiles.forEach(projectile -> projectile.move());
                
                
                //This part of the code is about hyperspace dance
                currentTime_jump = System.currentTimeMillis();
                if (pressedKeys.getOrDefault(KeyCode.DOWN, false) && currentTime_jump - lastHyperspaceJumpTime >= 10000) { 
                	//must 10 seconds between 2 hyperspace jump
                	ship.setOpacity(0);
                	//during hyperspace dance, this ship is invisible.
                    Random random = new Random();
                    int newX, newY;
                    //just show up in a random position
                    do {
                    	//to assure random position 
                        newX = random.nextInt(AsteroidsApplication.WIDTH);
                        newY = random.nextInt(AsteroidsApplication.HEIGHT);
                        ship.setOpacity(0);
                        ship.setPosition(newX, newY);
                        collision_hyperspace = false;
                        for (Asteroid asteroid : asteroids) {
                            if (ship.collide(asteroid)) {
                                collision_hyperspace = true;
                                break;
                            }
                        }
                        for (Alien alien : alienList) {
                            if (ship.collide(alien)) {
                                collision_hyperspace = true;
                                break;
                            }
                        }
                    } while (collision_hyperspace);
                    //and to ensure that the ship will not have collision with alien and asteroids
                    if(!collision_hyperspace) {
                        ship.setOpacity(1);
                        ship.setMovement(new Point2D(0, 0));
                        }
                    lastHyperspaceJumpTime = currentTime_jump;
                }


                
                //This part is for projectiles shooting law and projectiles showing
                scene.setOnKeyReleased(event -> {
                    pressedKeys.put(event.getCode(), Boolean.FALSE);
                    if (event.getCode() == KeyCode.SPACE && projectiles.size() < 20) {
                        long currentTime_bullet = System.currentTimeMillis();
                        if (currentTime_bullet - lastShotTime >= 200) { //Check if the distance from the last transmission has exceeded 200 milliseconds
                            lastShotTime = currentTime_bullet;

                            //Code for firing bullets
                            Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY(),ship);
                            projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                            projectiles.add(projectile);
                            projectile.accelerate();
                            projectile.setMovement(projectile.getMovement().normalize().multiply(3));
                            pane.getChildren().add(projectile.getCharacter());
                        }
                    }
                });
                
                projectiles.forEach(projectile -> {
                    // Obtain the creation time of the bullet
                    long createTime = projectile.getCreateTime();
                    // obtain the current time
                    long curTime = System.currentTimeMillis();
                    // Obtain the creation time of the bullet. If the bullet already exists for 2 seconds, set it to a dead state
                    if (curTime - createTime > 2000) {
                        projectile.setAlive(false);
                    }
                });
                
                    
                
                //Add Alien Part
                //Automatically generate Allen's logic every other period of time
                long currentTime_alien = System.currentTimeMillis();
                long timeSinceLastSpawn = currentTime_alien - lastAlienSpawnTime;
                Random rnd = new Random();
                long spawnInterval = rnd.nextInt(4001) + 8000; // Random value between 8000 and 12000 to create an alien
               
                

                    if (timeSinceLastSpawn >= spawnInterval && alienList.size() < 1) {
                    lastAlienSpawnTime = currentTime_alien;

                    Random random = new Random();
                    int x = random.nextInt(AsteroidsApplication.WIDTH);
                    int y = random.nextInt(AsteroidsApplication.HEIGHT);

                    Alien alien = new Alien(x, y);

                    //Check if Alien has collided with a meteorite and spacecraft
                    boolean isColliding = false;
                    for (Asteroid asteroid : asteroids) {
                        if (alien.collide(asteroid)) {
                            isColliding = true;
                            break;
                        }
                    }
                    if (!isColliding && alien.collide(ship)) {
                        isColliding = true;
                    }

                    //If Alien did not collide with a meteorite or spacecraft, add it to the scene and Alien list
                    if (!isColliding) {
                    	showTemporaryMessage(pane, "Alien is coming!!!", WIDTH / 2 - 80, HEIGHT *0.3, Duration.seconds(3));
                        alienList.add(alien);
                        pane.getChildren().add(alien.getCharacter());
                    }
                }
                    
                    
                // //Move Alien and let it fire bullets around
                for (Alien alien : alienList) {
                    List<Projectile> alienProjectiles = alien.moveAndShoot(now);
                    projectiles.addAll(alienProjectiles);
                    alienProjectiles.forEach(projectile -> pane.getChildren().add(projectile.getCharacter()));
                }
                
                
                
                            
                
                //collision part!!!!
                
                
                //collision(asteroids and ship): check collision between asteroids between ship           
                asteroids.forEach(asteroid -> {
                    if (ship.collide(asteroid) && !ship.isInvincible()) {
                    	ship.setInvincible(true); 
                    	//Set the spaceship to invincible before calling resetShip to prevent the generated small meteorites from repeatedly colliding
                        resetShip(ship, asteroids, alienList,pane);
                    }
                });
                
                
                //collision(asteroids and alien): check collision between asteroids between alien
                //check collision between alien and asteroid
                //In this case, using new ArrayList<>(alienList) and new ArrayList<>(asteroids) to create new lists is to avoid the ConcurrentModificationException. This exception may occur when you modify a collection (e.g., adding or removing elements) while iterating over it.
                //Certainly, you can separate the creation of the new ArrayLists and then use them in the loop.
                //List<Alien> alienListCopy = new ArrayList<>(alienList);
                //List<Asteroid> asteroidsCopy = new ArrayList<>(asteroids);

                for (Alien alien : new ArrayList<>(alienList)) {
                    for (Asteroid asteroid : new ArrayList<>(asteroids)) {
                        if (alien.collide(asteroid)) {
                        	lastAlienSpawnTime = System.currentTimeMillis();
                            alien.setAlive(false);
                            asteroid.setAlive(false);
                            createExplosion3(alien,pane);
                            createExplosion2(asteroid,pane);
                            List<Asteroid> splittedAsteroids = asteroid.handleCollision();
                            asteroids.addAll(splittedAsteroids);
                            splittedAsteroids.forEach(newAsteroid -> pane.getChildren().add(newAsteroid.getCharacter()));
                        }
                    }
                }
                
                
                //collision(ship and alien):check collision between alien and ship
                for (Alien alien : alienList) {
                    if (ship.collide(alien) && !ship.isInvincible()) {
                        //stop();
                    	ship.setInvincible(true); // 在调用 resetShip 之前设置飞船为无敌
                        resetShip(ship, asteroids, alienList,pane);
                    }
                }
                
                //collision(ship and projectiles):check collision between projectiles and ship
                projectiles.forEach(projectile -> {
                    if (!projectile.getOwner().equals(ship) && ship.collide(projectile) && !ship.isInvincible()) {
                        //stop();
                    	projectile.setAlive(false);
                    	ship.setInvincible(true); // 在调用 resetShip 之前设置飞船为无敌
                        resetShip(ship, asteroids, alienList,pane);
                    }
                });
                
                //collision(alien and projectiles):check collision between projectiles and alien   
                projectiles.forEach(projectile -> {
                    for (Alien alien : new ArrayList<>(alienList)) {
                        if (!projectile.getOwner().equals(alien) && projectile.collide(alien)) {
                            projectile.setAlive(false);
                            lastAlienSpawnTime = System.currentTimeMillis();
                            alien.setAlive(false);
                            createExplosion3(alien,pane);
                            text.setText("Points: " + points.addAndGet(3000));
                        }
                    }
                });

                
                //collision(projectiles and asteroids):check collision between projectiles and asteroids   
                //By using streams, a series of operations can be performed on elements in a collection, such as filtering, mapping, sorting, counting, and so on.
                List<Asteroid> newAsteroids = new ArrayList<>();             
                    for (Projectile projectile2 : new ArrayList<>(projectiles)) {
                        for (Asteroid asteroid : new ArrayList<>(asteroids)) {
                            if (projectile2.collide(asteroid)) {
                                projectile2.setAlive(false);
                                asteroid.setAlive(false);
                                if (projectile2.getOwner().equals(ship)) {
                                text.setText("Points: " + points.addAndGet(1000));//The spaceship destroyed the meteorite and added points
                                }
                                createExplosion2(asteroid,pane);
                                newAsteroids.addAll(asteroid.handleCollision());//The big asteroids destroied will create 2 other asteroids. 
                                asteroids.addAll(newAsteroids);
                                newAsteroids.forEach(newAsteroid -> pane.getChildren().add(newAsteroid.getCharacter()));
                                newAsteroids.clear();
                            }
                        }
                    }
                    
                    
                //Remove dead things part!!!
                    
                //remove all the dead projectiles out of the pane  
                projectiles.stream()
                    .filter(projectile -> !projectile.isAlive())
                    .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));
                projectiles.removeAll(projectiles.stream()
                                        .filter(projectile -> !projectile.isAlive())
                                        .collect(Collectors.toList()));
                
                
                //remove all the dead asteroids out of the pane 
                asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
                asteroids.removeAll(asteroids.stream()
                                            .filter(asteroid -> !asteroid.isAlive())
                                            .collect(Collectors.toList()));
                
                
                //remove all the dead alien out of the pane 
                alienList.stream()
                         .filter(alien -> !alien.isAlive())
                         .forEach(alien -> pane.getChildren().remove(alien.getCharacter()));
                alienList.removeAll(alienList.stream()
                                             .filter(alien -> !alien.isAlive())
                                             .collect(Collectors.toList()));
             
                
                
                //At the End of the animation render part,and I want to check if the player has scored 10000 points
                int currentPointsMultiple = points.get() / 10000;
                if (currentPointsMultiple > lastPointsMultiple ) {
                    lives.incrementAndGet();
                    lastPointsMultiple = currentPointsMultiple;
                    showTemporaryMessage(pane, "Extra Life!", WIDTH / 2 - 50, HEIGHT / 2, Duration.seconds(3));
                } 
            }
        }.start();  
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
