package vt.smt.Render;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Created by semitro on 03.11.17.
 */
public class Charge  extends Pane {
    private vt.smt.Physics.Charge e;
    private ImageView rendered;

    public Charge(){
        rendered = new ImageView();
        this.getChildren().add(rendered);
        setCharge(new vt.smt.Physics.Charge(1,new Point2D(0,0)));

        rendered.setLayoutX(-rendered.getBoundsInLocal().getWidth()/2.);
        rendered.setLayoutY(-rendered.getBoundsInLocal().getHeight()/2.);
        this.setOnMouseDragged(event -> {
            rendered.setTranslateX(event.getX());
            rendered.setTranslateY(event.getY());
            e.setPosition(new Point2D(event.getX(),event.getY()));

            whileDragging.run();
        });


    }

    public Charge(vt.smt.Physics.Charge charge){
        this();
        setCharge(charge);
        loadImage(charge.getCharge() >= 0);
    }

    private void loadImage(boolean positive_charge){
        if (positive_charge) {
            rendered.setImage(new Image(getClass().getResourceAsStream("/res/plusE.png")));
            Platform.runLater(()-> {
                InnerShadow sh = new InnerShadow(5, Color.RED);
                DropShadow dh =new DropShadow(100, Color.RED);
                dh.setSpread(0.9);
                sh.setInput(dh);
                rendered.setEffect(sh);
            });
        }
        else {
            rendered.setImage(new Image(getClass().getResourceAsStream("/res/minusE.png")));
            Platform.runLater(()->{
                InnerShadow sh = new InnerShadow(5, Color.AQUA);
                DropShadow dh = new DropShadow(100, Color.BLUE);
                dh.setSpread(0.9);
                sh.setInput(dh);

                rendered.setEffect(sh);
            });
        }


    }
    public vt.smt.Physics.Charge getCharge() {
        return e;
    }

    public void setPosition(Point2D position){
        e.setPosition(position);
        setCharge(e);
    }
    public void setCharge(vt.smt.Physics.Charge e) {
        this.e = e;
        rendered.setTranslateX( e.getPosition().getX());
        rendered.setTranslateY( e.getPosition().getY());
        loadImage(e.getCharge() >= 0);
    }
    private Runnable whileDragging;

    public void setWhileDragging(Runnable callback){
        this.whileDragging = callback;
    }

}
