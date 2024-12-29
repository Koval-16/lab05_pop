package ite.kubak.graphic;

import ite.kubak.communication.Lunchroom;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    Lunchroom lunchroom = new Lunchroom();

    @FXML
    private Button button;

    @FXML
    public void button_clicked() throws InterruptedException {
        lunchroom.start();
    }

    @FXML
    public void switch_clicked(){
        Lunchroom.switch_status();
    }

}
