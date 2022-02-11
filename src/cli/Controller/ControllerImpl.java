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

    AddModeListener inputEventListenerAddMode;
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

    }


}