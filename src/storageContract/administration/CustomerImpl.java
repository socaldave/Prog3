package storageContract.administration;

import java.math.BigDecimal;
import java.time.Duration;

public class CustomerImpl implements Customer{
    private String name;
    BigDecimal maxValue;
    Duration maxStorageTime;
    Integer id;
    private static final long serialVersionUID = 2438199182536764087L;

    public CustomerImpl(String name, BigDecimal value, Duration time){
        this.name = name;
        this.maxValue = value;
        this.maxStorageTime = time;
    }


    @Override
    public String getName() { return this.name; }

    public BigDecimal getMaxValue() {
        return this.maxValue;
    }


    public Duration getMaxDurationOfStorage() {
        return this.maxStorageTime;
    }

    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "customer name: "+this.getName();
    }
}
