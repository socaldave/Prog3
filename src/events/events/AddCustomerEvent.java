package events.events;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.EventObject;

public class AddCustomerEvent extends EventObject {

    private String customerName;
    private BigDecimal value;
    private Duration duration;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public AddCustomerEvent(Object source, String name, Duration duration, BigDecimal value) {
        super(source);
        this.customerName = name;
        this.duration = duration;
        this.value = value;
    }

    public String getCustomerName(){
        return customerName;
    }

    public BigDecimal getValue(){
        return value;
    }

    public Duration getDuration(){
        return duration;
    }
}
