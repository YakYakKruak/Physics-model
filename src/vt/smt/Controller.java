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
    @FXML private vt.smt.Render.Charge prototype_positive; // Заряды-меню внизу
    @FXML private vt.smt.Render.Charge prototype_negative;

    private ContextMenu contextMenu;
    private List<vt.smt.Render.Charge> charges = new LinkedList<>();
    private VectorFieldCalculator calculator = new VectorFieldCalculatorImpl();
    private MenuItem  add = new MenuItem("Добавить заряд");
    public void initialize(){
        calculator.setКондюк(new Кондюк(new Point2D(800,200),300,30,400,0.));
        initContextMenu();
       init_prototypes();

    }
    private void init_prototypes(){
        Platform.runLater(()->{
            prototype_negative.setCharge( new vt.smt.Physics.Charge(-10,new Point2D(0,0)));
            prototype_positive.setCharge( new vt.smt.Physics.Charge(10,new Point2D(0,0)) );
            Runnable on_drag_done = ()->{
                System.out.println("back");
            };
            prototype_positive.setOnDragDone(e->on_drag_done.run());
            prototype_positive.setWhileDragging(()->{
            });
        });

    }
    private Point2D inital_prototype_1_pos; // Изначальные положения зарядов - меню внизу
    private Point2D inital_prototype_2_pos;

    private Point2D lastClick; // to set the charge after the click to a proper position

    private void initContextMenu(){
        class Костыль{
            public int x;
        }
        Костыль t1 = new Костыль();
        
        add.setOnAction(e->{
            Platform.runLater(()->{
                vt.smt.Physics.Charge new_phys_charge;
                new_phys_charge = new Charge(1E-8,lastClick);
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
