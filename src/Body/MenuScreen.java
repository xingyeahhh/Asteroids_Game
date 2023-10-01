
package Body;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuScreen {
    private static final int WIDTH_Menu = 900;
    private static final int HEIGHT_Menu = 600;


    public void show(Stage stage) {
        // Create menu title
        Text title = new Text("Asteroids!");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        title.setFill(Color.RED);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(1);

        // Create the flashing effect
        Timeline timeline = new Timeline();
        KeyValue keyValue1 = new KeyValue(title.fillProperty(), Color.PINK);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(300), keyValue1);
        KeyValue keyValue2 = new KeyValue(title.fillProperty(), Color.RED);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(1000), keyValue2);
        timeline.getKeyFrames().addAll(keyFrame1, keyFrame2);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        // Create clickable start game text
        StackPane startTextContainer = new StackPane();
        Text startText = new Text("New   Game");
        startText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        startText.setFill(Color.YELLOWGREEN);
        startText.setStroke(Color.BLACK);
        startText.setStrokeWidth(0.5);
        startTextContainer.getChildren().add(startText);
        startTextContainer.setOnMouseClicked((MouseEvent event) -> {
            // Start the game
            try {
                new AsteroidsApplication().start2(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Create clickable instructions text
        StackPane instructionsTextContainer = new StackPane();
        Text instructionsText = new Text("Instruction");
        instructionsText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        instructionsText.setFill(Color.SKYBLUE);
        instructionsText.setStroke(Color.BLACK);
        instructionsText.setStrokeWidth(0.5);
        instructionsTextContainer.getChildren().add(instructionsText);
        instructionsTextContainer.setOnMouseClicked((MouseEvent event) -> {
            // Show instructions
        	showInstructions(stage);
        });

        // Create clickable high scores text
        StackPane highScoresTextContainer = new StackPane();
        Text highScoresText = new Text("High Scores");
        highScoresText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        highScoresText.setFill(Color.YELLOW);
        highScoresText.setStroke(Color.BLACK);
        highScoresText.setStrokeWidth(0.5);
        highScoresTextContainer.getChildren().add(highScoresText);
        highScoresTextContainer.setOnMouseClicked((MouseEvent event) -> {
            // Show high scores
        	 showHighScores(stage);
        });

     // Add spacing between title and first text button
        Region spacer = new Region();
        spacer.setMinHeight(30);

        // Create layout
        VBox menuLayout = new VBox(20);
        menuLayout.getChildren().addAll(title, spacer ,startTextContainer, instructionsTextContainer, highScoresTextContainer);
        menuLayout.setAlignment(Pos.CENTER);

        // Create the main layout
        StackPane mainLayout = new StackPane();
        mainLayout.setAlignment(Pos.TOP_LEFT); // to ensure position is right
        
        // Create asteroids
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
        	Asteroid asteroid = new Asteroid((int) (Math.random() * MenuScreen.WIDTH_Menu), (int) (Math.random() * MenuScreen.HEIGHT_Menu), Asteroid.Size.values()[new Random().nextInt(Asteroid.Size.values().length)]);
            asteroids.add(asteroid);
            mainLayout.getChildren().add(asteroid.getCharacter());
        }

        // Add the menuLayout on top of the asteroids
        mainLayout.getChildren().add(menuLayout);
        
        // Create asteroid animation timer
        AnimationTimer asteroidAnimationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Asteroid asteroid : asteroids) {
                    asteroid.move();
                }
            }
        };
        asteroidAnimationTimer.start();

        // Create and show the scene
        Scene menuScene = new Scene(mainLayout, MenuScreen.WIDTH_Menu, MenuScreen.HEIGHT_Menu);
        stage.setScene(menuScene);
        stage.setTitle("Asteroids-Menu");
        stage.show();
        
        
    }
    
    
    
    
    
    
    private void showHighScores(Stage stage) {
        // Create high scores title
        Text highScoresTitle = new Text("High Scores");
        highScoresTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        highScoresTitle.setFill(Color.SKYBLUE);
        highScoresTitle.setStroke(Color.BLACK);
        highScoresTitle.setStrokeWidth(1);
        
        // Create the flashing effect
        Timeline timeline = new Timeline();
        KeyValue keyValue1 = new KeyValue(highScoresTitle.fillProperty(), Color.PINK);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(300), keyValue1);
        KeyValue keyValue2 = new KeyValue(highScoresTitle.fillProperty(), Color.ORANGE);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(1000), keyValue2);
        timeline.getKeyFrames().addAll(keyFrame1, keyFrame2);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Load high scores
        HighScoresManager highScoresManager = new HighScoresManager();
        List<String> highScores = highScoresManager.loadHighScores();

        // Create high scores list container
        VBox highScoresContainer = new VBox(5);
        highScoresContainer.setAlignment(Pos.CENTER);

        // Add high scores to the container
        showGameOverScreen gameOverScreen = new showGameOverScreen(new AtomicInteger(0));
        gameOverScreen.updateHighScoresList(highScoresContainer, highScores);

        // Create back button
        Text backToMenu = new Text("Back to menu");
        backToMenu.setFill(Color.YELLOWGREEN);
        backToMenu.setStroke(Color.BLACK);
        backToMenu.setStrokeWidth(1);
        backToMenu.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        backToMenu.setOnMouseClicked(event -> {
            show(stage);
        });

        // Create layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(highScoresTitle, highScoresContainer, backToMenu);
        layout.setAlignment(Pos.CENTER);

        // Create and show the scene
        Scene highScoresScene = new Scene(layout,  MenuScreen.WIDTH_Menu, MenuScreen.HEIGHT_Menu);
        stage.setScene(highScoresScene);
        stage.setTitle("Asteroids-High Score");
        stage.show();
    }
    
    private void showInstructions(Stage stage) {
        // Create instructions title
        Text instructionsTitle = new Text("Instructions");
        instructionsTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        instructionsTitle.setFill(Color.SKYBLUE);
        instructionsTitle.setStroke(Color.BLACK);
        instructionsTitle.setStrokeWidth(1);
        
        // Create the flashing effect
        Timeline timeline = new Timeline();
        KeyValue keyValue1 = new KeyValue(instructionsTitle.fillProperty(), Color.YELLOW);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(300), keyValue1);
        KeyValue keyValue2 = new KeyValue(instructionsTitle.fillProperty(), Color.SKYBLUE);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(1000), keyValue2);
        timeline.getKeyFrames().addAll(keyFrame1, keyFrame2);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Create instruction content
        Text instructionsContent = new Text(
        		  "1. UP    Ship Move\n"
                + "2. Left  Turn Left\n"
                + "3. Right Turn Right\n"
                + "4. Down  Hpyerspace Dance\n"
                +"5. Space  Fires Bullets (With Acceleration)\n"
                +"6. The player can shoot up to 20 bullets at once.\n"
                +"7. Bullets vanish from the screen two seconds after being fired.\n"
                +"8. The life value will rise by one each time the player reaches 10,000 points.\n"
                +"9. Regardless of size, players receive 1000 points for destroying meteorites.\n"
                +"10. Small asteroids have the highest movement speed, and acceleration\n"
                +"11. Large asteroids have the lowest movement speed, and acceleration\n"
                +"12. Players earn 3000 points for eliminating the alien.\n"
                +"13. The spacecraft has a hyperspace jump capability every 10 seconds.\n"
                +"14. The spaceship inherits the direction after the hyperspace jump, but its speed is zero.\n"
                + "15. The alien shoots in five of the eight directions roughly every three seconds.\n"
                +"16. The alien will reemerge after being hit every eight to twelve seconds.\n"
                +"17. The respawned spaceship will appear in a safe place on the screen and will be invincible for 3-5 seconds.\n"
                );

        instructionsContent.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        instructionsContent.setFill(Color.GREY);

        // Create back button
        Text backToMenu = new Text("Back to menu");
        backToMenu.setFill(Color.YELLOWGREEN);
        backToMenu.setStroke(Color.BLACK);
        backToMenu.setStrokeWidth(1);
        backToMenu.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        backToMenu.setOnMouseClicked(event -> {
            show(stage);
        });

        // Create layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(instructionsTitle, instructionsContent, backToMenu);
        layout.setAlignment(Pos.CENTER);

        // Create and show the scene
        Scene instructionsScene = new Scene(layout,  MenuScreen.WIDTH_Menu, MenuScreen.HEIGHT_Menu);
        stage.setScene(instructionsScene);
        stage.setTitle("Asteroids-Instruction");
        stage.show();
    }


}

