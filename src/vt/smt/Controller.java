package vt.smt;

import javafx.application.Platform;
import javafx.fxml.FXML;
import vt.smt.Render.VectorField;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Controller {
    @FXML private VectorField field;
    public void setField(){
        field.resize();
            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
            executor.scheduleAtFixedRate(()->{
                Platform.runLater(()->field.resize());
            },
                    0,20, TimeUnit.MILLISECONDS);
    }
}
