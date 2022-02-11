package Simulations;

import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ProducerUntimed extends Thread {

    static volatile int counter;
    private StorageManager manager;
    private String producerName;
    private CustomerManager customerManagement;

    public ProducerUntimed(StorageManager manager, CustomerManager customerStore,  String threadName){


        this.manager = manager;
        this.customerManagement = customerStore;
        this.producerName = threadName;
    }

    public void run(){
        System.out.println("Starting thread");
        while(true){
            synchronized (manager.storage){
                if(manager.storage.size() != 10) {
                    int zahl = (int)((Math.random()) * manager.customerManager.getList().size() + 1);
                    //System.out.println(zahl);
                    int lagerDauer = (int)((Math.random()) * 22 + 8);
                    double price = (double)((Math.random()) * 12 + 1);
                    BigDecimal valueEur = new BigDecimal(price);
                    Duration duration = Duration.ofDays(lagerDauer);
                    Cargo cargo = new CargoImpl(this.manager.customerManager.getList().get(zahl-1),valueEur,duration,null);
                    manager.addCargo(cargo);
                    System.out.println( producerName + " Thread: "  + "Cargo Number" + counter + " added succesfully");
                } else {
                    System.out.println( producerName + " Thread: "  + "Waiting");
                }
            }

        }

    }
}
