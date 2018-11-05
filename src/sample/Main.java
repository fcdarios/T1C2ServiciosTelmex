package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;
    @Override
    public void start(Stage _primaryStage) throws Exception{
        primaryStage = _primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sampleConsulta.fxml"));
        Scene scene = new Scene(root, 380, 300);
        scene.getStylesheets().add("bootstrapfx.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("TELMEX");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
