package events.listeners.modes;

import cli.Commands;
import cli.view.View;


import storageContract.administration.StorageManager;

public class InitListener implements InputEventListener {
    public StorageManager storageManager;
    private View view;

    public InitListener(View view, StorageManager management) {
        this.view = view;
        this.storageManager = management;
    }

    @Override
    public void onInputEvent(InputEvent event) throws Exception {
        if (event.getLastCommand().equals("")) {
            switch (event.getCurrentCommand()) {
                case Commands.ADD_MODE: {
                    view.setViewMode(View.MODE_ACTIVE);
                    view.printAddInstruction();
                }
                break;
                case Commands.DELETE_MODE: {
                    view.setViewMode(View.MODE_ACTIVE);
                    view.printDeleteInstruction();
                }
                break;
                case Commands.LIST_MODE: {
                    view.setViewMode(View.MODE_ACTIVE);
                    view.printListInstruction();
                }
                break;

                case Commands.PERS_MODE: {
                    view.setViewMode(View.MODE_ACTIVE);
                    view.printSaveLoad();
                }
                break;


                case Commands.EDIT_MODE: {
                    view.setViewMode(View.MODE_ACTIVE);
                    view.printInspectInstruction();
                }
                break;
                case Commands.EXIT: {

                    view.printExitProgramm();
                    System.exit(0);
                }
                default:
                    view.setViewMode(View.MODE_DEACTIVE);
                    view.printUnsupportCommand();
            }
        }
    }
}
