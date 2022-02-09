package gui;

import javafx.beans.property.*;
import storageContract.cargo.Cargo;
import storageContract.cargo.Hazard;
import storageContract.cargo.LiquidBulkCargo;
import storageContract.cargo.UnitisedCargo;

import java.util.Collection;

public class CargoModel {
    SimpleStringProperty customerName;
    SimpleIntegerProperty customerId;
    SimpleLongProperty storeDuration;
    SimpleDoubleProperty value;

    SimpleStringProperty date;
    SimpleStringProperty type;
    //SimpleIntegerProperty kalorien;
    SimpleIntegerProperty fachnummer;

    SimpleBooleanProperty explosiv;
    SimpleBooleanProperty flammable;
    SimpleBooleanProperty radioactive;
    SimpleBooleanProperty toxic;


    SimpleBooleanProperty isPressured;
    SimpleBooleanProperty isFragile;

    public CargoModel(Cargo cargo) {
        super();
        customerName = new SimpleStringProperty(cargo.getOwner().getName());
        //herstellerId = new SimpleIntegerProperty(cargo.getOwner().getId());
        //haltbarkeit = new SimpleStringProperty(""+cargo.getHaltbarkeit().toDays());
        //price = new SimpleDoubleProperty(cargo.getPreis().doubleValue());
        type = new SimpleStringProperty(cargo.getClass().getInterfaces()[0].getSimpleName());
        //date = new SimpleStringProperty(cargo.getLastInspectionDate());
        //kalorien = new SimpleIntegerProperty(cargo.getNaehrwert());
        //type = new SimpleStringProperty(cargo.getClass().getInterfaces()[0].getSimpleName());
        fachnummer = new SimpleIntegerProperty(cargo.getStorageNumber());
        storeDuration = new SimpleLongProperty((long)cargo.getDurationOfStorage().toDays());

        System.out.println("pass dur");

        switch (type.getValue()) {
            case "Cargo":
                this.setCargoProperty(cargo); break;
            case "UnitisedCargo":
                this.setUnitisedCargoProperties(cargo); break;
            case "MixedCargoLiquidBulkAndUnitised":
                this.setMixedCargoLiquidBulkAndUnitisedProperties(cargo); break;
            case "LiquidBulkCargo":
                this.setLiquidBulkCargoProperties(cargo);System.out.println("liquid choosed"); break;
            default:
                this.setNull(); break;
        }

        Collection<Hazard> hazards = cargo.getHazards();

        this.setAllHazardsFalse();
        if (hazards.contains(Hazard.explosive)) explosiv = new SimpleBooleanProperty(true);
        if (hazards.contains(Hazard.radioactive)) radioactive = new SimpleBooleanProperty(true);
        if (hazards.contains(Hazard.toxic)) toxic = new SimpleBooleanProperty(true);
        if (hazards.contains(Hazard.flammable)) flammable = new SimpleBooleanProperty(true);

    }
    private void setAllHazardsFalse() {
        explosiv = new SimpleBooleanProperty(false);
        flammable = new SimpleBooleanProperty(false);
        radioactive = new SimpleBooleanProperty(false);
        toxic = new SimpleBooleanProperty(false);
    }
    private void setHazards(Cargo cargo){
        for(Hazard harzard : cargo.getHazards()){
            if(harzard.toString().equals("explosive")){
                explosiv = new SimpleBooleanProperty(true);
            } else if(harzard.toString().equals("flammable")){
                flammable = new SimpleBooleanProperty(true);
            } else if(harzard.toString().equals("radioactive")){
                radioactive = new SimpleBooleanProperty(true);
            } else if(harzard.toString().equals("toxic")){
                toxic = new SimpleBooleanProperty(true);
            }
        }
    }
    private void setCargoProperty(Cargo cargo) {


        //kalorien = new SimpleIntegerProperty(((Cargo) cargo).getNaehrwert());
        value = new SimpleDoubleProperty(((Cargo)cargo).getValue().doubleValue());
        customerName = new SimpleStringProperty(((Cargo) cargo).getOwner().getName());
        storeDuration = new SimpleLongProperty(((Cargo)cargo).getDurationOfStorage().toDays());
        //date = new SimpleStringProperty(((Cargo)cargo).getLastInspectionDate().toString());

    }

    private void setLiquidBulkCargoProperties(Cargo cargo) {
        //kalorien = new SimpleIntegerProperty(((LiquidBulkCargo) kuchen).getNaehrwert());

        value = new SimpleDoubleProperty(((LiquidBulkCargo) cargo).getValue().doubleValue());
        customerName = new SimpleStringProperty(((LiquidBulkCargo) cargo).getOwner().getName());
        storeDuration = new SimpleLongProperty(((LiquidBulkCargo) cargo).getDurationOfStorage().toDays());
        //date = new SimpleStringProperty(((LiquidBulkCargo) cargo).getLastInspectionDate().toString());

    }

    private void setUnitisedCargoProperties(Cargo cargo) {

        value = new SimpleDoubleProperty(((UnitisedCargo) cargo).getValue().doubleValue());
        customerName = new SimpleStringProperty(((UnitisedCargo) cargo).getOwner().getName());
        storeDuration = new SimpleLongProperty(((UnitisedCargo) cargo).getDurationOfStorage().toDays());
        //date = new SimpleStringProperty(((UnitisedCargo) cargo).getLastInspectionDate().toString());
    }

    private void setMixedCargoLiquidBulkAndUnitisedProperties(Cargo cargo) {

       // value = new SimpleDoubleProperty(((MixedCargoLiquidBulkAndUnitised) cargo).getValue().doubleValue());
       // customerName = new SimpleStringProperty(((MixedCargoLiquidBulkAndUnitised) cargo).getOwner().getName());
       // storeDuration = new SimpleLongProperty(((MixedCargoLiquidBulkAndUnitised) cargo).getDurationOfStorage().toDays());
        //date = new SimpleStringProperty(((MixedCargoLiquidBulkAndUnitised) cargo).getLastInspectionDate().toString());

    }

    private void setNull() {
        customerName = new SimpleStringProperty(null);
        value = new SimpleDoubleProperty(0);
        storeDuration = new SimpleLongProperty(0);
        //date = new SimpleStringProperty(null);

    }

    public String getCustomerName() {
        return customerName.get();
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public int getFachnummer() {
        return fachnummer.get();
    }

    /*
       public int getKalorien() {
        return kalorien.get();
    }
     */

    public Long getStoreDuration() {
        return storeDuration.get();
    }

    public String getType(){ return type.get(); }

    public String getDate(){ return date.get(); }



    public double getValue() {
        return value.get();
    }

    public boolean getExplosiv() {
        return explosiv.get();
    }

    public boolean getFlammable() {
        return flammable.get();
    }

    public boolean getRadioactive() {
        return radioactive.get();
    }

    public boolean getToxic() {
        return toxic.get();
    }


}
