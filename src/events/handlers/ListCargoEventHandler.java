package events.handlers;

import events.events.InspectionEvent;
import events.events.ListCargoEvent;
import events.listeners.modes.InspectionEventListener;
import events.listeners.modes.ListCargoListener;

import java.util.ArrayList;
import java.util.List;

public class ListCargoEventHandler {
    private List<ListCargoListener> listenerList = new ArrayList<>();

    public void add(ListCargoListener inputEventListener) {
        this.listenerList.add(inputEventListener);
    }

    public void remove(ListCargoListener inputEventListener) {
        this.listenerList.remove(inputEventListener);
    }

    public void handle (ListCargoEvent event) throws Exception {
        for (ListCargoListener listener : listenerList) {
            listener.onListCargo(event);
        }
    }
}
