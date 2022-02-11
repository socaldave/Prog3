package test.Persistance;

import org.junit.jupiter.api.Test;
import saveLoad.JOS.JOS_Save_Load;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;
import storageContract.cargo.Hazard;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class JOS_Save_LoadTest {

    StorageManager management;
    CustomerManager customers;
    Cargo cargo;
    Cargo cargo2;
    Cargo cargo3;
    Customer customer;
    Customer customer2;
    Collection<Hazard> hazards;

    JOS_Save_Load jos;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        jos = new JOS_Save_Load();
        customers = new CustomerManager(new ArrayList<>());
        management = new StorageManager(customers);
        customer = new CustomerImpl("David", BigDecimal.valueOf(3), Duration.ofDays(90));
        customer2 = new CustomerImpl("Jasmin", BigDecimal.valueOf(3), Duration.ofDays(90));
        hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        cargo = new CargoImpl(customer, BigDecimal.valueOf(3), Duration.ofDays(2), hazards);
        cargo2 = new CargoImpl(customer, BigDecimal.valueOf(3), Duration.ofDays(2), hazards);
        cargo3 = new CargoImpl(customer, BigDecimal.valueOf(3), Duration.ofDays(2), hazards);
        management.addCargo(cargo);
    }
    @Test
    void saveDB() {
    }

    @Test
    void loadDB() {
    }
}