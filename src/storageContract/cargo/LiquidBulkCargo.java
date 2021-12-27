package storageContract.cargo;

import storageContract.administration.Storable;

public interface LiquidBulkCargo extends Cargo, Storable {
    boolean isPressurized();
}
