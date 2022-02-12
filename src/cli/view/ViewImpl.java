package cli.view;


import events.events.*;
import events.handlers.*;
import events.listeners.messages.AddEvent;
import events.listeners.messages.AddEventListener;
import events.listeners.modes.*;
import network.tcp.TcpClient;
import network.udp.UdpClient;
import saveLoad.JDP.JDP_Save_Load;
import saveLoad.JOS.JOS_Save_Load;
import storageContract.administration.Customer;

import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;
import storageContract.cargo.Hazard;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Observable;

public class ViewImpl extends Observable implements View {
    InputEventHandler inputHandler;


    addEventHandler addHandler;
    AddEvent addEvent;

    AddCargoEventHandler addCargoEventHandler;

    AddCustomerEventHandler addCustomerEventHandler;
    DeleteCustomerEventHandler deleteCustomerEventHandler;
    DeleteCargoEventHandler deleteCargoEventHandler;
    InspectionEventHandler inspectionEventHandler;
    ListCargoEventHandler listCargoEventHandler;
    ListCustomerEventHandler listCustomerEventHandler;
    PersistanceEventHandler persistanceEventHandler;
    ListHazardsEventHandler listHazardsEventHandler;



    String viewMode;
    PrintStream ps;
    BufferedReader reader;

    JDP_Save_Load jdb;
    JOS_Save_Load jos;

    TcpClient tcp;
    UdpClient udp;

    public ViewImpl(InputStream is, OutputStream os) {
        this.viewMode = "";
        this.ps = new PrintStream(os);
        this.reader = new BufferedReader(new InputStreamReader(is));
    }

    @Override
    public PrintStream getPrintStream() {
        return ps;
    }

    @Override
    public BufferedReader getReader() {
        return reader;
    }

    @Override
    public boolean setInputEventHandler(InputEventHandler handler) {
        this.inputHandler = handler;
        return true;
    }

    @Override
    public boolean setAddHandler(addEventHandler handler) {
        this.addHandler = handler;
        return true;
    }

    @Override
    public addEventHandler getAddHandler() {
        return this.addHandler;
    }


    @Override
    public void printCargo(Cargo cargo) {
        ps.println("# id: " + cargo.getStorageNumber()
                + " | type: " + cargo.getType()
                + " | value: " + cargo.getValue()
                + " | harzards:" + cargo.getHazards().toString()
                + " | storage duration (days): " + cargo.getDurationOfStorage().toDays()
                + " | last inspection: " + cargo.getLastInspectionDate().toString());
    }


    @Override
    public void initView() {
        String lastCommand;
        String currentCommand;
        try {
            do {
                this.printInstruction();
                ps.print("~$:  ");
                lastCommand = "";
                currentCommand = reader.readLine().trim();
                InputEvent inputEvent = new InputEvent(this, currentCommand, lastCommand);
                this.inputHandler.handle(inputEvent);
                ps.print("~$:  ");
                if (this.viewMode.equals(MODE_ACTIVE)) {
                    String nextCommand = reader.readLine().trim();
                    inputEvent = new InputEvent(this, nextCommand, inputEvent.getCurrentCommand());
                    this.inputHandler.handle(inputEvent);
                }
            } while (!currentCommand.equals("exit"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            initView();
        }
    }

    @Override
    public void printInstruction() {
        ps.println("Please enter command, supported modes are:");
        ps.println("# To enter add mode         : ':c'");
        ps.println("# To enter delete mode      : ':d'");
        ps.println("# To enter list mode        : ':r'");
        ps.println("# To enter edit mode        : ':u'");
        ps.println("# To enter save/load mode   : ':p'");
        ps.println("# To enter config mode      : ':config'");
        ps.println("# To close programm         : ' exit'");
    }

    @Override
    public void printAddInstruction() {
        ps.println("# add new customer, please enter: [Kundenname]");
        ps.println("# add new cargo, please enter:    ");
        ps.println("[Frachttyp] " +
                "[Kundenname] " +
                "[Wert] " +
                "[Einlagerungsdauerin Sekunden] " +
                "[kommaseparierte Gefahrenstoffe, einzelnes Komma für keine] " +
                "[unter Druck(y/n)] " +
                "[zerbrechlich (y/n)] " +
                "[fest(y/n)]");
        ps.println("# go back to mode: exit");
    }

    @Override
    public void printDeleteInstruction() {
        ps.println("# To delete customer, enter: [name_of_customer]");
        ps.println("# To delete cargo, enter:    [storage_number]");
        ps.println("# To go back to mode:        exit");
    }

    @Override
    public void printListInstruction() {
        ps.println("# To get list customers enter: customer");
        ps.println("# To get list storage:         cargo [optional_type_filter]");
        ps.println("# To get list of hazards:      harzard");
        ps.println("# To go back to mode:          exit");
    }

    @Override
    public void printLostCargo(Cargo cargo) {
        ps.println(cargo.getType() + " removed of cargo id: " + cargo.getStorageNumber());
        ps.println("hazards: " + cargo.getHazards().toString());
    }

    @Override
    public void printAddCustomer(Customer customer) {
        ps.println("customer " + customer.getName() + " added");
    }

    @Override
    public void printAddedCustomerObserver() {
        ps.println("customer added... Notification triggered through observer" );
    }

    @Override
    public void printUnsupportCommand() {
        ps.println("Unsupported mode");
    }

    @Override
    public void printLowCapacity(double capacityProcentRest) {
        ps.println("Low capacity, just " + capacityProcentRest + " free");
    }


    @Override
    public void printInvalidType() {
        ps.println("# Invalid Type entered");
    }

    @Override
    public void printInvalidCargoParams() {
        ps.println("# Invalid Paramaters entered for Cargo");
        ps.println("# Returning to Main Menu.... ");
    }


    @Override
    public void printCargoAdded() {
        ps.println("# Sucessfully added Cargo");
    }

    @Override
    public void printCustomerAdded(String name) {
        ps.println("# Sucessfully added " + name);
    }

    @Override
    public void printCustomerNontexistent(String name) {
        ps.println("# Customer " + name +  " does not exist");
    }

    @Override
    public void listCustomer(StorageManager management)
            //throws CustomerNotFoundException
            {
        List<Customer> customerList = management.customerManager.getList();
        if (customerList.size() == 0) {
            ps.println("no customer available...");
        } else {
            for (int i = 0; i < customerList.size(); i++) {
                Customer customer = customerList.get(i);
                ps.println("Customer: " + customer.getName() + " owns "
                        + management.getNumberOfCargoStored(customer.getName()) + " item/s");
            }
        }
    }

    @Override
    public void listContentOfOwner(StorageManager management, String customerName) {
        List<Cargo> listOfContent = management.getStorage();
        for (int i = 0; i < listOfContent.size(); i++) {
            Cargo _cargo = listOfContent.get(i);
            if (_cargo.getOwner().getName().equals(customerName)) {
                ps.println("# id: " + _cargo.getStorageNumber()
                        + " | type: " + _cargo.getType()
                        + " | value: " + _cargo.getValue()
                        + " | storage date: " + _cargo.getStorageDate().toString());
            }
        }
    }

    public void listContentByTyp(StorageManager management, String type) {
        List<Cargo> listOfContent = management.getStorage();
        for (int i = 0; i < listOfContent.size(); i++) {
            Cargo _cargo = listOfContent.get(i);
            if (_cargo.getType().toLowerCase().equals(type.toLowerCase())) {
                ps.println("# id: " + _cargo.getStorageNumber()
                        + " | type: " + _cargo.getType()
                        + " | value: " + _cargo.getValue()
                        + " | storage date: " + _cargo.getStorageDate().toString());
            }
        }
    }

    @Override
    public void listHazards(StorageManager management) {
        for (Cargo cargo : management.storage) {
            ps.println(cargo.harzardsToString());
        }
    }

    @Override
    public void listContentByName(StorageManager management, String customerName) {

    }

    @Override
    public void addInputEventListener(InputEventListener listener) {
        this.inputHandler.add(listener);
    }

    @Override
    public void handleInputEvent(InputEvent inputEvent) throws Exception {
        this.inputHandler.handle(inputEvent);
    }

    @Override
    public void setCargoEventHandler(AddCargoEventHandler AddCargoEventHandler) throws Exception {
        this.addCargoEventHandler = AddCargoEventHandler;
    }

    @Override
    public void setCustomerEventHandler(AddCustomerEventHandler addCustomerEventHandler) throws Exception {
        this.addCustomerEventHandler = addCustomerEventHandler;
    }

    @Override
    public void setDeleteCargoHandler(DeleteCargoEventHandler deleteCargoEventHandler) throws Exception {
        this.deleteCargoEventHandler = deleteCargoEventHandler;
    }

    @Override
    public void setDeleteCustomerHandler(DeleteCustomerEventHandler deleteCustomerEventHandler) throws Exception {
        this.deleteCustomerEventHandler = deleteCustomerEventHandler;
    }

    @Override
    public void setInspectionHandler(InspectionEventHandler inspectionEventHandler) throws Exception {
        this.inspectionEventHandler = inspectionEventHandler;
    }

    @Override
    public void setListCargoEventHandler(ListCargoEventHandler inputEvent) throws Exception {
        this.listCargoEventHandler = inputEvent;
    }

    @Override
    public void setListCustomerHandler(ListCustomerEventHandler inputEvent) throws Exception {
        this.listCustomerEventHandler = inputEvent;
    }

    @Override
    public void setPersistanceHander(PersistanceEventHandler persistanceEventHandler) throws Exception {
        this.persistanceEventHandler = persistanceEventHandler;
    }



    @Override
    public void handleAddCargoEvent(AddCargoEvent event) throws Exception {
         this.addCargoEventHandler.handle(event);
    }

    @Override
    public void handleAddCustomerEvent(AddCustomerEvent event) throws Exception {
        this.addCustomerEventHandler.handle(event);
    }

    @Override
    public void handleDeleteCustomerEvent(DeleteCustomerEvent event) throws Exception {
        this.deleteCustomerEventHandler.handle(event);
    }

    @Override
    public void handleDeleteCargoEvent(DeleteCargoEvent event) throws Exception {
        this.deleteCargoEventHandler.handle(event);
    }

    @Override
    public void handleInspectionEvent(InspectionEvent event) throws Exception {
        this.inspectionEventHandler.handle(event);
    }

    @Override
    public void handleListCargoEvent(ListCargoEvent event) throws Exception {
        this.listCargoEventHandler.handle(event);
    }

    @Override
    public void handleListCustomerEvent(ListCustomerEvent event) throws Exception {
        this.listCustomerEventHandler.handle(event);
    }


    @Override
    public void handlePersistanceEvent(PersistanceEvent event) throws Exception {
        persistanceEventHandler.handle(event);
    }

    @Override
    public void handleListHazardEvent(ListHazardsEvent event) throws Exception {
        listHazardsEventHandler.handle(event);
    }

    @Override
    public void addAddCargoListener(AddingCargoListener listener) throws Exception {
        addCargoEventHandler.add(listener);
    }

    @Override
    public void addAddCustomerListener(AddCustomerListener listener) throws Exception {
        addCustomerEventHandler.add(listener);
    }

    @Override
    public void addDeleteCustomerListener(DeleteCustomerListener listener) throws Exception {
        deleteCustomerEventHandler.add(listener);
    }

    @Override
    public void addDeleteCargoListener(DeleteCargoListener listener) throws Exception {
        deleteCargoEventHandler.add(listener);
    }

    @Override
    public void addInspectionListener(InspectionEventListener listener) throws Exception {
        inspectionEventHandler.add(listener);
    }

    @Override
    public void addListCargoListener(ListCargoListener listener) throws Exception {
        listCargoEventHandler.add(listener);
    }

    @Override
    public void addListCustomerListener(ListCustomerListener listener) throws Exception {
        listCustomerEventHandler.add(listener);
    }

    @Override
    public void addPersistanceListener(saveLoadListener listener) throws Exception {
        persistanceEventHandler.add(listener);
    }


    @Override
    public void printIndexNotFound() {
        ps.println("Index not found ...");
    }

    @Override
    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }

    @Override
    public String getViewMode() {
        return viewMode;
    }

    @Override
    public void printRemoveCustomer(String name) {
        ps.println("delete customer " + name + " successful");
    }

    @Override
    public void printRemoveCargo(int fachnummer) {
        ps.println("delete cargo on " + fachnummer + " succesful");
    }

    @Override
    public void printExitProgramm() {
        ps.println("Close Programm");
    }

    @Override
    public void listContent(StorageManager management) {
        for (Cargo _cargo : management.storage) {
            printCargo(_cargo);
        }
    }

    @Override
    public void addNewElementEventListener(AddEventListener listener) {
        this.addHandler.add(listener);
    }

    @Override
    public void removeNewElementEventListener(AddEventListener listener) {
        this.addHandler.remove(listener);
    }

    @Override
    public void handleAddEvent(AddEvent addEvent) throws Exception {
        this.addHandler.handle(addEvent);
        System.out.println("Handling event ");
    }

    @Override
    public void printObjectNotFound() {
        System.out.println("object not found");
    }

    @Override
    public void printInspection(Cargo cargo) {
        System.out.println("# id: " + cargo.getStorageNumber() + " | last inspection: " + cargo.getLastInspectionDate());
    }
    @Override
    public void printConfigInstructions() {
        System.out.println("# add cargolistener");
        System.out.println("# remove cargolistener");
        System.out.println("# add customerlistener");
        System.out.println("# remove customerlistener");
    }
    @Override
    public void printInspectInstruction() {
        System.out.println("# to inspect cargo type: [storageNumber]");
    }

    @Override
    public void inspect(StorageManager management, int id) {
        try {
            Cargo _cargo = management.storage.get(id);
            _cargo.setLastInspectionDate(new Date());
            this.printInspection(_cargo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.printObjectNotFound();
        }
    }


    @Override
    public void saveJOS(StorageManager management) {

        if (jos == null) this.jos = new JOS_Save_Load();
        File file = new File("db/storage.txt");
        try (FileOutputStream fos = new FileOutputStream(file);) {
            this.jos.saveDB(fos, management);
        } catch (Exception e) {
            System.out.println("saveJOS error -> " + e.getMessage());
        }
    }

    public void saveJOS(StorageManager management, String filePath) {
        if (jos == null) this.jos = new JOS_Save_Load();
        File file = new File(filePath);
        try (FileOutputStream fos = new FileOutputStream(file);) {
            this.jos.saveDB(fos, management);
        } catch (Exception e) {
            System.out.println("saveJOS error -> " + e.getMessage());
        }
    }

    @Override
    public void loadJDB(StorageManager management) {

        jdb = new JDP_Save_Load(management.customerManager);

        try (FileInputStream fos = new FileInputStream(new File("db/storage.tmp"));) {
            jdb.loadDB(fos, management);
        } catch (Exception e) {
            System.out.println("loadJDB error -> " + e.getMessage());
        }



    }

    @Override
    public void load(StorageManager management, int position) {
        if (jos == null) this.jos = new JOS_Save_Load(management.customerManager);
        try (FileInputStream fos = new FileInputStream(new File("db/store.txt"));) {
            this.jos.load(fos, management, position);
        } catch (Exception e) {
            System.out.println("load position error -> " + e.getMessage());
        }
    }

    @Override
    public void save(StorageManager management, int position) {
        if (jos == null) this.jos = new JOS_Save_Load();
        File file = new File("db/position.txt");
        try (FileOutputStream fos = new FileOutputStream(file);) {
            this.jos.save(fos, management,position);
        } catch (Exception e) {
            System.out.println("save position error -> " + e.getMessage());
        }
    }

    @Override
    public void setNetwork(boolean tcp, boolean udp, StorageManager management) {
        if (tcp || (tcp && udp)) {
            this.tcp = new TcpClient();
            StorageManager m = this.tcp.getData();
            if (m != null) m.resetContent(m);
            System.out.println("management loaded trough tcp client");
        } else if (udp && !tcp) {
            try {
                this.udp = new UdpClient();
                StorageManager m = this.udp.getData();
                if (m != null) m.resetContent(m);
                System.out.println("management loaded trough udp client");

            } catch (Exception e) {

            }
        }
    }

    @Override
    public void listContainedHazards(StorageManager manager) {
        System.out.println("Contained Hazard/s: ");
        for(Hazard hazard : manager.containedHazards){
            System.out.println(hazard.toString());
        }
    }

    @Override
    public void saveJDB(StorageManager management) {

        jdb = new JDP_Save_Load(management.customerManager);
        File file = new File("db/storage.tmp");
        try (FileOutputStream fos = new FileOutputStream(file);) {
            jdb.saveDB(fos,management);

        } catch(Exception e){}


    }



    @Override
    public void loadJOS(StorageManager management) {

        if (jos == null) this.jos = new JOS_Save_Load(management.customerManager);
        try (FileInputStream fos = new FileInputStream(new File("db/storage.txt"));) {
            this.jos.loadDB(fos, management);
        } catch (Exception e) {
            System.out.println("loadJOS error -> " + e.getMessage());
        }
    }



    @Override
    public void listConfig() {
        ps.println("# add    cargolistener");
        ps.println("# remove cargolistener");
        ps.println("# add    customerlistener");
        ps.println("# remove customerlistener");
    }

    @Override
    public void printSaveLoad() {
        ps.println("# saveJDB");
        ps.println("# loadJDB");
        ps.println("# saveJOS");
        ps.println("# loadJOS");
        ps.println("# save [position]");
        ps.println("# load [position]");
    }


    public void setAddCustomerEventHandler(AddCustomerEventHandler addCustomerEventHandler) {
        this.addCustomerEventHandler = addCustomerEventHandler;

    }

    @Override
    public void setListHazardsEventHandler(ListHazardsEventHandler listHazardsEventHandler) {
        this.listHazardsEventHandler = listHazardsEventHandler;
    }

    @Override
    public void addListHazardsListener(ListHazardListener listHazardListener) {
        if(listHazardsEventHandler != null)
            listHazardsEventHandler.add(listHazardListener);
    }

    //TODO Implement
    @Override
    public void printNotContainedHazards() {

    }

    @Override
    public void invalidHazardsParam() {
        ps.println("#Invalid Hazard Params. Please enter  (i) for contained and (E) for not contained");
    }

    public ListHazardsEventHandler getListHazardsEventHandler() {
        return listHazardsEventHandler;
    }
}