package cli.Observers;


import cli.view.View;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;

import java.util.Observable;
import java.util.Observer;

public class CapacityObserver implements Observer {

    StorageManager manager;
    View view;
    Cargo lastContent;
    String activity;

    public CapacityObserver(StorageManager manager, View view)  {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void update(Observable o, Object arg) {
        view.printLowCapacity(this.manager.capNotification);
    }
}
