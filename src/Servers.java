import cli.Controller.Controller;
import cli.Controller.ControllerImpl;
import cli.view.View;
import cli.view.ViewImpl;
import events.handlers.*;
import events.listeners.messages.AddCargoListener;
import events.listeners.modes.*;
import network.server.Server;
import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;

import java.util.ArrayList;
import java.util.Locale;

public class Servers {
    private static Server server;

    public static void main(String[] args) throws Exception {
        Server server = null;

        if(args[0].toLowerCase(Locale.ROOT).equals("tcp")){
             server = new Server("tcp",100000);
             startCli(true,false,10);
        } else if(args[0].toLowerCase(Locale.ROOT).equals("udp")){
             server = new Server("udp",100000);
             startCli(false,true,10);
        }

        server.run();
        server.InitView(100);
    }

    public static void startCli(boolean tcp, boolean udp, Integer cap) throws Exception {
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


        CustomerManager customers = new CustomerManager(new ArrayList<>());
        StorageManager management = new StorageManager(customers);
        View view = new ViewImpl(System.in, System.out);

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

        //TODO This should be handled in the MAIN class to allow for configuration
        //modes:
        InitListener activeMode = new InitListener(view, management);
        AddModeListener addModeListener = new AddModeListener(management, view);
        DeleteListener deleteListener = new DeleteListener(management, view);
        ListListener listListener = new ListListener(management, view);
        PersistanceListener loadModelistener = new PersistanceListener(management, view);
        EditListener editListener = new EditListener(management, view);


        // listen to view
        view.addInputEventListener(activeMode);
        view.addInputEventListener(addModeListener);
        view.addInputEventListener(deleteListener);
        view.addInputEventListener(listListener);
        view.addInputEventListener(loadModelistener);

        view.addInputEventListener(editListener);
        //messages:
        AddCargoListener addEventListenerCargo = new AddCargoListener(view);

        events.listeners.messages.AddCustomerListener addEventListenerCustomer = new events.listeners.messages.AddCustomerListener(view);
        // listen to view
        view.addNewElementEventListener(addEventListenerCargo);
        view.addNewElementEventListener(addEventListenerCustomer);

        management.storage.changeSize(cap);

        view.setNetwork(tcp, udp, management);


        view.initView();

    }


}
