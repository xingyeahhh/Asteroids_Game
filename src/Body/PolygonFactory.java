//package Body;
//
//import java.util.Random;
//import javafx.scene.shape.Polygon;
//
//public class PolygonFactory {
//
//    public Polygon createPolygon() {
//        Random rnd = new Random();
//
//        double size = 10 + rnd.nextInt(10);
//
//        Polygon polygon = new Polygon();
//        double c1 = Math.cos(Math.PI * 2 / 5);
//        double c2 = Math.cos(Math.PI / 5);
//        double s1 = Math.sin(Math.PI * 2 / 5);
//        double s2 = Math.sin(Math.PI * 4 / 5);
//
//        polygon.getPoints().addAll(
//            size, 0.0,
//            size * c1, -1 * size * s1,
//            -1 * size * c2, -1 * size * s2,
//            -1 * size * c2, size * s2,
//            size * c1, size * s1);
//
//        for (int i = 0; i < polygon.getPoints().size(); i++) {
//            int change = rnd.nextInt(5) - 2;
//            polygon.getPoints().set(i, polygon.getPoints().get(i) + change);
//        }
//
//        return polygon;
//    }
//}

package Body;

import java.util.Random;
import javafx.scene.shape.Polygon;

public class PolygonFactory {

    public Polygon createPolygon() {
        Random rnd = new Random();

        int numOfSides = 5 + rnd.nextInt(3); //Randomly select the number of edges (5 to 8)
        double size = 10 + rnd.nextInt(10); //Change the size range of polygons (10 to 40)

        Polygon polygon = new Polygon();

        for (int i = 0; i < numOfSides; i++) {
            double angle = 2 * Math.PI * i / numOfSides;
            double x = size * Math.cos(angle);
            double y = size * Math.sin(angle);

            //The position of random change points
            int changeX = rnd.nextInt(5) - 2;
            int changeY = rnd.nextInt(5) - 2;
            polygon.getPoints().addAll(x + changeX, y + changeY);
        }
        return polygon;
    }
}