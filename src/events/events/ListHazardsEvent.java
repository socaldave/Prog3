package events.events;

public class ListHazardsEvent {
    String command;

    public ListHazardsEvent(String str){
        this.command=str;
    }

    public String getCommand(){
        return command;
    }

}
