package events.listeners.messages;

import java.util.EventObject;

public class AddEvent extends EventObject {

    private Object addObject;

    public AddEvent(Object source, Object obj) {
        super(source);
        this.addObject = obj;
    }

    public Object getAddEventObject() {
        return this.addObject;
    }
}
