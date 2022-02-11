package test.Events;

import events.events.ListCargoEvent;
import events.handlers.ListCargoEventHandler;
import events.listeners.modes.ListCargoListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;

import java.math.BigDecimal;
import java.time.Duration;

import static org.mockito.Mockito.*;

public class ListCargoEventTest {

    StorageManager management;
    Customer customer;
    Cargo cargo;

    @BeforeEach
    void setUp() {
        management = new StorageManager();
        customer = new CustomerImpl("david", BigDecimal.valueOf(1000), Duration.ofDays(30));
        cargo = new CargoImpl(customer, BigDecimal.valueOf(10), Duration.ofDays(3), null);
    }

    @Test
    void ListCargoEventChainTest() {
        ListCargoEvent inputEvent = mock(ListCargoEvent.class);
        when(inputEvent.getType()).thenReturn("cargo");

        ListCargoEventHandler inputEventHandler = new ListCargoEventHandler();
        ListCargoListener addModeListener = mock(ListCargoListener.class);
        inputEventHandler.add(addModeListener);
        try {
            inputEventHandler.handle(inputEvent);
            InOrder inOrder = inOrder(addModeListener);
            inOrder.verify(addModeListener, times(1)).onListCargo(inputEvent);
        } catch (Exception e) {

        }
    }
}
