package saveLoad;

import storageContract.administration.StorageManager;

import java.io.FileInputStream;
import java.io.OutputStream;

public interface SaveLoadInterface {
    boolean loadDB(FileInputStream oi, StorageManager management);
    boolean saveDB(OutputStream os, StorageManager management);
    boolean load(FileInputStream oi, StorageManager management, Integer position);
    boolean save(OutputStream os, StorageManager management, Integer position);
}
