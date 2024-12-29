module gui {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires lunchroom;
    opens ite.kubak.graphic to javafx.graphics, javafx.fxml;
}