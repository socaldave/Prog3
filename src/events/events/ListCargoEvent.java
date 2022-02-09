package events.events;

public class ListCargoEvent {
    private String type;

    public ListCargoEvent(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
