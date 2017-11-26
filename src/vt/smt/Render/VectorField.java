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
    // Последнее переданное физическое поле (для ресайза)
    private Function<Point2D, Double> lastU = f->0.;

    public VectorField(){
        super();
        texture = new Image(getClass().getResourceAsStream("/res/miniArrow.png"));
    }

    @Override
    public void setFieldByAngle(Function<Point2D, Double> f_angle) {
        lastU = f_angle;
        vectors.forEach(e->{
            Point2D vector_pos = new Point2D(e.getTranslateX()+texture.getWidth()/2
                    ,                       +e.getTranslateY()+texture.getHeight()/2);
            e.setRotate(-f_angle.apply(vector_pos));
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


                vector.setRotate(-lastU.apply(new Point2D(vector.getTranslateX()+texture.getWidth()/2,
                        vector.getTranslateY()+texture.getHeight()/2)));

                vectors.add(vector);
                j_w += w_step;
            }
            i_h += h_step;
        }
    }
}
