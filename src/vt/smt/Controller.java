package vt.smt;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.shape.Rectangle;
import vt.smt.Physics.Charge;
import vt.smt.Physics.VectorFieldCalculator;
import vt.smt.Physics.VectorFieldCalculatorImpl;
import vt.smt.Physics.Кондюк;
import vt.smt.Render.VectorField;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.max;

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
    private TextField inputLenght;

    @FXML //  fx:id="fruitCombo"
    private ComboBox<String> chargeCombo; // Value injected by FXMLLoader

    private Кондюк conduc;

    private ContextMenu contextMenu = null;
    private List<vt.smt.Render.Charge> charges = new LinkedList<>();
    private VectorFieldCalculator calculator = new VectorFieldCalculatorImpl();
    private double plastCharge = 1E-9;

    // Коэффициент - 'максимальное значение напряжённости'
    private double fieldOpacityFactor = 8.85*10E-10;
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private double k = 1E-9;

    public void initialize(){

        conduc = new Кондюк(new Point2D(200,385),14,50,22,plastCharge*k);
        chargeCombo.setOnAction((event) -> {
            conduc.setCharge(conduc.getCharge()/k);
            String s = chargeCombo.getSelectionModel().getSelectedItem();
            switch (s) {
                case "пКл": {
                    k = 1E-12;
                    chargeCombo.setPromptText("aaaaa");
                }
                    break;
                case "нКл":
                    k = 1E-9;
                    break;
                case "мкКл":
                    k = 1E-6;
                    break;
                default:
                    k = 1E-3;
                    break;
            }
            conduc.setCharge(conduc.getCharge()*k);
        });

        calculator.setКондюк(conduc);
        initContextMenu();
        field.setFieldByPoint(calculator.getField());
        inputDistance.setOnKeyReleased(e-> {
            try{
                conduc.setDistance(Double.parseDouble(inputDistance.getText()));
                redrawPlasts();
            } catch (NumberFormatException nfe) {}
        });

        inputLenght.setOnKeyReleased( event -> {
            try{
                conduc.setPlateLength(Double.parseDouble(inputLenght.getText()));
            } catch (NumberFormatException nfe) {}
            redrawPlasts();
        });

        inputPower.setOnKeyReleased( event -> {
            try{
                conduc.setCharge(Double.parseDouble(inputPower.getText())*k);
            } catch (NumberFormatException nfe) {}
            redrawPlasts();
        });

        redrawPlasts();
    }

    private void redrawPlasts(){
        plast1.setTranslateX(conduc.getPlateCenter().getX() + 12);
        plast1.setTranslateY(conduc.getPlateCenter().getY() - conduc.getPlateLength()*37.5/2);
        plast1.setWidth(7);
        plast1.setHeight(conduc.getPlateLength()*37.5);

        plast2.setTranslateX(plast1.getTranslateX() + conduc.getDistance()*37.795 - 15);
        plast2.setTranslateY(conduc.getPlateCenter().getY() - conduc.getPlateLength()*37.5/2);
        plast2.setWidth(plast1.getWidth());
        plast2.setHeight(plast1.getHeight());
        calculator.setКондюк(conduc);
        field.setFieldByPoint(calculator.getField());
    }

    private Point2D lastClick; // to set the charge after the click to a proper position

    public void addNegative(){
        addCharge(-1E-9);
    }
    public void addPositive(){
        addCharge(1E-9);
    }
    private void addCharge(double value){
        Platform.runLater(()->{
            Charge new_phys_charge;
            new_phys_charge = new Charge(value,lastClick);
            vt.smt.Render.Charge newCharge = new vt.smt.Render.Charge(new_phys_charge);
            newCharge.setWhileDragging(()->Platform.runLater(()->field.setFieldByPoint(calculator.getField())));
            charges.add(newCharge);
            calculator.addCharge(new_phys_charge);
            field.getChildren().add(charges.get(charges.size()-1));
            field.setMax_e(max (field.getMax_e(),
                    abs(new_phys_charge.getCharge())/fieldOpacityFactor)
            );
            Platform.runLater(()->field.setFieldByPoint(calculator.getField()));

        });
    }

    private void initContextMenu(){
        if(contextMenu == null)
            Platform.runLater(()->{
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("addChargeMenu.fxml"));
                    loader.setController(this);
                    contextMenu = loader.load();
                }catch (IOException io){
                    System.err.println("ЭТО КАПЕЦ, ЗДЕСЬ НЕТ ФХМЛ ДЛЯ МЕНЮ ДОБАВЛЕНИЯ ЗАРЯДА.");
                    io.printStackTrace();
                }
            });
    }

    public void onFieldClick(ContextMenuEvent click){
        lastClick = new Point2D(click.getSceneX(),click.getSceneY());
        contextMenu.show(field,click.getScreenX(),click.getScreenY());
    }

}

