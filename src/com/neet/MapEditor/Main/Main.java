package com.neet.MapEditor.Main;

import com.neet.DiamondHunter.Main.Game;
import com.neet.MapEditor.View.RootLayoutController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Main extends Application{

    public static Stage primaryStage;
    public static TileMapEditor tileMapEditor;

    public BorderPane layout;
    public TilePane tilePane;

    public static boolean launch = false;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        Main.primaryStage.setTitle("Map Editor");
        Platform.setImplicitExit(false);
        initLayout();
        showMap();
        launch = true;
        primaryStage.setOnCloseRequest(event -> {  Platform.setImplicitExit(true); });
    }

    public void initLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/neet/MapEditor/View/RootLayout.fxml"));
            layout = loader.load();

            Scene scene = new Scene(layout);
            scene.setOnKeyPressed(layout.getOnKeyPressed());
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public void showMap(){
        tileMapEditor = new TileMapEditor();
        tileMapEditor.loadMapFile("/Maps/testmap.map");
        tileMapEditor.loadImageFiles("/Tilesets/testtileset.gif", "/Sprites/items.gif");

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/neet/MapEditor/View/Overview.fxml"));
            tilePane = loader.load();
        } catch(IOException exception){
            exception.printStackTrace();
        }

        tilePane.setPrefColumns(tileMapEditor.numberOfColumns);
        tilePane.setPrefRows(tileMapEditor.numberOfRows);
        tileMapEditor.initialiseCanvas();
        tilePane.getChildren().add(tileMapEditor.currentCanvas);

        layout.setCenter(tilePane);
    }
    
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void about (String about, String Heading)
    {
    	JOptionPane.showMessageDialog(null, about, Heading, JOptionPane.INFORMATION_MESSAGE);
    }
    public static void main(String[] args){ launch(args); }
}
