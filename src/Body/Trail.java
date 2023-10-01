package Body;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Trail extends Circle {
    private static final Random RANDOM = new Random();
    
    // Define the colors
    final Color red = Color.RED;
    final Color orange = Color.ORANGE;
    
    public Trail(double x, double y, double minRadius, double maxRadius) {
        super(x, y, getRandomRadius(minRadius, maxRadius));

        // Randomly select an interpolation factor between 0 and 1
        double interpolationFactor = RANDOM.nextDouble();

        // Interpolate between the colors based on the random factor
        Color mixedColor = red.interpolate(orange, interpolationFactor);

        setFill(mixedColor);
    }

    private static double getRandomRadius(double minRadius, double maxRadius) {
        // Calculate a random radius within the specified range
        return minRadius + (maxRadius - minRadius) * RANDOM.nextDouble();
    }

    public void fadeOut(Pane pane, double durationInSeconds) {
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(this.opacityProperty(), 0);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(durationInSeconds), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        timeline.setOnFinished(event -> pane.getChildren().remove(this));
    }
}
