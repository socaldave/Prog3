package cli.Controller;

import cli.view.View;
import events.listeners.messages.AddCargoListener;
import events.listeners.messages.AddCustomerListener;
import events.listeners.modes.*;
import storageContract.administration.StorageManager;

public class ControllerImpl implements Controller {
    StorageManager management;
   // ManagementObserver managementObserver;

    View view;
    //ViewObserver viewObserver;


    //Mode Listener
    InitListener inputEventListenerActiveMode;

    AddListener inputEventListenerAddMode;
    AddCargoListener addCargoListener;
    AddCustomerListener addCustomerListener;

    //Delete Listeners
    DeleteListener inputEventListenerDeleteMode;
    DeleteCargoListener deleteCargoListener;
    DeleteCustomerListener deleteCustomerListener;

    //List Listeners
    ListListener inputEventListenerListMode;
    ListCustomerListener listCustomerListener;
    ListCargoListener listCargoListener;

    PersistanceListener inputEventListenerPersistenceMode;
    ConfigModeListener inputEventListenerConfigMode;

    //Inspection Listener
    EditListener inputEventListenerEditMode;
    InspectionEventListener inspectionEventListener;

    // add success events
    AddCargoListener addEventListenerCargo;
    AddCustomerListener addEventListenerCustomer;


    public ControllerImpl(StorageManager management, View view) {
        this.management = management;
        //this.managementObserver = new ManagementObserver(management, view);
        //this.management.addObserver(managementObserver);
        this.view = view;

        //this.viewObserver = new ViewObserver(view);
        this.init();
    }

    private void init() {

        //TODO This should be handled in the MAIN class to allow for configuration
        //modes:
        this.inputEventListenerActiveMode = new InitListener(view, management);
        this.inputEventListenerAddMode = new AddListener(management, view);
        this.inputEventListenerDeleteMode = new DeleteListener(management, view);
        this.inputEventListenerListMode = new ListListener(management, view);
        this.inputEventListenerPersistenceMode = new PersistanceListener(management, view);
        this.inputEventListenerConfigMode = new ConfigModeListener(management, view);
        this.inputEventListenerEditMode = new EditListener(management, view);
        // listen to view
        this.view.addInputEventListener(inputEventListenerActiveMode);
        this.view.addInputEventListener(inputEventListenerAddMode);
        this.view.addInputEventListener(inputEventListenerDeleteMode);
        this.view.addInputEventListener(inputEventListenerListMode);
        this.view.addInputEventListener(inputEventListenerPersistenceMode);
        this.view.addInputEventListener(inputEventListenerConfigMode);
        this.view.addInputEventListener(inputEventListenerEditMode);
        //messages:
        this.addEventListenerCargo = new AddCargoListener(view);
        this.addEventListenerCustomer = new AddCustomerListener(view);
        // listen to view
        this.view.addNewElementEventListener(this.addEventListenerCargo);
        this.view.addNewElementEventListener(this.addEventListenerCustomer);
    }


}
