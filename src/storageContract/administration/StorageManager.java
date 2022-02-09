package storageContract.administration;

import storageContract.cargo.Cargo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

public class StorageManager extends Observable implements Serializable {
    public CustomerManager customerManager;
    public Storage<Cargo> storage;
    public int capNotification = 1;

    public StorageManager() {
        this.storage = new Storage<>();
        this.customerManager= new CustomerManager(new ArrayList<>());
    }

    public StorageManager(Storage<Cargo> store, CustomerManager customerStore) {
        this.storage =store;
        this.customerManager = customerStore;
    }

    public StorageManager(CustomerManager customerManager){
        this.storage = new Storage<>();
        this.customerManager = customerManager;
    }

    public synchronized boolean  addCargo(Cargo cargo) {
        try {
            if (storage.add(cargo) != false) {
                //cargo.setStorageNumber(store.size() - 1);
                //cargo.setStorageNumber(getStorageNumber(cargo));
                if (storage.size() == (storage.maxValue - this.capNotification)) {
                    notifyObservers();
                }
                updateIndex();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public  boolean removeCargo(int index) {
        try {
            storage.remove(index);
            updateIndex();
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateIndex() {
        for (int i = 0; i < this.storage.size(); i++) {
            Cargo cargo = this.storage.get(i);
            if (cargo != null) cargo.setStorageNumber(i);
        }
    }

    public int getCargoStorageNumber(Cargo cargo) {
        if (cargo == null) throw new NullPointerException("null pointer");
        return storage.indexOf(cargo);
    }

    public void listStorageCargo() {
        for (int i = 0; i < this.storage.size(); i++) {
            Cargo c = this.storage.get(i);
            System.out.println("store number: " + i + " | owner: " + c.getOwner().getName() + " | store duration (min): " + c.getDurationOfStorage().getSeconds() / 60 + " | last inspecting date: " + c.getLastInspectionDate().toString());
        }
    }

    public int getCurrentStorageCapacity() {
        if (this.storage.size() == this.storage.maxValue) {
            return -1; // return -1 if automat is full.
        } else {
            return this.storage.size();
        }
    }

    public Cargo getOldestCargo() {
        Cargo cargo = null;
        int i = 0;
        for (Cargo _cargo : this.storage) {
            if (cargo == null) {
                cargo = _cargo;
            } else {
                if (cargo.getDurationOfStorage().compareTo(_cargo.getDurationOfStorage()) > 0) {
                    cargo = _cargo;
                    i = this.storage.indexOf(_cargo);
                }
            }
        }
        return cargo;
    }

    public int getIndexOfOldestContent() {
        Cargo cargo = null;
        int i = -1;
        for (Cargo _cargo : this.storage) {
            if (cargo == null) {
                cargo = _cargo;
                i = this.storage.indexOf(_cargo);
            } else {
                if (cargo.getDurationOfStorage().compareTo(_cargo.getDurationOfStorage()) > 0) {
                    cargo = _cargo;
                    i = this.storage.indexOf(_cargo);
                }
            }
        }
        return i;
    }

    public Storage<Cargo> getStorage() {
        return storage;
    }

    public int getNumberOfCargoStored(String customerName) {
        int i = 0;
        for (Cargo _cargo : this.storage) {
            if (_cargo.getOwner().getName().equals(customerName)) i++;
        }
        return i;
    }

    public boolean setListContent(Storage<Cargo> _storage) {
        for (Cargo c : _storage) {
            if(!checkExists(c)){
                this.customerManager.add(c.getOwner());
                if (!this.addCargo(c)) return false;
            }
        }
        listStorageCargo();
        return true;
    }

    public boolean checkExists(Cargo cargo) {
        for (Cargo c : storage) {
            if (c.equals(cargo)) return true;
        }
        return false;
    }

    public boolean setListContentPosition(Storage<Cargo> readObject, Integer position) {
        try {
            Cargo cargo = storage.get(position);
            this.customerManager.add(cargo.getOwner());
            if (!this.checkExists(cargo)) return storage.add(cargo);
        } catch (Exception e) {
            System.out.println("set position error:" + e.getMessage());
            return false;
        }
        return false;
    }

    public Cargo getOldestContent() {
        Cargo cargo = null;
        int i = 0;
        for (Cargo _cargo : this.storage) {
            if (cargo == null) {
                cargo = _cargo;
            } else {
                if (cargo.getDurationOfStorage().compareTo(_cargo.getDurationOfStorage()) > 0) {
                    cargo = _cargo;
                    i = this.storage.indexOf(_cargo);

                }
            }
        }
        return cargo;
    }

    public boolean Inspection(int storageNumber){
        Cargo inspectedCargo = storage.get(storageNumber);
        if(inspectedCargo == null) return false;
        inspectedCargo.setLastInspectionDate(new Date());
        return true;
    }

    public void list() {
        for (int i = 0; i < this.storage.size(); i++) {
            Cargo c = this.storage.get(i);
            System.out.println("store number: " + i + " | owner: " + c.getOwner().getName() + " | store duration (min): " + c.getDurationOfStorage().getSeconds() / 60 + " | last inspecting date: " + c.getLastInspectionDate().toString());
        }
    }

    public boolean resetContent(StorageManager management) {
        return this.setListContent(management.storage);
    }
}
