package events.handlers;

import events.listeners.modes.InputEvent;
import events.listeners.modes.InputEventListener;

import java.util.ArrayList;
import java.util.List;

public class InputEventHandler {
    private List<InputEventListener> listenerList = new ArrayList<>();

    public void add(InputEventListener inputEventListener) {
        this.listenerList.add(inputEventListener);
    }

    public void remove(InputEventListener inputEventListener) {
        this.listenerList.remove(inputEventListener);
    }

    public void handle (InputEvent event) throws Exception {
        for (InputEventListener listener : listenerList) {
            listener.onInputEvent(event);
        }
    }
}
