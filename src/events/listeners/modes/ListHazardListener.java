package events.listeners.modes;

import cli.view.View;
import events.events.ListHazardsEvent;
import storageContract.administration.StorageManager;

public class ListHazardListener {

    StorageManager manager;
    View view;

    public ListHazardListener(StorageManager manager, View view){
        this.manager = manager;
        this.view = view;
    }

    public void onListHazardEvent(ListHazardsEvent event){
        if(event.getCommand().equals("i")){
            view.listContainedHazards(manager);
        }else if(event.getCommand().equals("e")){
            view.printNotContainedHazards();
        }else{
            view.printInvalidType();
        }


    }
}
