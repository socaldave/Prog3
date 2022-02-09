package events.events;

public class DeleteCustomerEvent {
    private String customerName;

    public DeleteCustomerEvent(String name){
        this.customerName = name;
    }

    public String getCustomerName() {
        return customerName;
    }
}
