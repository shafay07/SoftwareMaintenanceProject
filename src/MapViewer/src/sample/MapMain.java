package MapViewer.src.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MapMain extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Structure.fxml"));
        primaryStage.setTitle("Map Viewer");
        primaryStage.setScene(new Scene(root, 800, 650));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
