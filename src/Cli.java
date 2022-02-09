import cli.Controller.Controller;
import cli.Controller.ControllerImpl;
import cli.view.View;
import cli.view.ViewImpl;
import events.handlers.AddCargoEventHandler;
import events.handlers.AddCustomerEventHandler;
import events.handlers.InputEventHandler;
import events.handlers.addEventHandler;
import events.listeners.messages.AddCargoListener;
import events.listeners.modes.AddCustomerListener;
import events.listeners.modes.AddingCargoListener;
import javafx.application.Application;
import javafx.stage.Stage;
import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;

import java.util.ArrayList;

public class Cli extends Application {

    public static void startCli(boolean tcp, boolean udp, Integer cap) throws Exception {
        InputEventHandler inputEventHandler = new InputEventHandler();
        addEventHandler addEventHandler = new addEventHandler();

        AddCustomerEventHandler addCustomerEventHandler = new AddCustomerEventHandler();
        AddCargoEventHandler addCargoEventHandler = new AddCargoEventHandler();

        CustomerManager customers = new CustomerManager(new ArrayList<>());
        StorageManager management = new StorageManager(customers);
        View view = new ViewImpl(System.in, System.out);

        //set Command Handlers
        view.setInputEventHandler(inputEventHandler);
        //Set Mode Handler
        view.setAddHandler(addEventHandler);

        view.setCargoEventHandler(addCargoEventHandler);
        view.setCustomerEventHandler(addCustomerEventHandler);



         //Listeners
        AddCustomerListener addCustomerListener = new AddCustomerListener(management, view);
        AddingCargoListener addCargoListener = new AddingCargoListener(management, view);


        view.addAddCustomerListener(addCustomerListener);
        view.addAddCargoListener(addCargoListener);
        Controller controller = new ControllerImpl(management, view);

        if (cap != null) management.storage.changeSize(cap);
        view.initView();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startCli(false, false, 10);
    }
}
