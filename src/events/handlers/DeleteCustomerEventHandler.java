package events.handlers;

import events.events.DeleteCargoEvent;
import events.events.DeleteCustomerEvent;
import events.listeners.modes.DeleteCargoListener;
import events.listeners.modes.DeleteCustomerListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteCustomerEventHandler {
    private List<DeleteCustomerListener> listenerList = new ArrayList<>();

    public void add(DeleteCustomerListener inputEventListener) {
        this.listenerList.add(inputEventListener);
    }

    public void remove(DeleteCustomerListener inputEventListener) {
        this.listenerList.remove(inputEventListener);
    }

    public void handle (DeleteCustomerEvent event) throws Exception {
        for (DeleteCustomerListener listener : listenerList) {
            listener.onDeleteCustomer(event);
        }
    }
}
