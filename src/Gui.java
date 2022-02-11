import cli.Controller.Controller;
import cli.Controller.ControllerImpl;
import cli.view.View;
import cli.view.ViewImpl;
import events.handlers.InputEventHandler;
import events.handlers.addEventHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.server.Server;
import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class Gui extends Application {


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