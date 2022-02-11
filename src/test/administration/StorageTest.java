package test.administration;

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

class StorageTest {
    StorageManager manager;
    Customer c1;
    Customer c2;
    Cargo cargo1;
    Cargo cargo2;
    BigDecimal big;
    Duration duration;
    @BeforeEach
    void setUp() {
        manager = new StorageManager();
        c1 = new CustomerImpl("davis", BigDecimal.valueOf(999333), Duration.ofDays(90));
        c2 = new CustomerImpl("anna", BigDecimal.valueOf(9193233), Duration.ofDays(45));
        manager.customerManager.add(c1);
        manager.customerManager.add(c2);
        big = BigDecimal.valueOf(2000);
        duration = Duration.ofDays(30);
    }

    @Test
    void isFull() {
        boolean check = true;
        for(int i = 0; i<manager.storage.maxValue+1;i++){
            cargo1 = new CargoImpl(c1,big,Duration.ofDays(10),null);
            if(!manager.storage.add(cargo1)) check = false;
        }
        assertFalse(check);
    }

    @Test
    void changeSize() {
        manager.storage.changeSize(3);
        boolean check = true;
        for(int i = 0; i<4;i++){
            cargo1 = new CargoImpl(c1,big,Duration.ofDays(i+10),null);
            if(!manager.storage.add(cargo1)) check = false;
        }
        assertFalse(check);
    }

}