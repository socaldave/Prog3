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

public class AddCargoListener {
    private StorageManager storageManager;
    private View view;

    public AddCargoListener(StorageManager management, View view) {
        this. storageManager= management;
        this.view = view;
    }

    //TODO parsing of user strings and descisions should be handled in the CLI,GUI

    public void onAddCargoEvent(AddCargoEvent event) throws Exception {
        try {
            Cargo cargo = null;
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
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setManagement(StorageManager management){
        this.storageManager = management;
    }

    public Collection<Hazard> getHazardsOfInput(String inputHarzards) {
        Collection<Hazard> col = new LinkedList<>();
        if (inputHarzards.equals(",")) return col;
        else if (inputHarzards.contains(",") && inputHarzards.length() > 1) {
            String[] str = inputHarzards.split(",");
            for (int i = 0; i < str.length; i++) {
                col = checkHazards(str[i], col);
            }
        } else col = checkHazards(inputHarzards, col);
        return col;
    }

    public Collection<Hazard> checkHazards(String str, Collection<Hazard> col) {
        if (str.toLowerCase().equals(Hazard.flammable.toString().toLowerCase()))
            col.add(Hazard.flammable);
        else if (str.toLowerCase().equals(Hazard.toxic.toString().toLowerCase()))
            col.add(Hazard.toxic);
        else if (str.toLowerCase().equals(Hazard.radioactive.toString().toLowerCase()))
            col.add(Hazard.radioactive);
        else if (str.toLowerCase().equals(Hazard.explosive.toString().toLowerCase()))
            col.add(Hazard.explosive);
        return col;

    }

    public boolean YesOrNo(String input) {
        if (input.toLowerCase().equals("y")) return true;
        else if (input.toLowerCase().equals("yes")) return true;
        else if (input.toLowerCase().equals("n")) return false;
        else if (input.toLowerCase().equals("no")) return false;
        return false;
    }

}
