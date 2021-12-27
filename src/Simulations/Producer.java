package Simulations;

import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Random;

public class Producer extends Thread{
    private StorageManager manager;
    private String customer = "David";
    private CustomerManager customerManager;

    public Producer(StorageManager manager,
                    CustomerManager customerManagement,
                    String customerName){

        this.customerManager = customerManagement;
        this.manager = manager;
        this.customer = customerName;

        customerManager.add(new CustomerImpl(customerName , new BigDecimal(1000), Duration.ofDays(1000)));
    }

    public void put(){
        Random random = new Random();
        int zahl = (int)((Math.random()) * 5);
        System.out.println(zahl);

        int lagerDauer = (int)((Math.random()) * 20000);
        double price = (double)((Math.random()) * 12 + 1);
        BigDecimal value = new BigDecimal(price);
        Duration duration = Duration.ofDays(lagerDauer);
        Cargo cargo = new CargoImpl(this.manager.customerManager.getList().get(zahl-1),value,duration,null);



    }
}
