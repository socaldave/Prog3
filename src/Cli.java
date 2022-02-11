import cli.Controller.Controller;
import cli.Controller.ControllerImpl;
import cli.Observers.CapacityObserver;
import cli.Observers.CustomerObserver;
import cli.view.View;
import cli.view.ViewImpl;
import events.handlers.*;
import events.listeners.messages.AddCargoListener;
import events.listeners.modes.*;
import javafx.application.Application;
import javafx.stage.Stage;
import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;

import java.util.ArrayList;

public class Cli extends Application {

    public static void startCli(boolean tcp, boolean udp, Integer cap) throws Exception {

        CustomerManager customers = new CustomerManager(new ArrayList<>());
        StorageManager management = new StorageManager(customers);
        View view = new ViewImpl(System.in, System.out);

        CapacityObserver capacityObserver = new CapacityObserver(management, view);
        CustomerObserver customerObserver = new CustomerObserver(management, view);

        management.addObserver(capacityObserver);
        management.customerManager.addObserver(customerObserver);


        InputEventHandler inputEventHandler = new InputEventHandler();
        addEventHandler addEventHandler = new addEventHandler();

        AddCustomerEventHandler addCustomerEventHandler = new AddCustomerEventHandler();
        AddCargoEventHandler addCargoEventHandler = new AddCargoEventHandler();
        ListCustomerEventHandler listCustomerEventHandler = new ListCustomerEventHandler();
        ListCargoEventHandler listCargoEventHandler = new ListCargoEventHandler();
        DeleteCustomerEventHandler deleteCustomerEventHandler = new DeleteCustomerEventHandler();
        DeleteCargoEventHandler deleteCargoEventHandler = new DeleteCargoEventHandler();
        InspectionEventHandler inspectionEventHandler = new InspectionEventHandler();
        PersistanceEventHandler persistanceEventHandler = new PersistanceEventHandler();

        //set Command Handlers
        view.setInputEventHandler(inputEventHandler);
        //Set Mode Handler
        view.setAddHandler(addEventHandler);

        view.setCargoEventHandler(addCargoEventHandler);
        view.setCustomerEventHandler(addCustomerEventHandler);
        view.setListCustomerHandler(listCustomerEventHandler);
        view.setListCargoEventHandler(listCargoEventHandler);
        view.setDeleteCargoHandler(deleteCargoEventHandler);
        view.setDeleteCustomerHandler(deleteCustomerEventHandler);
        view.setInspectionHandler(inspectionEventHandler);
        view.setPersistanceHander(persistanceEventHandler);

        //Listeners
        AddCustomerListener addCustomerListener = new AddCustomerListener(management, view);
        AddingCargoListener addCargoListener = new AddingCargoListener(management, view);
        ListCargoListener listCargoListener = new ListCargoListener(management, view);
        ListCustomerListener listCustomerListener = new ListCustomerListener(management, view);
        DeleteCargoListener deleteCargoListener = new DeleteCargoListener(management, view);
        DeleteCustomerListener deleteCustomerListener = new DeleteCustomerListener(management, view);
        saveLoadListener persistanceListener = new saveLoadListener(management, view);
        InspectionEventListener inspectionEventListener = new InspectionEventListener(management , view);

        view.addAddCustomerListener(addCustomerListener);
        view.addAddCargoListener(addCargoListener);
        view.addListCargoListener(listCargoListener);
        view.addListCustomerListener(listCustomerListener);
        view.addDeleteCargoListener(deleteCargoListener);
        view.addDeleteCustomerListener(deleteCustomerListener);
        view.addInspectionListener(inspectionEventListener);
        view.addPersistanceListener(persistanceListener);

        Controller controller = new ControllerImpl(management, view);



        InitListener inputEventListenerActiveMode = new InitListener(view, management);
        AddModeListener inputEventListenerAddMode = new AddModeListener(management, view);
        DeleteListener inputEventListenerDeleteMode = new DeleteListener(management, view);
        ListListener inputEventListenerListMode = new ListListener(management, view);
        PersistanceListener inputEventListenerPersistenceMode = new PersistanceListener(management, view);
        EditListener inputEventListenerEditMode = new EditListener(management, view);

        // listen to view
        view.addInputEventListener(inputEventListenerActiveMode);
        view.addInputEventListener(inputEventListenerAddMode);
        view.addInputEventListener(inputEventListenerDeleteMode);
        view.addInputEventListener(inputEventListenerListMode);
        view.addInputEventListener(inputEventListenerPersistenceMode);

        view.addInputEventListener(inputEventListenerEditMode);
        //messages:
        AddCargoListener addEventListenerCargo = new AddCargoListener(view);
        events.listeners.messages.AddCustomerListener addEventListenerCustomer = new events.listeners.messages.AddCustomerListener(view);
        // listen to view
        view.addNewElementEventListener(addEventListenerCargo);
        view.addNewElementEventListener(addEventListenerCustomer);


        if (cap != null) management.storage.changeSize(cap);
        view.initView();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startCli(false, false, 10);
    }
}
