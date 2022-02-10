package events.listeners.modes;

import cli.Commands;

import cli.view.View;
import events.events.AddCargoEvent;
import events.events.AddCustomerEvent;
import events.listeners.messages.AddEvent;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;

import storageContract.administration.StorageManager;
import storageContract.cargo.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;
import java.util.StringTokenizer;

public class AddModeListener implements InputEventListener {

    private StorageManager storageManager;
    private View view;


    public AddModeListener(StorageManager management, View view) {
        this. storageManager= management;
        this.view = view;
    }

    //TODO parsing of user strings and descisions should be handled in the CLI,GUI
    @Override
    public void onInputEvent(InputEvent event) throws Exception {
        String text = event.getCurrentCommand();
        StringTokenizer st = new StringTokenizer(text);
        if (event.getLastCommand().equals(Commands.ADD_MODE)) {
            if (st.countTokens() > 1) {
                this.doAddCargo(st);
            } else {
                AddCustomerEvent addCustomerEvent = new AddCustomerEvent(this, st.nextToken(),  Duration.ofDays(90), BigDecimal.valueOf(1000000) );
                view.handleAddCustomerEvent(addCustomerEvent);
            }
        }
    }


    public void doAddCargo(StringTokenizer st) throws Exception {
        try {
            String type = st.nextToken();
            String customer = st.nextToken();
            BigDecimal value = BigDecimal.valueOf(Float.parseFloat(st.nextToken()));
            int sec = Integer.parseInt(st.nextToken());
            Collection<Hazard> harzards = this.getHazardsOfInput(st.nextToken());
            Boolean pressurized = false;
            Boolean fraggile = false;
            Boolean fest = false;
            if (st.hasMoreTokens()) pressurized = this.YesOrNo(st.nextToken());
            if (st.hasMoreTokens()) fraggile = this.YesOrNo(st.nextToken());
            if (st.hasMoreTokens()) fest = this.YesOrNo(st.nextToken());
            Cargo cargo = null;

            AddCargoEvent addCargoEvent = new AddCargoEvent(this, type,customer,value, Duration.ofDays(sec), harzards,pressurized, fraggile, fest);
            this.view.handleAddCargoEvent(addCargoEvent);

        } catch (Exception e) {
            //System.out.println(e.getMessage());
            view.printUnsupportCommand();
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