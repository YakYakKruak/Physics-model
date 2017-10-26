package vt.smt.Render;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by semitro on 27.10.17.
 */
public class VectorField extends Pane {
    private Image texture;
    private List<ImageView> vectors = new LinkedList<>();
    public VectorField(){
        super();
        texture = new Image(getClass().getResourceAsStream("/res/arrow.png"));
        for(int i = 0; i < 15; i++) {
            ImageView vector = new ImageView(texture);
            vector.setRotate(Math.random()*180);
            vector.setTranslateX(Math.random()*800);
            vector.setTranslateY(Math.random()*400);
            this.getChildren().add(vector);
            vectors.add(vector);
        }
    }
    // f: x, y -> _a(x, y)
    public void setField(Function<Point2D,Point2D> u){

    }
}
