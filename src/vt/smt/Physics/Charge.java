package vt.smt.Physics;


import javafx.geometry.Point2D;

public class Charge {
    private double power;
    private Point2D position;

    public Charge(double power, Point2D position) {
        this.power = power;
        this.position = position;
    }
    public Charge(Charge proto){
        this(proto.getPower(), proto.getPosition());
    }
    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }
}
