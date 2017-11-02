package vt.smt.Physics;

import javafx.geometry.Point2D;
import java.util.function.Function;

public interface VectorFieldConsumer {
    void setField(Function<Point2D,Point2D> u);
}

