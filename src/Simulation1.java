import Simulations.SyncConsumer;
import Simulations.Producer;
import storageContract.administration.*;
import storageContract.cargo.Cargo;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;

public class Simulation1 {

    public static void main(String[] args) {
        CustomerManager customerStore = new CustomerManager(new ArrayList<Customer>());
        Customer h1 = new CustomerImpl("David", BigDecimal.valueOf(100), Duration.ofDays(42));
        Customer h2 = new CustomerImpl("Paul", BigDecimal.valueOf(123), Duration.ofDays(21));
        Customer h3 = new CustomerImpl("Carl", BigDecimal.valueOf(312), Duration.ofDays(31));
        Customer h4 = new CustomerImpl("Phillip", BigDecimal.valueOf(3234), Duration.ofDays(10));
        Customer h5 = new CustomerImpl("Jakob", BigDecimal.valueOf(765), Duration.ofDays(39));

        customerStore.add(h1);
        customerStore.add(h2);
        customerStore.add(h3);
        customerStore.add(h4);
        customerStore.add(h5);

        Storage<Cargo> a1 = new Storage<>();
        a1.changeSize(Integer.parseInt(args[0]));

        String name = "Producer 1";
        String name2 = "Producer 2";

        StorageManager v1 = new StorageManager(a1,customerStore);

        Object waitMonitor = new Object();
        Object producentsWaitMonitor = new Object();
        Object consumentsMonitor = new Object();

        Thread einlagerung1 = new Producer(v1, customerStore, name ,waitMonitor,producentsWaitMonitor, consumentsMonitor);
        Thread einlagerung2 = new Producer(v1, customerStore, name2 ,waitMonitor,producentsWaitMonitor, consumentsMonitor);
        Thread auslagerung1 = new SyncConsumer(v1,customerStore,waitMonitor, consumentsMonitor, "Consumer 1");
        Thread auslagerung2 = new SyncConsumer(v1,customerStore,waitMonitor, consumentsMonitor , "Consumer 2");

        einlagerung1.start();
        einlagerung2.start();
        auslagerung1.start();
        auslagerung2.start();

    }

}
