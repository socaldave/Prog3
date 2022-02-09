package events.listeners.modes;

import cli.Commands;
import cli.view.View;
import events.events.AddCargoEvent;
import events.listeners.messages.AddEvent;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.StorageManager;
import storageContract.cargo.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class AddingCargoListener {
    private StorageManager storageManager;
    private View view;

    public AddingCargoListener(StorageManager management, View view) {
        this. storageManager= management;
        this.view = view;
    }

    //TODO parsing of user strings and descisions should be handled in the CLI,GUI

    public void onAddCargoEvent(AddCargoEvent event) throws Exception {
        try {
            Cargo cargo = null;
            System.out.println(event.getType().toString());
            switch (event.getType().toString()) {
                case "cargo": {
                    cargo = new CargoImpl(storageManager.customerManager.getCustomerWithName(event.getCustomerName()), event.getValue(), event.getDuration(), event.getHazards());
                }
                break;
                case "liquidbulkcargo": {
                    cargo = new LiquidBulkCargoImpl(this.storageManager.customerManager.getCustomerWithName(event.getCustomerName()), event.getValue(), event.getDuration(), event.getHazards(), event.getPressurized());
                }
                break;

                case "unitisedcargo": {
                    cargo = new UnitisedCargoImpl(this.storageManager.customerManager.getCustomerWithName(event.getCustomerName()), event.getValue(), event.getDuration(), event.getHazards(), event.getPressurized(), event.getFragile());
                }
                break;
            }
            if (this.storageManager.addCargo(cargo)) {
                AddEvent addEvent = new AddEvent(view, cargo);
                this.view.getAddHandler().handle(addEvent);
            } else {
                view.printInvalidCargoParams();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setManagement(StorageManager management){
        this.storageManager = management;
    }

}
