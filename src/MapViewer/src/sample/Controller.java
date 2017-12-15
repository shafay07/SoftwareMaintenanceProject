package MapViewer.src.sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label axePosition;

    @FXML
    private Label boatPosition;

    @FXML
    private Label cursorPosition;

    @FXML
    private Label information;


    @FXML
    void aboutInfo(ActionEvent event) {

    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void handleKeyAction(KeyEvent event) {

    }

    @FXML
    void handleSetPosition(KeyEvent event) {

    }

    @FXML
    void helpInfo(ActionEvent event) {

    }


    @FXML
    void zoomInFromMenu(ActionEvent event) {

    }

    @FXML
    void zoomOutFromMenu(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert axePosition != null : "fx:id=\"axePosition\" was not injected: check your FXML file 'Structure.fxml'.";
        assert boatPosition != null : "fx:id=\"boatPosition\" was not injected: check your FXML file 'Structure.fxml'.";
        assert cursorPosition != null : "fx:id=\"cursorPosition\" was not injected: check your FXML file 'Structure.fxml'.";
        assert information != null : "fx:id=\"information\" was not injected: check your FXML file 'Structure.fxml'.";


    }

}
