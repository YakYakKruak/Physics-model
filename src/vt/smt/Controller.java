package vt.smt;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
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

    @FXML
    private VectorField field;

    @FXML
    private Rectangle plast1;

    @FXML
    private Rectangle plast2;

    @FXML
    private TextField inputPower;

    @FXML
    private TextField inputDistance;

    @FXML
    private TextField inputWidth;

    @FXML
    private TextField inputLenght;


    @FXML //  fx:id="fruitCombo"
    private ComboBox<String> fruitCombo; // Value injected by FXMLLoader

    private Кондюк conduc;
    private ContextMenu contextMenu;
    private List<vt.smt.Render.Charge> charges = new LinkedList<>();
    private VectorFieldCalculator calculator = new VectorFieldCalculatorImpl();
    private MenuItem  add = new MenuItem("Добавить заряд");
    private double k = 1E-9;
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    public void initialize(){
        conduc = new Кондюк(new Point2D(200,400),10,30,10,k);
        calculator.setКондюк(conduc);
        initContextMenu();
        field.setFieldByPoint(calculator.getField());
        inputDistance.setOnKeyReleased(e-> {
            try{
                conduc.setDistance(Double.parseDouble(inputDistance.getText()));
                redrawPlasts();
            } catch (NumberFormatException nfe) {

            }
        });

        inputLenght.setOnKeyReleased( event -> {
            try{
                conduc.setPlateLength(Double.parseDouble(inputLenght.getText()));
            } catch (NumberFormatException nfe) {

            }

            redrawPlasts();
        });

        inputPower.setOnKeyReleased( event -> {
            try{

                conduc.setCharge(Double.parseDouble(inputPower.getText())*k);
            } catch (NumberFormatException nfe) {
//                alert.setContentText("Некорректный ввод");
//                alert.showAndWait();
            }

            redrawPlasts();
        });

        redrawPlasts();
    }

    private void redrawPlasts(){
        plast1.setTranslateX(conduc.getPlateCenter().getX() - 12);
        plast1.setTranslateY(conduc.getPlateCenter().getY() - conduc.getPlateLength()*37.5/2);
        plast1.setWidth(5);
        plast1.setHeight(conduc.getPlateLength()*37.5);
        plast1.setFill(Color.BLACK);

        plast2.setTranslateX(plast1.getTranslateX() + conduc.getDistance()*37.795);
        plast2.setTranslateY(conduc.getPlateCenter().getY() - conduc.getPlateLength()*37.5/2);
        plast2.setWidth(plast1.getWidth());
        plast2.setHeight(plast1.getHeight());
        calculator.setКондюк(conduc);
        field.setFieldByPoint(calculator.getField());
    }

    private Point2D lastClick; // to set the charge after the click to a proper position

    private void initContextMenu(){
        class Костыль{
            public int x;
        }
        Костыль t1 = new Костыль();

        add.setOnAction(e->{
            Platform.runLater(()->{
                Charge new_phys_charge;
                new_phys_charge = new Charge(6E-9,lastClick);
                if(t1.x++ % 2 == 0)
                    new_phys_charge.setCharge(new_phys_charge.getCharge()*-1);
                vt.smt.Render.Charge newCharge = new vt.smt.Render.Charge(new_phys_charge);
                newCharge.setWhileDragging(()->Platform.runLater(()->field.setFieldByPoint(calculator.getField())));

                charges.add(newCharge);
                calculator.addCharge(new_phys_charge);
                field.addChargeToSkipEIn(newCharge);
                field.getChildren().add(charges.get(charges.size()-1));
                Platform.runLater(()->field.setFieldByPoint(calculator.getField()));
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
