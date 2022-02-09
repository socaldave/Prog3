package Simulations;

import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Producer extends Thread{
    private Object waitMonitor;
    private Object producerMonitor;
    private Object consumerMoniter;

    static volatile int counter;
    private StorageManager manager;
    private String producerName;
    private CustomerManager customerManagement;

    public Producer(
            StorageManager mangement,
            CustomerManager customerManagement,
            String producerName,
            Object waitMonitor,
            Object producentsWaitMonitor,
            Object consumentsMonitor) {

        this.manager = mangement;
        this.customerManagement = customerManagement;
        this.producerName = producerName;
        this.waitMonitor = waitMonitor;
        this.producerMonitor = producentsWaitMonitor;
        this.consumerMoniter = consumentsMonitor;
        this.counter = 1;
    }

    public void run(){
        System.out.println("Starting thread");
        while(true){
            synchronized (producerMonitor) {


                int zahl = (int)((Math.random()) * manager.customerManager.getList().size() + 1);
                //System.out.println(zahl);

                int lagerDauer = (int)((Math.random()) * 22 + 8);
                double price = (double)((Math.random()) * 12 + 1);
                BigDecimal valueEur = new BigDecimal(price);
                Duration duration = Duration.ofDays(lagerDauer);
                Cargo cargo = new CargoImpl(this.manager.customerManager.getList().get(zahl-1),valueEur,duration,null);

                try {
                    if (manager.addCargo(cargo)) {
                        counter++;
                        System.out.println( producerName + " Thread: "  + "Cargo Number" + counter + " added succesfully");
                    } else {
                        synchronized (waitMonitor) {
                            do{
                                try {
                                    System.out.println("Producer Thread : Cargo " + counter + " cannot be added. Waiting");
                                    synchronized (consumerMoniter) {
                                        consumerMoniter.notify();
                                    }
                                    waitMonitor.wait();

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }while (!manager.addCargo(cargo));
                            counter++;
                            System.out.println(this.getName() + "  stores cargo from " + cargo.getOwner().getName());
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
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
