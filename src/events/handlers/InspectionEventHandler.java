package events.handlers;

import events.events.DeleteCustomerEvent;
import events.events.InspectionEvent;
import events.listeners.modes.DeleteCustomerListener;
import events.listeners.modes.InspectionEventListener;

import java.util.ArrayList;
import java.util.List;

public class InspectionEventHandler {
    private List<InspectionEventListener> listenerList = new ArrayList<>();

    public void add(InspectionEventListener inputEventListener) {
        this.listenerList.add(inputEventListener);
    }

    public void remove(InspectionEventListener inputEventListener) {
        this.listenerList.remove(inputEventListener);
    }

    public void handle (InspectionEvent event) throws Exception {
        for (InspectionEventListener listener : listenerList) {
            listener.onInspection(event);
        }
    }
}
