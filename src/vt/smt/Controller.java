package vt.smt;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vt.smt.Physics.Charge;
import vt.smt.Physics.VectorFieldCalculator;
import vt.smt.Physics.VectorFieldCalculatorImpl;
import vt.smt.Physics.Кондюк;
import vt.smt.Render.VectorField;

import java.util.LinkedList;
import java.util.List;

public class Controller {
    @FXML private VectorField field;
    @FXML private Rectangle plast1;
    @FXML private Rectangle plast2;

    @FXML private TextField inputPower;
    @FXML private TextField inputDistance;
    @FXML private TextField inputWidth;
    @FXML private TextField inputLenght;

    private Кондюк conduc;
    private ContextMenu contextMenu;
    private List<vt.smt.Render.Charge> charges = new LinkedList<>();
    private VectorFieldCalculator calculator = new VectorFieldCalculatorImpl();
    private MenuItem  add = new MenuItem("Добавить заряд");

    public void initialize(){

        conduc = new Кондюк(new Point2D(200,400),300,30,600,10E-4);

        calculator.setКондюк(conduc);
        initContextMenu();
        field.setFieldByAngle(calculator.getVectorAngleInPoint());

//        inputDistance.setOnKeyPressed(e->conduc.setDistance());
    }

    private void redraw_plasts(){
        plast1.setTranslateX(conduc.getPlateCenter().getX() - 12);
        plast1.setTranslateY(conduc.getPlateCenter().getY() - conduc.getPlateLength()/2);
        plast1.setWidth(5);
        plast1.setHeight(conduc.getPlateLength());
        plast1.setFill(Color.BLACK);

        plast2.setTranslateX(plast1.getTranslateX() + conduc.getDistance() + 12);
        plast2.setTranslateY(plast1.getTranslateY());
        plast2.setWidth(plast1.getWidth());
        plast2.setHeight(plast1.getHeight());
        field.setFieldByAngle(calculator.getVectorAngleInPoint());
    }

    private Point2D lastClick; // to set the charge after the click to a proper position

    private void initContextMenu(){
        class Костыль{
            public int x;
        }
        Костыль t1 = new Костыль();

        add.setOnAction(e->{
            Platform.runLater(()->{
                vt.smt.Physics.Charge new_phys_charge;
                new_phys_charge = new Charge(1E-4,lastClick);
                if(t1.x++ % 2 == 0)
                    new_phys_charge.setCharge(new_phys_charge.getCharge()*-1);
                vt.smt.Render.Charge newCharge = new vt.smt.Render.Charge(new_phys_charge);
                newCharge.setWhileDragging(()->Platform.runLater(()->field.setFieldByAngle(calculator.getVectorAngleInPoint())));
                charges.add(newCharge);
                calculator.addCharge(new_phys_charge);
                field.getChildren().add(charges.get(charges.size()-1));
                Platform.runLater(()->{field.setFieldByAngle(calculator.getVectorAngleInPoint());});
            });

        });
        contextMenu = new ContextMenu(add);
    }
    public void onFieldClick(MouseEvent click){

        lastClick = new Point2D(click.getSceneX(),click.getSceneY());

        if(click.getButton().equals(MouseButton.SECONDARY))
            contextMenu.show(field,click.getScreenX(),click.getScreenY());
    }


}
