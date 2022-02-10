package events.listeners.modes;

import cli.Commands;
import cli.view.View;
import events.events.DeleteCargoEvent;
import events.events.DeleteCustomerEvent;
import storageContract.administration.Customer;

import storageContract.administration.StorageManager;

import java.util.StringTokenizer;

public class DeleteListener implements InputEventListener {

    private StorageManager management;
    private View view;

    public DeleteListener(StorageManager management, View view) {
        this.management = management;
        this.view = view;
    }

    @Override
    public void onInputEvent(InputEvent event) throws Exception {
        StringTokenizer st = new StringTokenizer(event.getCurrentCommand());
        String str = st.nextToken();
        if (event.getLastCommand().equals(Commands.DELETE_MODE)) {
            if (isNumeric(event.getCurrentCommand())) {
                DeleteCargoEvent deleteCargoEvent = new DeleteCargoEvent(Integer.parseInt(str));
                view.handleDeleteCargoEvent(deleteCargoEvent);
            } else {
                DeleteCustomerEvent deleteCustomerEvent = new DeleteCustomerEvent(str);
                view.handleDeleteCustomerEvent(deleteCustomerEvent);
            }
        }
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}