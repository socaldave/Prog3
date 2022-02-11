package Simulations;

import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;

import java.util.Random;

public class RandomConsumer extends Thread {
    private Object waitMonitor;
    private Object consumerMoniter;
    private StorageManager manager;
    private CustomerManager customerStore;
    private String name;

    public RandomConsumer(StorageManager manager, CustomerManager customerStore,  String threadName){


        this.manager = manager;
        this.customerStore = customerStore;
        this.name = threadName;
    }

    public void run(){
        while (true){
            synchronized (manager.storage){
                if(manager.getStorage().size() == 0){
                    System.out.println( name +" Nothing to consume... waiting");

                } else {
                    Random random = new Random();
                    Integer cargoSpot = manager.returnOldestInspectionId();
                    System.out.println("Deleting Cargo : " + manager.storage.get(cargoSpot).toString());
                    manager.removeCargo(cargoSpot);

                }
            }

        }


        }
    }

