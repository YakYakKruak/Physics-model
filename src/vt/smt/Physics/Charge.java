package vt.smt.Physics;


import javafx.geometry.Point2D;

public class Charge {
    private double charge;
    private Point2D position;

    public Charge(double charge, Point2D position) {
        this.charge = charge;
        this.position = position;
    }
    public Charge(Charge charge){
        this(charge.getCharge(),charge.getPosition());
    }
    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }
}
