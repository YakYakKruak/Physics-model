package vt.smt;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import vt.smt.Physics.Charge;
import vt.smt.Physics.VectorFieldCalculator;
import vt.smt.Physics.VectorFieldCalculatorImpl;
import vt.smt.Physics.Кондюк;
import vt.smt.Render.VectorField;

public class Controller {
    @FXML private VectorField field;
    @FXML private vt.smt.Render.Charge renderedCharge;
    private VectorFieldCalculator calculator = new VectorFieldCalculatorImpl();
    public void initialize(){
        calculator.setКондюк(new Кондюк(new Point2D(800,200),300,30,400,0));
        calculator.addCharge(new Charge(10E-9,new Point2D(0,0)));
        field.setFieldByAngle(e->0.);

    }
    public void onFieldClick(MouseEvent click){
            vt.smt.Physics.Charge newCharge = new Charge(1E-1,
                    new Point2D(click.getSceneX(), click.getSceneY()));
            renderedCharge.setCharge(newCharge);
            calculator.getCharges().set(0,newCharge);

           // field.setField(calculator.getField());
            field.setFieldByAngle(calculator.getVectorAngleInPoint());

    }
}
