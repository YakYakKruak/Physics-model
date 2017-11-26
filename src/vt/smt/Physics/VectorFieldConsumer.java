package vt.smt.Physics;

import javafx.geometry.Point2D;
import java.util.function.Function;

public interface VectorFieldConsumer {
    void setFieldByAngle(Function<Point2D, Double> f_angle);
}

