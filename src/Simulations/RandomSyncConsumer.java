package Simulations;

import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;

import java.util.List;
import java.util.Random;

public class RandomSyncConsumer extends Thread {

    private Object waitMonitor;
    private Object consumerMoniter;
    private StorageManager manager;
    private CustomerManager customerStore;
    private String name;

    public RandomSyncConsumer(StorageManager manager, CustomerManager customerStore, Object waitMonitor, Object consumerMonitor, String threadName){
        this.waitMonitor = waitMonitor;
        this.consumerMoniter = consumerMonitor;
        this.manager = manager;
        this.customerStore = customerStore;
        this.name = threadName;
        List<Cargo> cargoList;
    }

    public void run(){
        while (true){
            synchronized(consumerMoniter) {
                try {
                    if(manager.getStorage().size() == 0){
                        System.out.println( name +" Nothing to consume... waiting");
                        consumerMoniter.wait();
                    } else {
                        Random random = new Random();
                        Integer cargoSpot = manager.returnOldestInspectionId();
                        System.out.println("Deleting Cargo : " + manager.storage.get(cargoSpot).toString());
                        manager.removeCargo(cargoSpot);

                        synchronized(waitMonitor) { waitMonitor.notify();}

                    }
                }
                catch(InterruptedException ex) {
                    System.out.println("InterruptedEx.:" + ex);
                }
            }
        }
    }
}
