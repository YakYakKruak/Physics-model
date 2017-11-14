package vt.smt.Render;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import vt.smt.Physics.VectorFieldConsumer;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;


public class VectorField extends Pane implements VectorFieldConsumer {
    private Image texture;
    private List<ImageView> vectors = new LinkedList<>();
    public VectorField(){
        super();
        texture = new Image(getClass().getResourceAsStream("/res/miniArrow.png"));
    }

    // f: x, y -> _a(x, y)
    public void setField(Function<Point2D,Point2D> u){
        vectors.forEach(e->{
            // -, т.к. в физике ось oY вверх, в графике - вниз
            Point2D vector_pos = new Point2D(e.getTranslateX()+texture.getWidth()/2
                    ,+e.getTranslateY()+texture.getHeight()/2);
            // -, т.к. fx вращает по часовой
            e.setRotate(-vector_pos.angle( u.apply(vector_pos) ));
        });
    }

    @Override
    public void setFieldByAngle(Function<Point2D, Double> f_angle) {
        vectors.forEach(e->{
            Point2D vector_pos = new Point2D(e.getTranslateX()+texture.getWidth()/2.
                    ,+e.getTranslateY()+texture.getHeight()/2.);
            e.setRotate(-f_angle.apply(vector_pos));

        });
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        this.resize();
    }

    private void resize(){
        vectors.forEach(e->this.getChildren().remove(e));
        vectors.clear();
        double width  =  (float)this.getWidth ();
        double height =  (float)this.getHeight();

        double w_step = texture.getWidth()  + 8;
        double h_step = texture.getHeight() + 8;

        double i_h = 0.;
        double j_w = 0.;

        while (i_h < height){
            j_w = 0.;
            while(j_w < width){
                ImageView vector = new ImageView(texture);
                this.getChildren().add(vector);
                vector.toBack();
                vector.setTranslateX(j_w);
                vector.setTranslateY(i_h);
                vectors.add(vector);
                j_w += w_step;
            }
            i_h += h_step;
        }
    }
}
