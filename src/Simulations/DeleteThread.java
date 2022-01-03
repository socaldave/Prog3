package Simulations;

import exceptions.CapacityOverFlowException;
import storageContract.administration.Customer;
import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;

import java.util.ArrayList;

public class DeleteThread extends Thread {
    private Object waitMonitor;
    private Object comsumentsMonitor;
    private ArrayList<StorageManager> managements;
    private CustomerManager customerStore;

    public DeleteThread(ArrayList<StorageManager> managements, CustomerManager customerStore, Object waitMonitor, Object comsumentsMonitor){
        this.waitMonitor = waitMonitor;
        this.comsumentsMonitor = comsumentsMonitor;
        this.managements = managements;
        this.customerStore = customerStore;
    }

    public void run(){

        while (true){

            synchronized(comsumentsMonitor) {

                try {
                    System.out.println("SyncConsumer Thread: Starting");

                    comsumentsMonitor.wait();
                    System.out.println("[Outsourcing "+this.toString()+"] -> active" );
                    Organization reorganize = new Organization(this.getName(), managements, customerStore);
                    reorganize.moveContent();
                    synchronized(waitMonitor) {waitMonitor.notify();}
                }
                catch(InterruptedException | CapacityOverFlowException ex) {
                    System.out.println("InterruptedEx.:" + ex);
                }

            }
        }

    }

}
