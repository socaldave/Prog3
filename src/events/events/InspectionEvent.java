package events.events;

public class InspectionEvent {
    private int fachnummer;


    public InspectionEvent(int fachnummer)
    {
        this.fachnummer = fachnummer;
    }

    public int getFachnummer() {
        return fachnummer;
    }
}
