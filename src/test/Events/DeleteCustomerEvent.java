package test.Events;

import events.events.DeleteCustomerEvent;
import events.handlers.DeleteCustomerEventHandler;
import events.listeners.modes.DeleteCustomerListener;
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

 class DeleteCustomerEventTest {

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
    void addCustomerEventChainTest() {
        DeleteCustomerEvent inputEvent = mock(events.events.DeleteCustomerEvent.class);
        when(inputEvent.getCustomerName()).thenReturn("David");

        DeleteCustomerEventHandler inputEventHandler = new DeleteCustomerEventHandler();
        DeleteCustomerListener addModeListener = mock(DeleteCustomerListener.class);
        inputEventHandler.add(addModeListener);
        try {
            inputEventHandler.handle(inputEvent);
            InOrder inOrder = inOrder(addModeListener);
            inOrder.verify(addModeListener, times(1)).onDeleteCustomer(inputEvent);
        } catch (Exception e) {

        }
    }
}
