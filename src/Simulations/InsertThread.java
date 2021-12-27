package Simulations;

import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class InsertThread extends Thread {
    private Object moniter;
    private Object moniter2;
    private Object moniter3;

    static volatile int counter;
    private StorageManager manager;
    private String customer = "David";
    private CustomerManager customerManager;

    public InsertThread(StorageManager managers,
                        CustomerManager customerManagement,
                        String customerName,
                        Object waitMonitor,
                        Object moniter2,
                        Object moniter3){

        this.manager = managers;
        this.customerManager = customerManagement;
        this.customer = customerName;
        this.moniter = waitMonitor;
        this.moniter2 = moniter2;
        this.moniter3 = moniter3;
        this.counter = 1;
    }


    public void run() {
        while(true){
            synchronized (moniter){
                Random random = new Random();
                int zahl = (int)((Math.random()) * 5);
                System.out.println(zahl);

                int lagerDauer = (int)((Math.random()) * 20000);
                double price = (double)((Math.random()) * 12 + 1);
                BigDecimal value = new BigDecimal(price);
                Duration duration = Duration.ofDays(lagerDauer);
                Cargo cargo = new CargoImpl(this.manager.customerManager.getList().get(zahl-1),value,duration,null);

                try{
                    if (manager.addCargo(cargo)) {
                        counter++;
                        System.out.println(this.getName() + "  " + "Fracht Nr." + counter + " wurde eingelagert");
                    } else {
                        synchronized (moniter2){
                            int attempts = 0;
                            do{
                                attempts++;
                                try{
                                    System.out.println( this.getName() + " Cargo " + counter + " cannot be inserted! Attempts: " + attempts);
                                    synchronized (moniter3){
                                        moniter3.notify();
                                    }
                                    moniter2.wait();
                                    if(attempts>5) { //max 6 versuche
                                        System.out.println("All storage spots are full.");
                                        break;
                                    }
                                } catch (InterruptedException e  ){
                                    e.printStackTrace();
                                }
                            } while (!manager.addCargo(cargo));
                            counter++;
                            System.out.println(this.getName() + " is storing cargo from " + cargo.getOwner().getName());
                        }
                    }
                } catch (Exception e ){
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
