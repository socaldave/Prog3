package events.handlers;

import events.events.AddCustomerEvent;
import events.listeners.modes.AddCustomerListener;

import java.util.ArrayList;
import java.util.List;

public class AddCustomerEventHandler {
    private List<AddCustomerListener> listenerList = new ArrayList<>();

    public void add(AddCustomerListener inputEventListener) {
        this.listenerList.add(inputEventListener);
    }

    public void remove(AddCustomerListener inputEventListener) {
        this.listenerList.remove(inputEventListener);
    }

    public void handle (AddCustomerEvent event) throws Exception {
        for (AddCustomerListener listener : listenerList) {
            listener.onInputEvent(event);
        }
    }
}
