package events.listeners.modes;

import cli.view.View;
import events.events.DeleteCustomerEvent;
import events.events.InspectionEvent;
import storageContract.administration.StorageManager;

public class InspectionEventListener {
    StorageManager manager;
    View view;

    public InspectionEventListener(StorageManager manager, View view){
        this.manager = manager;
        this.view = view;
    }

    public void onInspection(InspectionEvent event) {
        try{
            view.inspect(manager, event.getFachnummer());
        } catch (Exception e){
            view.printUnsupportCommand();
        }
    }
}
