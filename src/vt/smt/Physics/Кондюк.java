package vt.smt.Physics;


import javafx.geometry.Point2D;

public class Кондюк {

    private Point2D plateCenter;
    private double plateLength;
    private double plateWidth;
    private double distance;
    private double charge;

    public Кондюк(Point2D plateCenter, double plateLength, double plateWidth, double distance, double charge) {
        this.plateCenter = plateCenter;
        this.plateLength = plateLength;
        this.plateWidth = plateWidth;
        this.distance = distance;
        this.charge = charge;
    }

    public double getPlateWidth() {
        return plateWidth;
    }

    public void setPlateWidth(double plateWidth) {
        this.plateWidth = plateWidth;
    }

    public double getPlateLength() {
        return plateLength;
    }

    public void setPlateLength(double plateLength) {
        this.plateLength = plateLength;
    }

    public Point2D getPlateCenter() {
        return plateCenter;
    }

    public void setPlateCenter(Point2D plateCenter) {
        this.plateCenter = plateCenter;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }
}
