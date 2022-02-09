package cli.view;


import events.events.*;
import events.handlers.InputEventHandler;
import events.handlers.addEventHandler;
import events.listeners.messages.AddEvent;
import events.listeners.messages.AddEventListener;
import events.listeners.modes.InputEvent;
import events.listeners.modes.InputEventListener;

import storageContract.administration.Customer;

import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;

import java.io.BufferedReader;
import java.io.PrintStream;

public interface View {

    void initView();
    PrintStream getPrintStream();
    BufferedReader getReader();



    void printCargo(Cargo cargo);
    boolean setInputEventHandler(InputEventHandler handler);
    void printInstruction();
    void printAddInstruction();
    void printDeleteInstruction();
    void printListInstruction();
    void printLostCargo(Cargo cargo);
    void printRemoveCustomer(String name);
    void printRemoveCargo(int fachnummer);
    void printAddCustomer(Customer customer);
    void printUnsupportCommand();
    void printLowCapacity(double cap);
    void listCustomer(StorageManager management);
    void listContentOfOwner(StorageManager management, String customerName);
    void listContentByName(StorageManager management, String customerName);
    void addInputEventListener(InputEventListener listener);
    void handleInputEvent(InputEvent inputEvent) throws Exception;



    void handleAddCargoEvent(AddCargoEvent event) throws Exception;
    void handleAddCustomerEvent(AddCustomerEvent event) throws Exception;
    void handleDeleteCustomerEvent(DeleteCustomerEvent event) throws Exception;
    void handleDeleteCargoEvent(DeleteCargoEvent event) throws Exception;
    void handleInspectionEvent(InspectionEvent event) throws Exception;
    void handleListCargoEvent(ListCargoEvent event) throws Exception;
    void handleListCustomerEvent(ListCustomerEvent event) throws Exception;
    void handlePersistanceEvent(PersistanceEvent event) throws Exception;







    void printIndexNotFound();

    void setViewMode(String viewMode);
    String getViewMode();
    void printExitProgramm();
    void listContent(StorageManager management);
    void addNewElementEventListener(AddEventListener listener);

    void removeNewElementEventListener(AddEventListener listener);

    void handleAddEvent(AddEvent addEvent) throws Exception;
    boolean setAddHandler(addEventHandler handler);
    addEventHandler getAddHandler();
    void listContentByTyp(StorageManager management, String type);
    static final String MODE_ACTIVE = "activ";
    static final String MODE_DEACTIVE = "deactiv";
    void listHazards(StorageManager management);
    void printObjectNotFound() throws Exception;


    void printConfigInstructions();

    void printInspection(Cargo cargo);
    void printInspectInstruction();

    void inspect(StorageManager management, int id);



    void listConfig();

    void printSaveLoad();

    void saveJOS(StorageManager management);

    void loadJOS(StorageManager management);

    void saveJDB(StorageManager management);

    void loadJDB(StorageManager management);

    void load(StorageManager management, int position);

    void save(StorageManager management, int position);

    void setNetwork(boolean tcp, boolean udp, StorageManager management);
}
