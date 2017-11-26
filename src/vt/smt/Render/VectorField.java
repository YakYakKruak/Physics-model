package vt.smt.Render;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import vt.smt.Physics.VectorFieldConsumer;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static java.lang.Math.*;


public class VectorField extends Pane implements VectorFieldConsumer {
    private Image texture;
    private List<ImageView> vectors = new LinkedList<>();
    // Последнее переданное физическое поле (для ресайза)

    private Function<Point2D, Point2D> lastU = f->new Point2D(0.,0.);
    private double max_e = Double.MIN_VALUE;
    private double min_e = Double.MAX_VALUE;

    public VectorField(){
        super();
        texture = new Image(getClass().getResourceAsStream("/res/miniArrow.png"));
    }

    private double getAngleOfVector(Point2D p){
        return signum(p.getY())*toDegrees(acos(p.dotProduct(1.,0)/(sqrt(p.getX()*p.getX() + p.getY()*p.getY()))));
    }
    @Override
    public void setFieldByPoint(Function<Point2D, Point2D> u) {
        this.lastU = u;
        vectors.forEach(v->{
            Point2D p = u.apply(new Point2D(v.getTranslateX(), v.getTranslateY()));
            v.setRotate(getAngleOfVector(p));
        });
    }

    @Override
    public void resize(double width, double height) {
        // +340 - для стрелочек под меню. По идее, величина,
        // равная размеру меню
        super.resize     (width, height);
        this.resize_field(width + 340, height);
    }

    private void resize_field(double width, double height){
        vectors.forEach(e->this.getChildren().remove(e));
        vectors.clear();


        double w_step = texture.getWidth()  + 12;
        double h_step = texture.getHeight()+ 5;

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


                Point2D p = lastU.apply(new Point2D(vector.getTranslateX(), vector.getTranslateY()));
                vector.setRotate(getAngleOfVector(p));
                vectors.add(vector);
                j_w += w_step;
            }
            i_h += h_step;
        }
    }
}
