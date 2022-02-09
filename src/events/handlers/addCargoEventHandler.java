package events.handlers;

import events.events.AddCargoEvent;
import events.listeners.modes.AddCargoListener;
import events.listeners.modes.InputEvent;
import events.listeners.modes.InputEventListener;

import java.util.ArrayList;
import java.util.List;

public class addCargoEventHandler {
    //TODO Create Specific events for each user action
    private List<AddCargoListener> listenerList = new ArrayList<>();

    public void add(AddCargoListener inputEventListener) {
        this.listenerList.add(inputEventListener);
    }

    public void remove(InputEventListener inputEventListener) {
        this.listenerList.remove(inputEventListener);
    }

    public void handle (AddCargoEvent event) throws Exception {
        for (AddCargoListener listener : listenerList) {
            listener.onAddCargoEvent(event);
        }
    }
}
