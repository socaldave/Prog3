package Simulations;

import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class InsertThread extends Thread {

    private Object waitMonitor;
    private Object producentsWaitMonitor;
    private Object consumentsMonitor;

    static volatile int counter;
    private ArrayList<StorageManager> mangement;
    private String customerName;
    private CustomerManager customerManagement;

    public InsertThread(
      ArrayList<StorageManager> mangement,
                CustomerManager customerManagement,
                String customerName,
                Object waitMonitor,
                Object producentsWaitMonitor,
                Object consumentsMonitor) {

            this.mangement = mangement;
            this.customerManagement = customerManagement;
            this.customerName = customerName;
            this.waitMonitor = waitMonitor;
            this.producentsWaitMonitor = producentsWaitMonitor;
            this.consumentsMonitor = consumentsMonitor;
            this.counter = 1;
        }

        public void run(){
            System.out.println("Starting thread");
            while(true){
                synchronized (producentsWaitMonitor) {
                    Random randomObj = new Random();

                    int zahl = (int)((Math.random()) * mangement.get(0).customerManager.getList().size() + 1);
                    //System.out.println(zahl);

                    int lagerDauer = (int)((Math.random()) * 22 + 8);
                    double price = (double)((Math.random()) * 12 + 1);
                    BigDecimal valueEur = new BigDecimal(price);
                    Duration duration = Duration.ofDays(lagerDauer);
                    Cargo cargo = new CargoImpl(this.mangement.get(0).customerManager.getList().get(zahl-1),valueEur,duration,null);
                    StorageManager contentManager = (StorageManager) this.mangement.toArray()[randomObj.nextInt(this.mangement.size())];
                    try {
                        if (contentManager.addCargo(cargo)) {
                            counter++;
                            System.out.println(this.getName() + "  " + "Cargo Number" + counter + " added succesfully");
                        } else {
                            synchronized (waitMonitor) {
                                int tryTimes = 0;

                                do{//try to add content to manager
                                    tryTimes++;
                                    try {
                                        System.out.println("[" + this.getName() + "] Cargo " + counter + " cannot be added. Attempt: " + tryTimes);
                                        synchronized (consumentsMonitor) {
                                            consumentsMonitor.notify();
                                        }
                                        waitMonitor.wait();
                                        if(tryTimes>5) { //max 6 versuche
                                            System.out.println("All storage spaces are full.");
                                            break;
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }while (!contentManager.addCargo(cargo));
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


