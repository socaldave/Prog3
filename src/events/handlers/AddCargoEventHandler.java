package events.handlers;

import events.events.AddCargoEvent;
import events.listeners.modes.AddingCargoListener;
import events.listeners.modes.InputEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddCargoEventHandler {
    //TODO Create Specific events for each user action
    private List<AddingCargoListener> listenerList = new ArrayList<>();

    public void add(AddingCargoListener inputEventListener) {
        this.listenerList.add(inputEventListener);
    }

    public void remove(InputEventListener inputEventListener) {
        this.listenerList.remove(inputEventListener);
    }

    public void handle (AddCargoEvent event) throws Exception {
        for (AddingCargoListener listener : listenerList) {
            listener.onAddCargoEvent(event);
        }
    }
}
