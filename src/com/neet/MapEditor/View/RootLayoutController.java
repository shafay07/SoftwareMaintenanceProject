package com.neet.MapEditor.View;

import com.neet.DiamondHunter.Main.Game;
import com.neet.MapEditor.Main.MapMain;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class RootLayoutController implements EventHandler<KeyEvent>{

    @FXML
    private Label cursorPos;

    @FXML
    private Label info;

    @FXML
    private Label axePos;

    @FXML
    private Label boatPos;

    @FXML
    @Override
    public void handle(KeyEvent e) {
        if(!MapMain.tileMapEditor.cursorColor){
            info.setText("A for Axe, B for Boat");
        }

        if(e.getCode() == KeyCode.I){
            // Zoom in the the 'I' key is pressed
            MapMain.tileMapEditor.zoomInImage();
        } else if(e.getCode() == KeyCode.O){
            // Zoom out when the 'O' key is pressed
            MapMain.tileMapEditor.zoomOutImage();
        } else if(e.getCode() == KeyCode.UP){
            // Move cursor up when 'W' key is pressed
            MapMain.tileMapEditor.cursorUp();
        } else if (e.getCode() == KeyCode.DOWN) {
            // Move cursor down when 'S' key is pressed
            MapMain.tileMapEditor.cursorDown();
            updateCursorPos();
        } else if (e.getCode() == KeyCode.LEFT) {
            // Move cursor to the left when 'A' key is pressed
            MapMain.tileMapEditor.cursorLeft();
            updateCursorPos();
        } else if (e.getCode() == KeyCode.RIGHT) {
            // Move cursor to the right when 'D' key is pressed
            MapMain.tileMapEditor.cursorRight();
            updateCursorPos();
        } else if (e.getCode() == KeyCode.A) {
            // Place axe when 'A' key is pressed
            MapMain.tileMapEditor.turningOnCursorColor();
            info.setText("Placing Axe");
        } else if (e.getCode() == KeyCode.B) {
            // Place boat when 'B' key is pressed
            MapMain.tileMapEditor.turningOnCursorColor();
            info.setText("Placing Boat");
        } else if (e.getCode() == KeyCode.ENTER) {
            MapMain.primaryStage.hide();
            Game.main(null);
        }
    }

    @FXML
    private void setObjectPosition(KeyEvent e){
        int temp;
        if (e.getCode() == KeyCode.A) {
            temp = MapMain.tileMapEditor.handleSetAxeRequest();
            if (temp == 1) {
                info.setText("Oops, position unavaliable!");
            }
            else if (temp == 2) {
                info.setText("Axe pos updated!");
                axePos.setText("Axe pos: (" + MapMain.tileMapEditor.cursor.cursorRows + ", " + MapMain.tileMapEditor.cursor.cursorColumns + ")");
            }
            else if (temp == 0) {
                info.setText("Axe done!");
                axePos.setText("Axe pos: (" + MapMain.tileMapEditor.cursor.cursorRows + ", " + MapMain.tileMapEditor.cursor.cursorColumns + ")");
            }
        }

        else if (e.getCode() == KeyCode.B) {
            temp = MapMain.tileMapEditor.handleSetBoatRequest();
            if (temp == 1) {
                info.setText("Position invalid!");
            }
            else if (temp == 2) {
                info.setText("Boat pos updated!");
                boatPos.setText("Boat: (" + MapMain.tileMapEditor.cursor.cursorRows + ", " + MapMain.tileMapEditor.cursor.cursorColumns + ")");
            }
            else if (temp == 0) {
                info.setText("Boat done!");
                boatPos.setText("Boat: (" + MapMain.tileMapEditor.cursor.cursorRows + ", " + MapMain.tileMapEditor.cursor.cursorColumns + ")");
            }
        }
    }

    @FXML
    private void exit(){
        Platform.setImplicitExit(true);
        MapMain.primaryStage.hide();
    }

    @FXML
    private void zoomIn() {
        MapMain.tileMapEditor.zoomInImage();
    }

    @FXML
    private void zoomOut() {
        MapMain.tileMapEditor.zoomOutImage();
    }

    @FXML
    private void help(){
        // Display help information dialog
    }

    @FXML
    private void about(){
        // Display information about the program
    }

    private void updateCursorPos(){
        cursorPos.setText("(" + MapMain.tileMapEditor.cursor.cursorRows + ", " + MapMain.tileMapEditor.cursor.cursorColumns + ")");
    }
}
