package vt.smt;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import vt.smt.Physics.Charge;
import vt.smt.Physics.VectorFieldCalculator;
import vt.smt.Physics.VectorFieldCalculatorImpl;
import vt.smt.Physics.Кондюк;
import vt.smt.Render.VectorField;

import java.util.LinkedList;
import java.util.List;

public class Controller {
    @FXML private VectorField field;
    private ContextMenu contextMenu;
    private List<vt.smt.Render.Charge> charges = new LinkedList<>();
    private VectorFieldCalculator calculator = new VectorFieldCalculatorImpl();
    public void initialize(){
        calculator.setКондюк(new Кондюк(new Point2D(800,200),300,30,400,0.));
        field.setFieldByAngle(e->90.);
        initContextMenu();
    }
    private Point2D lastClick; // to set the charge after the click to a proper position
    private void initContextMenu(){
        MenuItem add = new MenuItem("Добавить заряд");
        add.setOnAction(e->{
            charges.add(new vt.smt.Render.Charge(new Charge(1E-10,lastClick)));
            calculator.addCharge(new Charge(1E-10,lastClick));
            field.getChildren().add(charges.get(charges.size()-1));
            field.setFieldByAngle(calculator.getVectorAngleInPoint());
            Platform.runLater(()->field.setFieldByAngle(e4->90.));
            System.out.println(calculator.getVectorAngleInPoint());
        });
        contextMenu = new ContextMenu(add);
    }
    public void onFieldClick(MouseEvent click){
        lastClick = new Point2D(click.getSceneX(),click.getSceneY());

        if(click.getButton().equals(MouseButton.SECONDARY))
            contextMenu.show(field,click.getScreenX(),click.getScreenY());
    }
}
