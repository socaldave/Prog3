package storageContract.cargo;

import storageContract.administration.Storable;

public interface DryBulkCargo extends Cargo, Storable {
    boolean isSolid();
}
