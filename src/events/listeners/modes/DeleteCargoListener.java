package events.listeners.modes;

import cli.view.View;
import events.events.DeleteCargoEvent;
import storageContract.administration.StorageManager;

public class DeleteCargoListener {
    StorageManager manager;
    View view;

    public DeleteCargoListener(StorageManager manager, View view){
        this.manager = manager;
        this.view = view;
    }

    public void onDeleteCargoEvent(DeleteCargoEvent event){

        boolean deleted = manager.removeCargo(event.getFachnummer());
        if(deleted){
            view.printRemoveCargo(event.getFachnummer());
        }
        else{
            view.printInvalidCargoParams();
        }
    }
}
