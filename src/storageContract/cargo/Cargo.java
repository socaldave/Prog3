package storageContract.cargo;

import storageContract.administration.Customer;
import storageContract.administration.Storable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public interface Cargo extends Serializable {
    Customer getOwner();
    BigDecimal getValue();
    Duration getDurationOfStorage();
    Collection<Hazard> getHazards();
    Date getLastInspectionDate();
    void setLastInspectionDate(Date date);
    Date getStorageDate();
    void setStorageNumber(int storageNumber);
    String getType();
    int getStorageNumber();
    String harzardsToString();
    boolean checkHarzard(Hazard searchHazards);



}
