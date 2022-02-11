package saveLoad.JDP;

import saveLoad.SaveLoadInterface;
import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;

import java.beans.XMLEncoder;
import java.io.*;

public class JDP_Save_Load extends StorageManager implements SaveLoadInterface {
    private XMLEncoder encoder;
    public JDP_Save_Load() {

    }
    public JDP_Save_Load(CustomerManager customerManagement) {
        super(customerManagement);
    }

    @Override
    public boolean load(FileInputStream oi, StorageManager management, Integer position) {
        // nicht notwendig
        return false;
    }

    @Override
    public boolean save(OutputStream os, StorageManager management, Integer position) {
        // nicht notwendig
        return false;
    }
    @Override
    public boolean loadDB(FileInputStream oi, StorageManager management) {
        try {
            ObjectInputStream ois = new ObjectInputStream(oi);
            StorageManager mana = (StorageManager) ois.readObject();
            management.setListContent(mana.storage);
            ois.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("file not found -> "+ e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e){
            System.out.println("other error -> "+ e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean saveDB(OutputStream os, StorageManager management) {

        try{
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(management);
            oos.close();
            return true;
        } catch(Exception e){
            System.out.println("save error -> "+e.getMessage());
            return false;
        }
    }
}
