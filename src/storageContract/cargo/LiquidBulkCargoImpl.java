package storageContract.cargo;

import storageContract.administration.Customer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class LiquidBulkCargoImpl implements LiquidBulkCargo {

    private Customer owner;
    private Collection<Hazard> hazards;
    private Date lastInspect;
    private BigDecimal value;
    private Duration storeDuration;
    private Date storageDate;
    private int storeNumber;
    private boolean isPressurized;

    public LiquidBulkCargoImpl(Customer owner, BigDecimal value, Duration duration, Collection<Hazard> hazards, boolean isPressurized){

        this.owner = owner;
        this.value = value;
        this.hazards = hazards;
        this.storageDate = new Date();
        this.lastInspect = new Date();
        this.storeDuration = duration;
        this.isPressurized = isPressurized;

    }
    @Override
    public int getStorageNumber() {
        return this.storeNumber;
    }
    @Override
    public boolean isPressurized() {
        return this.isPressurized;
    }

    @Override
    public Customer getOwner() {
        return this.owner;
    }

    @Override
    public BigDecimal getValue() {
        return this.value;
    }

    @Override
    public Duration getDurationOfStorage() {
        return this.storeDuration;
    }

    @Override
    public Collection<Hazard> getHazards() {
        return this.hazards;
    }

    @Override
    public Date getLastInspectionDate() {
        return this.lastInspect;
    }

    @Override
    public int getStorageLocation() {
        return storeNumber;
    }

    @Override
    public void setStorageNumber(int storageNumber) {
        this.storeNumber = storageNumber;
    }
    @Override
    public void setLastInspectionDate(Date date) {
        this.lastInspect = date;
    }
    @Override
    public Date getStorageDate() {
        return this.storageDate;
    }


    @Override
    public String getType() {
        return this.getClass().getInterfaces()[0].getSimpleName();
    }
    @Override
    public String toString(){
        String harzards = "[";
        for(Hazard harzard : this.getHazards()){
            harzards += (harzard.name()+", ");

        } harzards += "]";
        return "# "+this.storeNumber+" | owner: "+this.getOwner().getName()+" | value: "
                +this.getValue()+" | last inspect: "+this.getLastInspectionDate().toString()
                +" | harzards:"+harzards;
    }
    @Override
    public String harzardsToString() {
        String contains = "(i)";
        String containsNot = "(e)";
        String harzards = "# "+this.getStorageNumber()+" "+this.getType()+" | ";
        if(this.checkHarzard(Hazard.explosive)) harzards += (Hazard.explosive.toString()+" "+contains+" | ");
        else harzards += (Hazard.explosive.toString()+" "+containsNot+" | ");
        if(this.checkHarzard(Hazard.flammable)) harzards += (Hazard.flammable.toString()+" "+contains+" | ");
        else harzards += (Hazard.flammable.toString()+" "+containsNot+" | ");
        if(this.checkHarzard(Hazard.radioactive)) harzards += (Hazard.radioactive.toString()+" "+contains+" | ");
        else harzards += (Hazard.radioactive.toString()+" "+containsNot+" | ");
        if(this.checkHarzard(Hazard.toxic)) harzards += (Hazard.toxic.toString()+" "+contains+" | ");
        else harzards += (Hazard.toxic.toString()+" "+containsNot+" | ");
        return harzards;
    }
    @Override
    public boolean checkHarzard(Hazard searchHazards){
        Collection<Hazard> colHaz = this.getHazards();
        for(Hazard h : colHaz){
            if(h.equals(searchHazards)) return true;
        }
        return false;
    }

}
