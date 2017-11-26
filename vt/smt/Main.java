package vt.smt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

//        Point2D p = new Point2D(-1,-1);
//        System.err.println(Math.toDegrees(Math.acos(p.dotProduct(1, 0) / (sqrt(p.getX() * p.getX() + p.getY() * p.getY())))));
//        System.err.println(new Point2D(1, -2.).angle(1, 0));
        Scene scene = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}