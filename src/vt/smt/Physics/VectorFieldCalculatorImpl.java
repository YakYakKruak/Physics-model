package vt.smt.Physics;

import javafx.geometry.Point2D;
import sun.misc.Unsafe;

import java.lang.annotation.Native;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.lang.Math.*;

public class VectorFieldCalculatorImpl implements VectorFieldCalculator {

    private List<Charge> charges;
    private Кондюк кондюк;

    public VectorFieldCalculatorImpl() {
        charges = new ArrayList<>();
    }
    private final double PIXELS_IN_METR = 100_000;

    @Override
    public Function<Point2D, Point2D> getField() {
        final double epsilon_0 = 8.85E-12;
        return point2D -> {
            Double x= 0.;
            Double y= 0.;
            for (Charge e : charges) {
                Double delta_x =  point2D.getX() - e.getPosition().getX();
                Double delta_y =  point2D.getY() - e.getPosition().getY();
                Double r_2 = delta_x*delta_x + delta_y*delta_y;
                x +=  delta_x*e.getCharge()/(4*PI*epsilon_0*r_2);
                y +=  delta_y*e.getCharge()/(4*PI*epsilon_0*r_2);
            }
//            if(point2D.getX() - кондюк.getPlateCenter().getX() < кондюк.getDistance() &&
//                    кондюк.getPlateCenter().getX()<point2D.getX()
//                    && abs(point2D.getY() - кондюк.getPlateCenter().getY())< кондюк.getPlateLength()/2) {
//                x += кондюк.getCharge()/(кондюк.getPlateLength()* кондюк.getPlateWidth())/epsilon_0;
//            }

            return new Point2D(x,y);
        };
    }

    @Deprecated
    @Override
    public Function<Point2D, Double> getVectorAngleInPoint() {
        Function<Point2D, Point2D > f = getField();
        return point2D -> {
            Point2D p = f.apply(point2D);
            return -signum(p.getY())*toDegrees(acos(p.dotProduct(1.,0)/(sqrt(p.getX()*p.getX() + p.getY()*p.getY()))));
        };
    }

    @Override
    public void addCharge(Charge charge) {
        charges.add(charge);
    }

    public List<Charge> getCharges() {
        return charges;
    }

    @Override
    public void setКондюк(Кондюк c) {
        this.кондюк = c;
    }
}
