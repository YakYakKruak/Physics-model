package vt.smt.Physics;

import static java.lang.Math.*;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class VectorFieldCalculatorImpl implements VectorFieldCalculator {

    private List<Charge> charges;
    private Кондюк conder;

    public VectorFieldCalculatorImpl() {
        charges = new ArrayList<>();
    }

    @Override
    public Function<Point2D, Point2D> getField() {
        final double epsilon_0 = 8.85E-12;
        return point2D -> {
            double x= 0;
            double y= 0;
            for (Charge c : charges) {
                x += c.getCharge()/pow((c.getPosition().getX() - point2D.getX()),2)/(4*PI*epsilon_0);
                y += c.getCharge()/pow((c.getPosition().getY() - point2D.getY()),2)/(4*PI*epsilon_0);
            }
            if(point2D.getX()-conder.getPlateCenter().getX() < conder.getDistance() &&
                    conder.getPlateCenter().getX()<point2D.getX()
                    && abs(point2D.getY() - conder.getPlateCenter().getY())<conder.getPlateLength()/2) {
                x+=conder.getCharge()/(conder.getPlateLength()*conder.getPlateWidth())/epsilon_0;
            }

            return new Point2D(x,y);
        };
    }

    @Override
    public void addCharge(Charge charge) {
        charges.add(charge);
    }

    @Override
    public void setКондюк(Кондюк c) {
        this.conder = c;
    }
}
