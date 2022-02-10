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

        if(event.getType() == null || event.getType().equals("")){
            doListContent();
        } else{
            view.listContentByTyp(manager ,event.getType());
        }

    }

    public void doListContent() {
        view.listContent(this.manager);
    }

    public void doListContentByType(String type) {
        view.listContentByTyp(this.manager,type);
    }

    public void doListContentByName(String nameOfCustomer) {
        view.listContentByName(this.manager, nameOfCustomer);
    }

}
