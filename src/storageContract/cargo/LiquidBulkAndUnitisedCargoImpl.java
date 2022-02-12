package storageContract.cargo;

import storageContract.administration.Customer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class LiquidBulkAndUnitisedCargoImpl implements LiquidBulkAndUnitisedCargo{
    private Customer owner;
    private Collection<Hazard> hazards;
    private Date lastInspect;
    private BigDecimal value;
    private Duration storeDuration;
    private Date storageDate;
    private int storeNumber;
    private boolean isPressurized;
    private boolean isFragile;

    public LiquidBulkAndUnitisedCargoImpl(Customer owner, BigDecimal value, Duration duration, Collection<Hazard> hazards, boolean isPressurized, boolean isFragile){

        this.owner = owner;
        this.value = value;
        this.hazards = hazards;
        this.storageDate = new Date();
        this.lastInspect = new Date();
        this.storeDuration = duration;
        this.isPressurized = isPressurized;

    }

    @Override
    public int getStorageLocation() {
        return storeNumber;
    }

    @Override
    public Customer getOwner() {
        return owner;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public Duration getDurationOfStorage() {
        return storeDuration;
    }

    @Override
    public Collection<Hazard> getHazards() {
        return hazards;
    }

    @Override
    public Date getLastInspectionDate() {
        return getLastInspectionDate();
    }

    @Override
    public void setLastInspectionDate(Date date) {
        this.lastInspect = date;
    }

    @Override
    public Date getStorageDate() {
        return storageDate;
    }

    @Override
    public void setStorageNumber(int storageNumber) {
        this.storeNumber = storageNumber;
    }

    @Override
    public String getType() {
        return this.getClass().getInterfaces()[0].getSimpleName();
    }

    @Override
    public int getStorageNumber() {
        return 0;
    }

    @Override
    public String harzardsToString() {
        return null;
    }

    @Override
    public boolean checkHarzard(Hazard searchHazards) {
        return false;
    }

    @Override
    public boolean isPressurized() {
        return false;
    }

    @Override
    public boolean isFragile() {
        return false;
    }
}
