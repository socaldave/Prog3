package storageContract.cargo;

import com.sun.xml.internal.bind.v2.TODO;
import storageContract.administration.Customer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class CargoImpl implements Cargo {
    private Customer owner;

    private Collection<Hazard> hazards;
    private Date storageDate;

    private Date lastInspection;
    private BigDecimal value;
    private Duration storeDuration;
    private int storeNumber;

    public CargoImpl(Customer owner, BigDecimal value, Duration duration, Collection<Hazard> hazards){
        this.owner = owner;
        this.value = value;
        this.hazards = hazards;
        this.storageDate = new Date();
        this.lastInspection = new Date();
        this.storeDuration = duration;
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
        return this.storageDate;
    }
    @Override
    public void setLastInspectionDate(Date date) {
        this.lastInspection = date;
    }
    @Override
    public Date getStorageDate() {
        return this.storageDate;
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
        return this.storeNumber;
    }

    //TODO ask if all hazard should be outputed or only the ones present in the storage + do i need to output type and storage number
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
        else harzards += (Hazard.toxic.toString()+" "+containsNot);
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
}

