package events.listeners.messages;

import cli.view.View;
import storageContract.cargo.Cargo;

public class AddCargoListener implements AddEventListener {

    View view;

    public AddCargoListener(View view) {
        this.view = view;
    }

    @Override
    public void onAddEvent(AddEvent event) throws Exception {
        Object obj = event.getAddEventObject();
        if (!obj.getClass().getInterfaces()[0].getSimpleName().toLowerCase().equals("customer")) {
            System.out.println(((Cargo)obj).toString()+ " added");
        }
    }
}
