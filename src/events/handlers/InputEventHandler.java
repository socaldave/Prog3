package events.handlers;

import cli.Commands;
import events.events.AddCustomerEvent;
import events.listeners.messages.AddEvent;
import events.listeners.modes.InputEvent;
import events.listeners.modes.InputEventListener;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class InputEventHandler {
    //TODO Create Specific events for each user action
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
