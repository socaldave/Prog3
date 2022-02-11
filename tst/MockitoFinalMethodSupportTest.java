import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
    kann gelöscht werden wenn der Test funktioniert
 */
public class MockitoFinalMethodSupportTest {
    StorageManager management;
    Customer customer;
    Cargo cargo;

    @BeforeEach
    void setUp() {
        management = new StorageManager();
        customer = new CustomerImpl("davis", BigDecimal.valueOf(1000), Duration.ofDays(30));
        cargo = new CargoImpl(customer, BigDecimal.valueOf(10), Duration.ofDays(3), null);
    }
    @Test
    void addCustomerEventChainTest() {
        InputEvent inputEvent = mock(InputEvent.class);
        when(inputEvent.getCurrentCommand()).thenReturn("davis");
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
}
