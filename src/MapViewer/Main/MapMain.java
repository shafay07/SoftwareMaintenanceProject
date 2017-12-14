package MapViewer.Main;

import javafx.application.Application;
import javafx.stage.Stage;

public class MapMain extends Application {
    public static Stage first_stage;    //initial stage

    @Override
    /*abstract start method implemented for application.
    The application starts with the passed in stage as argument
     */

    public void start(Stage primaryStage) throws Exception {
        MapMain.first_stage = primaryStage;
        MapMain.first_stage.setTitle("MapViewer");

    }
    public static void main(String args[]){
        launch(args);
    }
}
