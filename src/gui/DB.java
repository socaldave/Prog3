package gui;

import exceptions.CustomerNotFoundException;
import exceptions.NoCustomerToRemove;
import exceptions.UnvalidCargoException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.DataFormat;
import network.tcp.TcpClient;
import network.udp.UdpClient;
import saveLoad.JDP.JDP_Save_Load;
import saveLoad.JOS.JOS_Save_Load;
import storageContract.administration.Customer;
import storageContract.administration.CustomerManager;
import storageContract.administration.Storage;
import storageContract.administration.StorageManager;
import storageContract.cargo.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class DB {
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
    public static final int MAX_CAPACITY = 5;
    public int customerId = 10000;
    CustomerManager customerManagement;
    StorageManager manager;
    JDP_Save_Load jdb = new JDP_Save_Load();
    JOS_Save_Load jos = new JOS_Save_Load();
    TcpClient tcp;
    UdpClient udp;


    ObservableList<CargoModel> cargoObserver = FXCollections.observableArrayList();
    ObservableList<CustomerModel> customerObserver = FXCollections.observableArrayList();

    public DB() {
        try{
            tcp = new TcpClient();
            udp = new UdpClient();
        } catch (Exception e){

        }
        this.customerManagement = new CustomerManager(new ArrayList<Customer>());
        this.manager = new StorageManager();
        manager.storage.changeSize(10);
    }

    public boolean updateCargoTableView() {
        cargoObserver.clear();
        for (Cargo cargo : manager.storage) {
            CargoModel cargoViewModel = new CargoModel(cargo);
            cargoObserver.add(cargoViewModel);
        }
        return true;
    }
    public boolean addNewCargo(String customerName, double price, int days , boolean flammable, boolean radioactive, boolean explosive
            , boolean toxic, String type, Boolean pressured, Boolean fragile) throws CustomerNotFoundException, UnvalidCargoException {

        if (customerName == null || customerName.equals("")) return false;
        Customer customer;
        if (customerManagement.getCustomerWithName(customerName)!=null) {
            //customerManagement.add(new Customer(herstellerName));
        } else {
            this.updateCustomerTableView();
        }
        Collection<Hazard> harzards = new HashSet<Hazard>();

        if(flammable) harzards.add(Hazard.flammable);
        if(radioactive) harzards.add(Hazard.radioactive);
        if(explosive) harzards.add(Hazard.explosive);
        if(toxic) harzards.add(Hazard.toxic);

        System.out.println("add customer 3: "+ customerManagement.getCustomerWithName(customerName).getId());
        Cargo cargo;
        switch (type) {
            case "Cargo":
                cargo = new CargoImpl(customerManagement.getCustomerWithName(customerName), BigDecimal.valueOf(price), Duration.ofDays(days),harzards);
                break;
            case "LiquidBulkCargo":
                cargo = new LiquidBulkCargoImpl(customerManagement.getCustomerWithName(customerName), BigDecimal.valueOf(price), Duration.ofDays(days),harzards,pressured);  break;
            case "MixedCargoLiquidBulkAndUnitised":
               // cargo = new MixedCargoLiquidBulkAndUnitisedImpl(customerManagement.getCustomerWithName(customerName), BigDecimal.valueOf(price), Duration.ofDays(days),harzards, pressured, fragile);  break;
            case "UnitisedCargo":
                cargo = new UnitisedCargoImpl(customerManagement.getCustomerWithName(customerName), BigDecimal.valueOf(price), Duration.ofDays(days),harzards,pressured,fragile);  break;
            default:
                cargo = null; break;
        }

        if(manager.addCargo(cargo)) {
            manager.list();
            this.updateCargoTableView();
            this.updateCustomerTableView();
            return true;
        }
        return false;
    }
    public int getId(){
        return this.customerId++;
    }
    public boolean removeCargo(int index) throws UnvalidCargoException {
        if (manager.removeCargo(index) != false) {
            this.updateCargoTableView();
            this.updateCustomerTableView();
            return true;
        }
        return false;
    }
    public boolean addCustomer(Customer customer) {
        if(customerManagement.add(customer)) {
            this.updateCustomerTableView();
            return true;
        }
        return false;
    }
    public boolean removeCustomer(String name) throws NoCustomerToRemove, UnvalidCargoException {
        System.out.println("remove: "+customerManagement.getCustomerWithName(name));
        if(this.removeAllContentOfCustomer(name) && customerManagement.removeCustomerByName(name)) {
            this.updateCustomerTableView();
            this.updateCargoTableView();
            customerManagement.list();
            return true;
        }
        System.out.println("pass removing");
        return false;
    }
    public void updateCustomerTableView() {
        customerObserver.clear();
        for(Customer customer : customerManagement.getList()) {
            CustomerModel customerViewModel = new CustomerModel((Customer) customer);
            customerObserver.add(customerViewModel);
        }
    }
    private boolean removeAllContentOfCustomer(String name) throws UnvalidCargoException {
        Storage<Cargo> cargos = this.manager.storage;
        try {
            for (int i = 0; i < cargos.size(); i++) {
                Cargo cargo = cargos.get(i);
                if(name.equals(cargo.getOwner().getName())) {
                    manager.removeCargo(i);
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    public void load(StorageManager management){

    }}
