package events.listeners.messages;

import cli.view.View;
import storageContract.administration.Customer;

public class AddCustomerListener implements AddEventListener{
    View view;

    public AddCustomerListener(View view) {
        this.view = view;
    }
    @Override
    public void onAddEvent(AddEvent event) throws Exception {
        Object obj = event.getAddEventObject();
        if (obj.getClass().getInterfaces()[0].getSimpleName().toLowerCase().equals("customer")) {
            System.out.println("added -> "+ ((Customer)obj).toString());
        }
    }
}
