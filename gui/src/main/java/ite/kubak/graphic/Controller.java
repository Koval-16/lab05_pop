package ite.kubak.graphic;

import ite.kubak.communication.GuiUpdater;
import ite.kubak.communication.Lunchroom;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller implements GuiUpdater {

    @FXML private TextArea foodQueuesArea;
    @FXML private TextArea cashQueuesArea;
    @FXML private TextArea tablesArea;
    @FXML private Button start_button;
    @FXML private Button switch_button;

    @FXML private TextField tableSize;

    private Lunchroom lunchroom;

    @FXML
    public void button_clicked(){
        if (lunchroom == null || !lunchroom.isAlive()) {
            try{
                lunchroom = new Lunchroom(this, getTableSize());
                lunchroom.setDaemon(true);
                lunchroom.start();
                tableSize.setDisable(true);
                start_button.setDisable(true);
                switch_button.setDisable(false);
            } catch (Exception e){}

        }
    }

    @FXML
    public void switch_clicked() {
        Lunchroom.switch_status();
        if(switch_button.getText().equals("ZAMKNIJ STOŁÓWKĘ")) switch_button.setText("OTWÓRZ STOŁÓWKĘ");
        else switch_button.setText("ZAMKNIJ STOŁÓWKĘ");
    }

    @FXML
    public int getTableSize(){
        int size;
        try{
            size = Integer.parseInt(tableSize.getText());
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Nieprawidłowa wartość");
            alert.setContentText("Proszę wpisać liczbę całkowitą.");
            alert.showAndWait();
            throw new IllegalArgumentException("Wprowadzona wartość nie jest liczbą całkowitą.");
        }
        return size;
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
