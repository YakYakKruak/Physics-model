package vt.smt.Render;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import  vt.smt.Physics.Charge;

public class Fleck extends Pane {
    private double power;
    private ImageView rendered;
    private Runnable whileDragging;

    public Fleck() {

        rendered = new ImageView();
        power = 1.;
        loadImage(true);
        this.getChildren().add(rendered);

       // setCharge(new vt.smt.Physics.Charge(1, new Point2D(0, 0)));

        rendered.setLayoutX(-rendered.getBoundsInLocal().getWidth() / 2.);
        rendered.setLayoutY(-rendered.getBoundsInLocal().getHeight() / 2.);

        this.setOnMouseDragged(event -> {
            rendered.setTranslateX(event.getX());
            rendered.setTranslateY(event.getY());

            if (whileDragging != null)
                whileDragging.run();
        });


    }


    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        if(this.power != power)
            loadImage(power >= 0);
        this.power = power;
    }

    public Fleck(Charge charge) {
        this();
        setTranslateX(charge.getPosition().getX());
        setTranslateY(charge.getPosition().getY());
        setPower(charge.getPower());
    }

    private void loadImage(boolean positive_charge) {
        if (positive_charge)
            rendered.setImage(new Image(getClass().getResourceAsStream("/res/plusE.png")));
        else
            rendered.setImage(new Image(getClass().getResourceAsStream("/res/minusE.png")));
    }

//    public vt.smt.Physics.Charge getCharge() {
//        return e;
//    }

//    public void setCharge(vt.smt.Physics.Charge e) {
//        this.power = e.getPower();
//        rendered.setTranslateX(e.getPosition().getX());
//        rendered.setTranslateY(e.getPosition().getY());
//        loadImage(e.getPower() >= 0);
//    }

    public void setWhileDragging(Runnable callback) {
        this.whileDragging = callback;
    }

}
