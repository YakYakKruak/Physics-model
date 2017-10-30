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
   // private List<ImageView> vectors = new LinkedList<>();
    private List<ImageView> vectors = new LinkedList<>();
    public VectorField(){
        super();
        texture = new Image(getClass().getResourceAsStream("/res/arrow.png"));
    }

    // f: x, y -> _a(x, y)
    public void setField(Function<Point2D,Point2D> u){
//        float height =  (float)this.getHeight() / SIZE;
//        float width  =  (float)this.getWidth () / SIZE;
//        float h = 0, w = 0;
//        for(int i = 0; i < SIZE; i++, h += height, w = 0f)
//            for (int j = 0; j < SIZE; j++, w += width, h = 0f) {
//                vectors[i][j].setTranslateY(h);
//                vectors[i][j].setTranslateX(w);
//            }

    }
    public void resize(){
        vectors.forEach(e->this.getChildren().remove(e));
        vectors.clear();
        double width  =  (float)this.getWidth ();
        double height =  (float)this.getHeight();

        double w_step = texture.getWidth()  + 5;
        double h_step = w_step;

        double i_h = 0.;
        double j_w = 0.;

        while (i_h < height){
            j_w = 0.;
            while(j_w < width){
                ImageView vector = new ImageView(texture);
                this.getChildren().add(vector);
                vector.setTranslateX(j_w);
                vector.setTranslateY(i_h);
                vectors.add(vector);
                vector.setRotate(Math.random()*360);
                j_w += w_step;
            }
            i_h += h_step;
        }
    }
}
