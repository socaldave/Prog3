package Simulations;

import exceptions.CapacityOverFlowException;
import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;

import java.util.ArrayList;

public class Organization {
    private ArrayList<StorageManager> managements;
    private String threadID;
    private CustomerManager customerStore;

    public Organization(String nameThread, ArrayList<StorageManager> managements, CustomerManager customerStore){
        this.threadID = nameThread;
        this.managements = managements;
        this.customerStore = customerStore;
    }
    public boolean moveContent() throws CapacityOverFlowException {
        StorageManager fullManager = this.findFullManager();
        if(fullManager == null)
            return false;
        Cargo oldestContent = fullManager.getOldestContent();
        if(oldestContent == null)
            return false;
        StorageManager notFullManager = this.findNotFullManager();
        if(notFullManager == null)
            return false;
        if(!notFullManager.addCargo(oldestContent))
            return false;
        if(!fullManager.removeCargo(fullManager.storage.indexOf(oldestContent))){
            System.out.println("Removed Cargo");
            return false;
        }

        return true;
    }
    public StorageManager findNotFullManager(){
        System.out.println("Ein Lager mit freier Kapazität wird gesucht...");
        if(managements == null || customerStore == null || threadID == null){return null;}
        if (this.freeCap()) {
            System.out.println("["+ threadID +"]:   alle Lager sind voll. exit...");
            System.exit(0);
        }
        for(StorageManager management : managements){
            if(management.getCurrentStorageCapacity() > -1){
                System.out.println("Lager mit freier Kapazität gefunden.");
                return management;
            }
        }
        System.out.println("["+ threadID +"] No available DB founded" );
        return null;
    }

    public StorageManager findFullManager(){

        System.out.println("Ein Lager ohne Kapazität wird gesucht...");
        if(managements == null || customerStore == null || threadID == null){return null;}
        if (this.freeCap()) {
            System.out.println("############### ["+ threadID +"] Alle Lager sind voll! exit...");
            System.exit(0);
        }
        for(StorageManager verwaltung : managements){
            if(verwaltung.getCurrentStorageCapacity() < 0){
                return verwaltung;
            }
        }
        System.out.println("["+ threadID +"] Kein freies managment vorhanden. " );
        return null;
    }

    public boolean freeCap() {
        int count = 0;
        int check = 0;
        for(StorageManager verwaltung : managements){
            if(verwaltung.getCurrentStorageCapacity() <0) count++;
            check++;
        }
        if(count == check) return true;
        return false;
    }
}
