package events.listeners.modes;

import cli.view.View;
import events.events.ListCustomerEvent;
import storageContract.administration.StorageManager;

public class ListCustomerListener {
    StorageManager manager;
    View view;

    public ListCustomerListener(StorageManager manager, View view){
        this.manager = manager;
        this.view = view;
    }

    public void onListCustomerEvent(){
        view.listCustomer(manager);
    }
}
