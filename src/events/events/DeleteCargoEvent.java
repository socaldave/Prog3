package events.events;

public class DeleteCargoEvent {
    private int fachnummer;


    public DeleteCargoEvent(int fachnummer)
    {
        this.fachnummer = fachnummer;
    }

    public int getFachnummer() {
        return fachnummer;
    }
}
