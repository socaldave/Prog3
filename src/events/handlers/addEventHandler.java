package events.handlers;

import events.listeners.messages.AddEvent;
import events.listeners.messages.AddEventListener;

import java.util.ArrayList;
import java.util.List;

public class addEventHandler {
    private List<AddEventListener> listenerList = new ArrayList<>();

    public void add(AddEventListener listener) {
        this.listenerList.add(listener);
    }

    public void remove(AddEventListener listener) {
        this.listenerList.remove(listener);
    }

    public void handle(AddEvent event) throws Exception {
        for (AddEventListener listener : listenerList) {
            listener.onAddEvent(event);
        }
    }
}
