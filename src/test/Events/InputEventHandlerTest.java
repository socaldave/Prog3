package test.Events;

import events.handlers.InputEventHandler;
import events.listeners.modes.*;
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

class InputEventHandlerTest {
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
        InputEvent inputEvent = mock(InputEvent.class);
        when(inputEvent.getCurrentCommand()).thenReturn("david");
        when(inputEvent.getLastCommand()).thenReturn(":c");
        InputEventHandler inputEventHandler = new InputEventHandler();
        AddModeListener addModeListener = mock(AddModeListener.class);
        inputEventHandler.add(addModeListener);
        try {
            inputEventHandler.handle(inputEvent);
            InOrder inOrder = inOrder(addModeListener);
            inOrder.verify(addModeListener, times(1)).onInputEvent(inputEvent);
        } catch (Exception e) {

        }
    }

    @Test
    void DeleteEventChainTest() {
        InputEvent inputEvent = mock(InputEvent.class);
        when(inputEvent.getCurrentCommand()).thenReturn("david");
        when(inputEvent.getLastCommand()).thenReturn(":d");
        InputEventHandler inputEventHandler = new InputEventHandler();
        DeleteListener addModeListener = mock(DeleteListener.class);
        inputEventHandler.add(addModeListener);
        try {
            inputEventHandler.handle(inputEvent);
            InOrder inOrder = inOrder(addModeListener);
            inOrder.verify(addModeListener, times(1)).onInputEvent(inputEvent);
        } catch (Exception e) {

        }
    }

    @Test
    void ListEventChainTest() {
        InputEvent inputEvent = mock(InputEvent.class);
        when(inputEvent.getCurrentCommand()).thenReturn("cargo");
        when(inputEvent.getLastCommand()).thenReturn(":r");
        InputEventHandler inputEventHandler = new InputEventHandler();
        ListListener addModeListener = mock(ListListener.class);
        inputEventHandler.add(addModeListener);
        try {
            inputEventHandler.handle(inputEvent);
            InOrder inOrder = inOrder(addModeListener);
            inOrder.verify(addModeListener, times(1)).onInputEvent(inputEvent);
        } catch (Exception e) {

        }
    }

    @Test
    void InspectioonEventChainTest() {
        InputEvent inputEvent = mock(InputEvent.class);
        when(inputEvent.getCurrentCommand()).thenReturn("8");
        when(inputEvent.getLastCommand()).thenReturn(":u");
        InputEventHandler inputEventHandler = new InputEventHandler();
        EditListener addModeListener = mock(EditListener.class);
        inputEventHandler.add(addModeListener);
        try {
            inputEventHandler.handle(inputEvent);
            InOrder inOrder = inOrder(addModeListener);
            inOrder.verify(addModeListener, times(1)).onInputEvent(inputEvent);
        } catch (Exception e) {

        }
    }

    @Test
    void PersistanceEventChainTest() {
        InputEvent inputEvent = mock(InputEvent.class);
        when(inputEvent.getCurrentCommand()).thenReturn("8");
        when(inputEvent.getLastCommand()).thenReturn(":u");
        InputEventHandler inputEventHandler = new InputEventHandler();
        PersistanceListener addModeListener = mock(PersistanceListener.class);
        inputEventHandler.add(addModeListener);
        try {
            inputEventHandler.handle(inputEvent);
            InOrder inOrder = inOrder(addModeListener);
            inOrder.verify(addModeListener, times(1)).onInputEvent(inputEvent);
        } catch (Exception e) {

        }
    }
}