package events.listeners.modes;

import cli.Commands;
import cli.view.View;
import events.events.ListCargoEvent;
import events.events.ListCustomerEvent;
import storageContract.administration.StorageManager;

import java.util.StringTokenizer;

public class ListListener implements InputEventListener {

    private StorageManager management;
    private View view;

    public ListListener(StorageManager management, View view) {
        this.management = management;
        this.view = view;
    }

    @Override
    public void onInputEvent(InputEvent event) throws Exception {
        String lastCommand = event.getLastCommand();
        String currentCommand = event.getCurrentCommand();
        StringTokenizer tokenizer = new StringTokenizer(currentCommand);
        String mode = tokenizer.nextToken();
        if (lastCommand.equals(Commands.LIST_MODE)) {
            switch (mode) {
                case Commands.CUSTOMER: {
                    ListCustomerEvent listEvent = new ListCustomerEvent();
                    view.handleListCustomerEvent(listEvent);
                    this.doListCustomer();
                }
                break;
                case Commands.CARGO: {
                    try{
                        if(tokenizer.hasMoreTokens()){
                            ListCargoEvent Listevent = new ListCargoEvent(tokenizer.nextToken());
                            view.handleListCargoEvent(Listevent);
                            this.doListContentByType(tokenizer.nextToken());
                        } else this.doListContent();
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                break;
                case Commands.HAZARD: {
                    try{
                        this.doListHazards();
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                }
                break;
                default:
                    view.printUnsupportCommand();
                    break;
            }
        }
    }

    private void doListHazards() {

        view.listHazards(this.management);
    }

    public void doListCustomer() throws Exception {
        view.listCustomer(this.management);
    }

    public void doListContent() {
        view.listContent(this.management);
    }

    public void doListContentByType(String type) {
        view.listContentByTyp(this.management,type);
    }

    public void doListContentByName(String nameOfCustomer) {
        view.listContentByName(this.management, nameOfCustomer);
    }
}
