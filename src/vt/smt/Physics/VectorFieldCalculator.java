package vt.smt.Physics;

import javafx.geometry.Point2D;

import java.util.List;
import java.util.function.Function;

public interface VectorFieldCalculator {
    // f point1 -> point2 - вектор напряжённости в данной точки
    Function<Point2D,Point2D> getField();
    void addCharge(Charge charge);
    void setКондюк(Кондюк c);
    List<Charge> getCharges();
}
