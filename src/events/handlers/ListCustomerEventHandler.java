package events.handlers;

import events.events.ListCargoEvent;
import events.events.ListCustomerEvent;
import events.listeners.modes.ListCargoListener;
import events.listeners.modes.ListCustomerListener;

import java.util.ArrayList;
import java.util.List;

public class ListCustomerEventHandler {
    private List<ListCustomerListener> listenerList = new ArrayList<>();

    public void add(ListCustomerListener inputEventListener) {
        this.listenerList.add(inputEventListener);
    }

    public void remove(ListCustomerListener inputEventListener) {
        this.listenerList.remove(inputEventListener);
    }

    public void handle (ListCustomerEvent event) throws Exception {
        for (ListCustomerListener listener : listenerList) {
            listener.onListCustomerEvent();
        }
    }
}
