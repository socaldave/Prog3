package events.events;

import storageContract.cargo.Hazard;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.EventObject;

public class AddCargoEvent extends EventObject {

    private Class type;
    private String customerName;
    private BigDecimal value;
    private Duration duration;
    private Collection<Hazard> hazards;
    private Boolean pressurized;
    private Boolean fragile;
    private Boolean fest;

    public AddCargoEvent(Object source, Class type, String customerName, BigDecimal value, Duration duration, Collection<Hazard> harzards, Boolean pressurized, Boolean fraggile, Boolean fest) {
        super(source);
        this.type = type;
        this.customerName = customerName;
        this.value = value;
        this.duration = duration;
        this.hazards = harzards;
        this.pressurized = pressurized;
        this.fragile = fraggile;
        this.fest = fest;
    }

    public Class getType() {
        return type;
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

    public Collection<Hazard> getHazards(){
        return hazards;
    }

    public Boolean getPressurized (){
        return pressurized;
    }

    public Boolean getFragile(){
        return fragile;
    }

    public Boolean getFest(){
        return fest;
    }
}
