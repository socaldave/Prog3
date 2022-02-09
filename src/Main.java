import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        //Found file test:
        URL filelink = getClass().getResource("gui/main.fxml");

        System.out.println("class: " + getClass());
        System.out.println(filelink);
        System.out.println("------------------------------------------");
        Parent root = FXMLLoader.load(filelink);
        primaryStage.setTitle("Cargo Management");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }
}