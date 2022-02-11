package test.administration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerManager;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerManagerTest {

    CustomerManager cManagement;
    Customer c1;
    Customer c2;
    Customer c3;
    @BeforeEach
    void setUp() {
        cManagement = new CustomerManager(new ArrayList<>());
        c1 = new CustomerImpl("david", BigDecimal.valueOf(999333), Duration.ofDays(90));
        c2 = new CustomerImpl("Paul", BigDecimal.valueOf(9193233), Duration.ofDays(45));
        c3 = new CustomerImpl("Jasmin", BigDecimal.valueOf(339333), Duration.ofDays(12));
        cManagement.add(c1);

    }
    @Test
    void getCustomerWithName() {
        Assertions.assertEquals(c1, cManagement.getCustomerWithName("david"));
    }

    @Test
    void getCustomerWithNamenon() {
        Assertions.assertEquals(null, cManagement.getCustomerWithName("rayj"));
    }

    @Test
    void checkIfCustomerExists() {
        assertEquals(true,cManagement.checkIfCustomerExists(c1));
    }

    @Test
    void checkIfCustomerNonExists() {
        assertEquals(false,cManagement.checkIfCustomerExists(c2));
    }

    @Test
    void add() {
        assertEquals(true, cManagement.add(c2));
    }

    @Test
    void remove() {
        assertEquals(true, cManagement.remove(0));
    }

    @Test
    void removeInvalid() {
        assertEquals(false, cManagement.remove(4));
    }

    @Test
    void removeCustomerByName() {
        assertEquals(true, cManagement.removeCustomerByName("david"));
    }

    @Test
    void removeCustomerByNameInvalid() {
        assertEquals(false, cManagement.removeCustomerByName("RayJ"));
    }

    @Test
    void getList() {
        ArrayList<Customer> list = new ArrayList<>();
        list.add(c1);
        assertEquals(list, cManagement.getList() );
    }
}