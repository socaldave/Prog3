package storageContract.cargo;

import storageContract.administration.Storable;

public interface UnitisedCargo extends Cargo, Storable {
    boolean isFragile();
}
