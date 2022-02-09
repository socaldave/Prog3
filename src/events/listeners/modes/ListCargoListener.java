package events.listeners.modes;

import cli.view.View;
import events.events.InspectionEvent;
import events.events.ListCargoEvent;
import storageContract.administration.StorageManager;

public class ListCargoListener {
    StorageManager manager;
    View view;

    public ListCargoListener(StorageManager manager, View view){
        this.manager = manager;
        this.view = view;
    }

    public void onListCargo(ListCargoEvent event) {
        view.listContentByTyp(manager ,event.getType());
    }
}
