package test.Persistance;

import org.junit.jupiter.api.Test;
import saveLoad.JDP.JDP_Save_Load;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;
import storageContract.cargo.Hazard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JDP_Save_LoadTest {

    StorageManager management;
    CustomerManager customers;
    Cargo cargo;
    Cargo cargo2;
    Cargo cargo3;
    Customer customer;
    Customer customer2;
    Collection<Hazard> hazards;

    JDP_Save_Load jdp;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        jdp = new JDP_Save_Load();
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
        management.customerManager.add(customer);
        management.addCargo(cargo);
        File file = new File("db/testStore.txt");
        try (FileOutputStream fos = new FileOutputStream(file);) {
            assertTrue(this.jdp.saveDB(fos, management));
        } catch (Exception e) {
            System.out.println("saveJOS error -> " + e.getMessage());
        }
    }

    @Test
    void loadDB() {
        try (FileInputStream fos = new FileInputStream(new File("db/testStore.txt"));) {
            assertTrue(this.jdp.loadDB(fos, management));

        } catch (Exception e) {
            System.out.println("loadJOS error -> " + e.getMessage());
        }
    }


}