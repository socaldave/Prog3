package events.listeners.modes;

//import cli.Constants;
//import cli.events.listeners.AddEvents.AddCargoListener;
//import cli.events.listeners.AddEvents.AddCustomerListener;
import cli.Commands;
import cli.view.View;
import events.listeners.messages.AddCargoListener;
import events.listeners.messages.AddCustomerListener;

import storageContract.administration.StorageManager;

import java.util.StringTokenizer;

public class ConfigModeListener implements InputEventListener {

    private StorageManager StorageManager;
    private View view;
    private AddCargoListener addEventListenerCargo;
    private AddCustomerListener addEventListenerCustomer;

    public ConfigModeListener(StorageManager management, View view) {
        this.StorageManager = management;
        this.view = view;
    }

    @Override
    public void onInputEvent(InputEvent event) throws Exception {
        String lastCmd = event.getLastCommand();
        StringTokenizer st = new StringTokenizer(event.getCurrentCommand());
        if (lastCmd.equals(Commands.CONFIG_MODE)) {
            switch (st.nextToken()) {
                case "add": {
                    this.onAdd(st);
                }
                break;
                case "remove": {
                    this.onRemove(st);
                }
                break;
            }
        }
    }

    public void onRemove(StringTokenizer st) {
        if (st.hasMoreTokens()) {
            switch (st.nextToken()) {
                case "cargolistener": {
                    this.view.removeNewElementEventListener(this.addEventListenerCargo);
                    this.addEventListenerCargo = null;
                }
                break;
                case "customerlistener": {
                    this.view.removeNewElementEventListener(this.addEventListenerCustomer);
                    this.addEventListenerCustomer = null;
                }
                break;
            }
        }
    }
    public void onAdd(StringTokenizer st) {
        if (st.hasMoreTokens()) {
            switch (st.nextToken()) {
                case "cargolistener": {
                    this.addEventListenerCargo = new AddCargoListener(view);
                    this.view.addNewElementEventListener(this.addEventListenerCargo);
                }
                break;
                case "customerlistener": {
                    this.addEventListenerCustomer = new AddCustomerListener(view);
                    this.view.addNewElementEventListener(this.addEventListenerCustomer);
                }
                break;
            }
        }
    }
}