package events.handlers;

import events.events.ListHazardsEvent;
import events.listeners.modes.ListHazardListener;

import java.util.ArrayList;
import java.util.List;

public class ListHazardsEventHandler {
    private List<ListHazardListener> listenerList = new ArrayList<>();

    public void add(ListHazardListener inputEventListener) {
        this.listenerList.add(inputEventListener);
    }

    public void remove(ListHazardListener inputEventListener) {
        this.listenerList.remove(inputEventListener);
    }

    public void handle (ListHazardsEvent event) throws Exception {
        for (ListHazardListener listener : listenerList) {
            listener.onListHazardEvent(event);
        }
    }
}
