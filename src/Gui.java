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

    public static void main(String[] args) throws Exception {
        Server server = null;

        if(args != null){
            String netType = args[0];
            int capacity = Integer.parseInt(args[1]);
            if(args[0].toLowerCase(Locale.ROOT).equals("tcp")){
                server = new Server("tcp",capacity);
            } else if(args[0].toLowerCase(Locale.ROOT).equals("udp")){
                server = new Server("udp",capacity);

            }
            server.run();
        } else System.out.println("Please provide args for start. args[0] = network type , args[1] = capacity");
    }



}
