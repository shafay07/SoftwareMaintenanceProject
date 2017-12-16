package com.neet.MapEditor.View;

import com.neet.DiamondHunter.Main.Game;
import com.neet.MapEditor.Main.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class RootLayoutController {

    @FXML
    private Label cursorPos;

    @FXML
    private Label info;

    @FXML
    private Label axePos;

    @FXML
    private Label boatPos;



    @FXML
    private void handleKeyPressEvent(KeyEvent e){
        if(Main.tileMapEditor.cursorColor == false){
            info.setText("A for Axe, B for Boat");
        }

        if(e.getCode() == KeyCode.I){
            // Zoom in the the 'I' key is pressed
            Main.tileMapEditor.zoomInImage();
        } else if(e.getCode() == KeyCode.O){
            // Zoom out when the 'O' key is pressed
            Main.tileMapEditor.zoomOutImage();
        } else if(e.getCode() == KeyCode.W){
            // Move cursor up when 'W' key is pressed
            System.out.println("W key pressed");
            Main.tileMapEditor.cursorUp();
        } else if (e.getCode() == KeyCode.S) {
            // Move cursor down when 'S' key is pressed
            System.out.println("S key pressed");
            Main.tileMapEditor.cursorDown();
            updateCursorPos();
        } else if (e.getCode() == KeyCode.A) {
            // Move cursor to the left when 'A' key is pressed
            System.out.println("A key pressed");
            Main.tileMapEditor.cursorLeft();
            updateCursorPos();
        } else if (e.getCode() == KeyCode.D) {
            // Move cursor to the right when 'D' key is pressed
            System.out.println("D key pressed");
            Main.tileMapEditor.cursorRight();
            updateCursorPos();
        } else if (e.getCode() == KeyCode.A) {
            // Place axe when 'A' key is pressed
            Main.tileMapEditor.turningOnCursorColor();
            info.setText("Placing Axe");
        } else if (e.getCode() == KeyCode.B) {
            // Place boat when 'B' key is pressed
            Main.tileMapEditor.turningOnCursorColor();
            info.setText("Placing Boat");
        } else if (e.getCode() == KeyCode.ENTER) {
            Main.primaryStage.hide();
            Game.main(null);
        }
    }

    @FXML
    private void setObjectPosition(KeyEvent e){
        int temp;
        if (e.getCode() == KeyCode.A) {
            temp = Main.tileMapEditor.handleSetAxeRequest();
            if (temp == 1) {
                info.setText("Oops, position unavaliable!");
            }
            else if (temp == 2) {
                info.setText("Axe pos updated!");
                axePos.setText("Axe pos: (" + Main.tileMapEditor.cursor.cursorRows + ", " + Main.tileMapEditor.cursor.cursorColumns + ")");
            }
            else if (temp == 0) {
                info.setText("Axe done!");
                axePos.setText("Axe pos: (" + Main.tileMapEditor.cursor.cursorRows + ", " + Main.tileMapEditor.cursor.cursorColumns + ")");
            }
        }

        else if (e.getCode() == KeyCode.B) {
            temp = Main.tileMapEditor.handleSetBoatRequest();
            if (temp == 1) {
                info.setText("Position invalid!");
            }
            else if (temp == 2) {
                info.setText("Boat pos updated!");
                boatPos.setText("Boat: (" + Main.tileMapEditor.cursor.cursorRows + ", " + Main.tileMapEditor.cursor.cursorColumns + ")");
            }
            else if (temp == 0) {
                info.setText("Boat done!");
                boatPos.setText("Boat: (" + Main.tileMapEditor.cursor.cursorRows + ", " + Main.tileMapEditor.cursor.cursorColumns + ")");
            }
        }
    }

    @FXML
    private void exit(){
        Platform.setImplicitExit(true);
        Main.primaryStage.hide();
    }

    @FXML
    private void zoomIn() {
        Main.tileMapEditor.zoomInImage();
    }

    @FXML
    private void zoomOut() {
        Main.tileMapEditor.zoomOutImage();
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
        cursorPos.setText("(" + Main.tileMapEditor.cursor.cursorRows + ", " + Main.tileMapEditor.cursor.cursorColumns + ")");
    }

}
