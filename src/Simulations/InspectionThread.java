package Simulations;

import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class InspectionThread extends Thread{
    private Object waitMonitor;
    private Object consumerMoniter;
    private StorageManager manager;
    private CustomerManager customerStore;
    private String name;

    public InspectionThread(StorageManager manager, CustomerManager customerStore, Object waitMonitor, Object consumerMonitor, String threadName){
        this.waitMonitor = waitMonitor;
        this.consumerMoniter = consumerMonitor;
        this.manager = manager;
        this.customerStore = customerStore;
        this.name = threadName;
    }

    public void run(){
        System.out.println(name + "started");
        while (true){
            //synchronized(consumerMoniter) {

                    if(manager.getStorage().size() == 0){
                        System.out.println( name +" Nothing to inspect... waiting");

                    } else {
                        Integer cargoSpot = (int)(Math.random() * manager.storage.size());
                        System.out.println("Inspecting Cargo : " + manager.storage.get(cargoSpot).toString());
                        manager.Inspection(cargoSpot);
                    }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
