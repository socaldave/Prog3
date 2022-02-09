package events.handlers;

import events.events.AddCustomerEvent;
import events.events.DeleteCargoEvent;
import events.listeners.modes.AddCustomerListener;
import events.listeners.modes.DeleteCargoListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteCargoEventHandler {
    private List<DeleteCargoListener> listenerList = new ArrayList<>();

    public void add(DeleteCargoListener inputEventListener) {
        this.listenerList.add(inputEventListener);
    }

    public void remove(DeleteCargoListener inputEventListener) {
        this.listenerList.remove(inputEventListener);
    }

    public void handle (DeleteCargoEvent event) throws Exception {
        for (DeleteCargoListener listener : listenerList) {
            listener.onDeleteCargoEvent(event);
        }
    }
}
