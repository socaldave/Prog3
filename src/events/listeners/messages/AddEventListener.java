package events.listeners.messages;

import java.util.EventListener;

public interface AddEventListener extends EventListener {

    void onAddEvent (AddEvent event) throws Exception;
}
