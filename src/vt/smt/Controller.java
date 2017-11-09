package vt.smt;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import vt.smt.Physics.Charge;
import vt.smt.Physics.VectorFieldCalculator;
import vt.smt.Physics.VectorFieldCalculatorImpl;
import vt.smt.Physics.Кондюк;
import vt.smt.Render.VectorField;

public class Controller {
    @FXML private VectorField field;
    @FXML private vt.smt.Render.Charge renderedCharge;
    @FXML private vt.smt.Render.Charge renderedCharge2;

    private VectorFieldCalculator calculator = new VectorFieldCalculatorImpl();
    public void initialize(){
        calculator.setКондюк(new Кондюк(new Point2D(800,200),300,30,400,0.));
        calculator.addCharge(new Charge(10E-9,new Point2D(0,0)));
        calculator.addCharge(new Charge(-10E-9,new Point2D(0,0)));
        field.setFieldByAngle(e->0.);

    }
    public void onFieldClick(MouseEvent click){

            vt.smt.Physics.Charge newCharge = new Charge(1E-1,
                        new Point2D(click.getSceneX(), click.getSceneY()));
            if(click.getButton().equals(MouseButton.SECONDARY)) {
                System.out.println("ox");
                renderedCharge.setCharge(newCharge);
                newCharge.setCharge(newCharge.getCharge()*-1.);
                calculator.getCharges().set(0,newCharge);

            }
            else {
                renderedCharge2.setCharge(newCharge);
                calculator.getCharges().set(1,newCharge);
            }
           // field.setField(calculator.getField());
            field.setFieldByAngle(calculator.getVectorAngleInPoint());

    }
}
