package events.events;

//TODO Implement Save/Load event
public class PersistanceEvent {

    private String state;

    public PersistanceEvent(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }

}
