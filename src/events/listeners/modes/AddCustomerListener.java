package events.listeners.modes;

import cli.view.View;
import events.events.AddCustomerEvent;
import events.listeners.messages.AddEvent;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.Storage;
import storageContract.administration.StorageManager;

import java.math.BigDecimal;
import java.time.Duration;

public class AddCustomerListener {
    StorageManager storageManager;
    View view;

    public AddCustomerListener(StorageManager management, View view) {
        this. storageManager= management;
        this.view = view;
    }

    public void onInputEvent(AddCustomerEvent event) throws Exception {
        Customer customer = new CustomerImpl(event.getCustomerName(), BigDecimal.valueOf(1000000), Duration.ofDays(90));
        if (this.storageManager.customerManager.add(customer)) {
            AddEvent addEvent = new AddEvent(this.view, customer);
            this.view.handleAddEvent(addEvent);
            view.printCustomerAdded(event.getCustomerName());
        } else
            this.view.printUnsupportCommand();
    }
}

