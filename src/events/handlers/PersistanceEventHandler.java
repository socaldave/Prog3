package events.handlers;

import events.events.ListCustomerEvent;
import events.events.PersistanceEvent;
import events.listeners.modes.InputEvent;
import events.listeners.modes.ListCustomerListener;
import events.listeners.modes.PersistanceListener;
import events.listeners.modes.saveLoadListener;

import java.util.ArrayList;
import java.util.List;

public class PersistanceEventHandler {
    private List<saveLoadListener> listenerList = new ArrayList<>();

    public void add(saveLoadListener persistanceListener) {
        this.listenerList.add(persistanceListener);
    }

    public void remove(saveLoadListener persistanceListener) {
        this.listenerList.remove(persistanceListener);
    }

    public void handle (PersistanceEvent event) throws Exception {
        for (saveLoadListener listener : listenerList) {
            listener.onStateChangeEvent(event);
        }
    }

}
