package events.listeners.modes;

import cli.Commands;
import cli.view.View;
import events.events.PersistanceEvent;
import storageContract.administration.StorageManager;

import java.util.StringTokenizer;

public class PersistanceListener implements InputEventListener {

    private StorageManager management;
    private View view;

    public PersistanceListener(StorageManager management, View view) {
        this.management = management;
        this.view = view;
    }

    @Override
    public void onInputEvent(InputEvent event) throws Exception {
        String cmd = event.getCurrentCommand();
        StringTokenizer st = new StringTokenizer(cmd);
        String last = event.getLastCommand().toLowerCase();
        if (last.equals(Commands.PERS_MODE)) {

            PersistanceEvent persistanceEvent = new PersistanceEvent(st.nextToken().toLowerCase());
            view.handlePersistanceEvent(persistanceEvent);

        }
    }

}
