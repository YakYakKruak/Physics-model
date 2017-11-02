package vt.smt;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import vt.smt.Physics.Charge;
import vt.smt.Physics.VectorFieldCalculator;
import vt.smt.Physics.VectorFieldCalculatorImpl;
import vt.smt.Physics.Кондюк;
import vt.smt.Render.FieldConnector;
import vt.smt.Render.VectorField;

import java.util.function.Function;

public class Controller {
    @FXML private VectorField field;
    FieldConnector fieldConnector = new FieldConnector(field,new VectorFieldCalculatorImpl());
    private VectorFieldCalculator calculator = new VectorFieldCalculatorImpl();
    public void setField(){
        field.resize();
        calculator.setКондюк(new Кондюк(new Point2D(200,200),300,30,400,1E-9));
        calculator.addCharge(new Charge(10E-9,new Point2D(100,700)));
        field.setField(calculator.getField());
     //   field.setField(point2D -> new Point2D(point2D.getX()/2.718281828459045235360, point2D.getY()*155));
//            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
//            executor.scheduleAtFixedRate(()->{
//                Platform.runLater(()->field.resize());
//            },
//                    0,20, TimeUnit.MILLISECONDS);
    }
}
