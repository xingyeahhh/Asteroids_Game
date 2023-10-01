package Exmaple;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;


public class PaneExample extends Application {
//    @Override
//    public void start(Stage stage) throws Exception {
//        Pane pane = new Pane();
//        pane.setPrefSize(300, 200);
//        pane.getChildren().add(new Circle(60, 50, 40));
//
//        Scene scene = new Scene(pane);
//        stage.setScene(scene);
//        stage.show();
//    }
//    
//    @Override
//    public void start(Stage stage) throws Exception {
//        Pane pane = new Pane();
//        pane.setPrefSize(300, 200);
//
//        Polygon parallelogram = new Polygon(0, 0, 100, 0, 100, 50, 0, 50);//�Ă�����
//        pane.getChildren().add(parallelogram);
//
//        Scene scene = new Scene(pane);
//        stage.setScene(scene);
//        stage.show();
//    }
    
//    @Override
//    public void start(Stage stage) throws Exception {
//        Pane pane = new Pane();
//        pane.setPrefSize(300, 200);
//
//        Polygon parallelogram = new Polygon(0, 0, 100, 0, 100, 50, 0, 50);
//        parallelogram.setTranslateX(100);//ƫ��
//        parallelogram.setTranslateY(100);//ƫ��
//
//        pane.getChildren().add(parallelogram);
//
//        Scene scene = new Scene(pane);
//        stage.setScene(scene);
//        stage.show();
//    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefSize(600, 400);

        Polygon ship = new Polygon(-5, -5, 10, 0, -5, 5);
        ship.setTranslateX(300);
        ship.setTranslateY(200);
        ship.setRotate(30);//for rotating!!

        pane.getChildren().add(ship);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        
        //to control rotate for one time
        //���Y��Ƕ����ship.getRotate׌�w�������D��
        //�O��5׌�����D�ܽz��
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                ship.setRotate(ship.getRotate() - 5);
            }

            if (event.getCode() == KeyCode.RIGHT) {
                ship.setRotate(ship.getRotate() + 5);
            }
        });
        
        
        stage.show();
    }
    
    public static void main(String[] args) {//run the function above
        launch(args);
    }
}

