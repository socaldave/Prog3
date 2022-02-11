import Simulations.ProducerUntimed;
import Simulations.RandomConsumer;
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


        StorageManager v1 = new StorageManager(a1,customerStore);

        Thread einlagerung1 = new ProducerUntimed(v1, customerStore, name );

        Thread auslagerung1 = new RandomConsumer(v1,customerStore, "Consumer 1");


        einlagerung1.start();

        auslagerung1.start();


    }

}
