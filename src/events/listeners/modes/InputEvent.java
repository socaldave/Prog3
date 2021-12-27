package events.listeners.modes;

import java.util.EventObject;

public class InputEvent extends EventObject {

    private String currentCommand;
    private String lastCommand;

    public InputEvent(Object source, String currentCommand, String lastCommand) {
        super(source);
        this.currentCommand = currentCommand;
        this.lastCommand = lastCommand;
    }
    public String getCurrentCommand() {
        return this.currentCommand;
    }
    public String getLastCommand() {
        return  this.lastCommand;
    }
}
