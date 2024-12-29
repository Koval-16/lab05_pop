package ite.kubak.graphic;

import ite.kubak.communication.GuiUpdater;
import ite.kubak.communication.Lunchroom;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller implements GuiUpdater {

    @FXML private TextArea foodQueuesArea;
    @FXML private TextArea cashQueuesArea;
    @FXML private TextArea tablesArea;

    @FXML private TextField tableSize;

    private Lunchroom lunchroom;

    @FXML
    public void button_clicked() throws InterruptedException {
        if (lunchroom == null || !lunchroom.isAlive()) {
            lunchroom = new Lunchroom(this, getTableSize());
            lunchroom.setDaemon(true);
            lunchroom.start();
        }
    }

    @FXML
    public void switch_clicked() {
        Lunchroom.switch_status();
    }

    @FXML
    public int getTableSize(){
        return Integer.parseInt(tableSize.getText());
    }

    @Override
    public void update(String foodQueues, String cashQueues, String tables) {
        Platform.runLater(() -> {
            foodQueuesArea.setText(foodQueues);
            cashQueuesArea.setText(cashQueues);
            tablesArea.setText(tables);
        });
    }

}
