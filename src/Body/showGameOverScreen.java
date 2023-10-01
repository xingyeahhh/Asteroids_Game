package Body;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class showGameOverScreen {
	private AtomicInteger score;
	private VBox layout; // Define layout as a member variable
	private Text highScoresTitle= new Text("High Scores");; // Define highScoresTitle as a member variable
	public showGameOverScreen(AtomicInteger points) {
		
	        this.score = points;
	        layout = new VBox(10); // Initialize layout in the constructor
	        //Text highScoresTitle = new Text("High Scores");
            highScoresTitle.setFill(Color.SKYBLUE);
            highScoresTitle.setStroke(Color.BLACK);
            highScoresTitle.setStrokeWidth(1);
            highScoresTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
            highScoresTitle.setVisible(false);
	    }

    public void show(Stage stage) {
        // Add a Game Over title
        Text gameOverTitle = new Text("Game Over");
        gameOverTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        gameOverTitle.setFill(Color.RED);
        gameOverTitle.setFill(Color.RED);
        gameOverTitle.setStroke(Color.BLACK);
        gameOverTitle.setStrokeWidth(1);
        
        // Create the flashing effect
        Timeline timeline_gameover = new Timeline();
        KeyValue keyValue1 = new KeyValue(gameOverTitle.fillProperty(), Color.PINK);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(300), keyValue1);
        KeyValue keyValue2 = new KeyValue(gameOverTitle.fillProperty(), Color.RED);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(1000), keyValue2);
        timeline_gameover.getKeyFrames().addAll(keyFrame1, keyFrame2);
        timeline_gameover.setCycleCount(Timeline.INDEFINITE);
        timeline_gameover.play();
        
        TextField playerNameInput = new TextField();
        playerNameInput.setPromptText("Enter your name");
        playerNameInput.setMaxWidth(200); // Set the max width of the text field
        playerNameInput.setMaxHeight(30); // Set the max height of the text field
        
        Button submitButton = new Button("Submit");
        // TODO: Add submitButton action to save playerNameInput and display high scores
 
        
        VBox highScoresContainer = new VBox(5);
        highScoresContainer.setAlignment(Pos.CENTER);
        HighScoresManager highScoresManager = new HighScoresManager();
        //updateHighScoresList(highScoresList, highScoresManager.loadHighScores());
        
        submitButton.setOnAction(event -> {
            highScoresManager.addHighScore(playerNameInput.getText(), score.get());
            updateHighScoresList(highScoresContainer, highScoresManager.loadHighScores());
            layout.getChildren().remove(playerNameInput);
            layout.getChildren().remove(submitButton);
            highScoresTitle.setVisible(true); // Show the title
            System.out.println("highScoresTitle visible: " + highScoresTitle.isVisible()); // Add this line to check if the visible property is changed
        });
        
              
              
        Text backToMenu = new Text("Back to menu");
        backToMenu.setFill(Color.YELLOWGREEN);
        backToMenu.setStroke(Color.BLACK);
        backToMenu.setStrokeWidth(1);
        backToMenu.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        backToMenu.setOnMouseClicked(event -> {
            // Return to main menu
            MenuScreen menuScreen = new MenuScreen();
            menuScreen.show(stage);
        });
        
        
        layout.getChildren().addAll(gameOverTitle, playerNameInput, submitButton, highScoresTitle, highScoresContainer, backToMenu);
        layout.setAlignment(Pos.CENTER);
        
        Scene gameOverScene = new Scene(layout, 900, 600);
        stage.setScene(gameOverScene);
        stage.show();
    }
    
    public void updateHighScoresList(VBox highScoresContainer, List<String> highScores) {
        int maxDisplayedScores = 5; // Limit to the top 5 scores
        int displayedScoresCount = Math.min(maxDisplayedScores, highScores.size());
        highScoresContainer.getChildren().clear(); // Clear previous high scores

        for (int i = 0; i < displayedScoresCount; i++) {
            HBox highScoreItem = new HBox();
            highScoreItem.setAlignment(Pos.CENTER);

            Label rank = new Label("No." + (i + 1));
            rank.setStyle("-fx-font-weight: bold;");
            Label score = new Label(highScores.get(i));
            score.setStyle("-fx-font-weight: bold;");

            highScoreItem.getChildren().addAll(rank, score);
            highScoresContainer.getChildren().add(highScoreItem);
        }
    }
}
