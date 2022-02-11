package test.administration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class StorageManagerTest {

    StorageManager manager;
    Customer c1;
    Cargo cargo2;
    Customer c2;
    Cargo cargo1;
    BigDecimal price;
    Duration duration;
    @BeforeEach

    void setUp() {
        manager = new StorageManager();
        manager.storage.changeSize(10);
        c1 = new CustomerImpl("david", BigDecimal.valueOf(999333), Duration.ofDays(90));
        c2 = new CustomerImpl("paul", BigDecimal.valueOf(9193233), Duration.ofDays(45));
        manager.customerManager.add(c1);
        //manager.customerManager.add(c2);
        price = BigDecimal.valueOf(2000);
        duration = Duration.ofDays(30);
    }

    @Test
    void addValidCargo() {
        cargo1 = new CargoImpl(c1,price,Duration.ofDays(10),null);
        boolean check = manager.storage.add(cargo1);
        Assertions.assertEquals(true, check);

    }

    @Test
    void addInvalidCustomerCargo() {
        cargo1 = new CargoImpl(c2,price,Duration.ofDays(10),null);
        boolean check = manager.addCargo(cargo1);
        Assertions.assertEquals(false, check);
    }

    @Test
    void removeCargo() {
        cargo1 = new CargoImpl(c1,price,Duration.ofDays(10),null);
        boolean check = manager.addCargo(cargo1);
        boolean check2 = manager.removeCargo(0);
    }

    @Test
    void returnOldestInspectionId() {
        cargo1 = new CargoImpl(c1,price,Duration.ofDays(10),null);
         manager.addCargo(cargo1);
        manager.Inspection(0);
        cargo2 = new CargoImpl(c1,price,Duration.ofDays(10),null);
        manager.addCargo(cargo2);
        manager.Inspection(1);
        Assertions.assertEquals(0, manager.returnOldestInspectionId());

    }

    @Test
    void getCargoStorageNumber() {
        cargo1 = new CargoImpl(c1,price,Duration.ofDays(10),null);
        manager.addCargo(cargo1);
        cargo2 = new CargoImpl(c1,price,Duration.ofDays(10),null);
        manager.addCargo(cargo2);
        Assertions.assertEquals(1,manager.getCargoStorageNumber(cargo2));
    }

    @Test
    void getCurrentStorageCapacity() {
        Assertions.assertEquals(0,manager.getCurrentStorageCapacity());
    }

    @Test
    void getNumberOfCargoStoredCusotmerExists() {
        cargo1 = new CargoImpl(c1,price,Duration.ofDays(10),null);
        manager.addCargo(cargo1);
        cargo2 = new CargoImpl(c1,price,Duration.ofDays(10),null);
        manager.addCargo(cargo2);
        Assertions.assertEquals(2,manager.getNumberOfCargoStored(c1.getName()));
    }

    @Test
    void getNumberOfCargoStoredCustomerNonExists() {
        cargo1 = new CargoImpl(c1,price,Duration.ofDays(10),null);
        manager.addCargo(cargo1);
        cargo2 = new CargoImpl(c1,price,Duration.ofDays(10),null);
        manager.addCargo(cargo2);
        Assertions.assertEquals(0,manager.getNumberOfCargoStored(c2.getName()));
    }

    @Test
    void checkExists() {
        cargo1 = new CargoImpl(c1,price,Duration.ofDays(10),null);
        manager.addCargo(cargo1);
        Assertions.assertEquals(true,manager.checkExists(cargo1));
    }

    @Test
    void getOldestContent() {
        cargo1 = new CargoImpl(c1,price,Duration.ofDays(10),null);
        manager.addCargo(cargo1);
        manager.Inspection(0);
        cargo2 = new CargoImpl(c1,price,Duration.ofDays(10),null);
        manager.addCargo(cargo2);
        manager.Inspection(1);
        Assertions.assertEquals(cargo1, manager.getOldestContent());

    }

    @Test
    void inspection() {
        cargo1 = new CargoImpl(c1,price,Duration.ofDays(10),null);
        manager.addCargo(cargo1);
        boolean check = manager.Inspection(0);
        Assertions.assertEquals(true, check);
    }

    @Test
    void list() {
    }
}