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
       boolean deleted = manager.customerManager.removeCustomerByName(event.getCustomerName());
        if(deleted){
            view.printRemoveCustomer(event.getCustomerName());
        }
         else{
             view.printCustomerNontexistent(event.getCustomerName());
        }

    }

}
