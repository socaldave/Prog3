package events.listeners.modes;

import cli.view.View;
import events.events.DeleteCustomerEvent;
import storageContract.administration.StorageManager;

public class DeleteCustomerListener {
    StorageManager manager;
    View view;

    public DeleteCustomerListener(StorageManager manager, View view){
        this.manager = manager;
        this.view = view;
    }

    public void onDeleteCustomer(DeleteCustomerEvent event){
        manager.customerManager.removeCustomerByName(event.getCustomerName());
    }

}
