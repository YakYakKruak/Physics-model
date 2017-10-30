package vt.smt;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import vt.smt.Render.VectorField;

import java.util.function.Function;

public class Controller {
    @FXML private VectorField field;
    public void setField(){
        field.resize();
        field.setField(new Function<Point2D, Point2D>() {
            @Override
            public Point2D apply(Point2D point2D) {
                return new Point2D(point2D.getX()/2.718281828459045235360, point2D.getY()*155);
            }
        });
//            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
//            executor.scheduleAtFixedRate(()->{
//                Platform.runLater(()->field.resize());
//            },
//                    0,20, TimeUnit.MILLISECONDS);
    }
}
