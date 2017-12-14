package MapViewer.GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import com.neet.DiamondHunter.Main.Game;
//import com.neet.MapViewer.Main.MapMain;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AppControl {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;


        @FXML
        //exit the program upon clicking close
        void exitProgram(ActionEvent event) {
            Platform.setImplicitExit(true);
            //MapMain.primaryStage.hide();
        }

        @FXML
        //info method for instructions
        void info(ActionEvent event) {
            Alert alert_instruc = new Alert(AlertType.INFORMATION);
            alert_instruc.setTitle("Instruction");
            alert_instruc.setHeaderText("Instruction of map viewer");
            alert_instruc.setContentText("Put text here!");
        }

        @FXML
        void zoomInMenu(ActionEvent event) {
        }

        @FXML
        void zoomoutMenu(Event event) {
        }

        @FXML
        void initialize() {


        }

    }

