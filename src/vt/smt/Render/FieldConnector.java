package vt.smt.Render;

import vt.smt.Physics.VectorFieldCalculator;
import vt.smt.Physics.VectorFieldConsumer;

public class FieldConnector {
    private VectorFieldConsumer graphic;
    private VectorFieldCalculator physics;

    public FieldConnector(VectorFieldConsumer graphic, VectorFieldCalculator physics) {
        this.graphic = graphic;
        this.physics = physics;
    }

    public VectorFieldConsumer getGraphic() {
        return graphic;
    }

    public void setGraphic(VectorFieldConsumer graphic) {
        this.graphic = graphic;
    }

    public VectorFieldCalculator getPhysics() {
        return physics;
    }

    public void setPhysics(VectorFieldCalculator physics) {
        this.physics = physics;
    }
}

