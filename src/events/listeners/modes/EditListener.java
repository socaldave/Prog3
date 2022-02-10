package events.listeners.modes;

import cli.Commands;
import cli.view.View;
import events.events.InspectionEvent;
import storageContract.administration.StorageManager;

import java.util.StringTokenizer;

public class EditListener implements InputEventListener {

    private StorageManager storageManager;
    private View view;

    public EditListener(StorageManager management, View view) {
        this.storageManager = management;
        this.view = view;
    }
    @Override
    public void onInputEvent(InputEvent event) throws Exception {
        String lastCmd = event.getLastCommand();
        StringTokenizer st = new StringTokenizer(event.getCurrentCommand());
        if (lastCmd.equals(Commands.EDIT_MODE)) {
            view.printInspectInstruction();
            try{
                int id = Integer.parseInt(st.nextToken());
                InspectionEvent inspectionEvent = new InspectionEvent(id);
                view.handleInspectionEvent(inspectionEvent);

            } catch (Exception e){
                view.printInvalidCargoParams();
            }
        }
    }

}
