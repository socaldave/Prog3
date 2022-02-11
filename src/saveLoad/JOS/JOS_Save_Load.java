package saveLoad.JOS;

import saveLoad.SaveLoadInterface;
import storageContract.administration.CustomerManager;
import storageContract.administration.Storage;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;

import java.io.*;

public class JOS_Save_Load extends StorageManager implements SaveLoadInterface {

    ObjectOutputStream oos;

    ObjectInputStream ois;

    public JOS_Save_Load() {
    }

    public JOS_Save_Load(CustomerManager customerManagement) {
        super(customerManagement);
    }

    /*public MediaContentDbJOS(HerstellerVerwaltung herstellerDB, int capacity) {
        super(herstellerDB, capacity);
    }*/

    @Override
    public boolean saveDB(OutputStream os, StorageManager management) {
        if(oos == null){
            try {
                oos =new ObjectOutputStream(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            oos.writeObject(management.storage);
            oos.flush();
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean loadDB(FileInputStream oi, StorageManager management){
        try {
            ois = new ObjectInputStream(oi);
            management.setListContent((Storage<Cargo>) ois.readObject());
            oi.close();
            return true;
        }
        catch (FileNotFoundException e) {System.out.println(e.toString());}
        catch (IOException e) {System.out.println(e.toString());}
        catch (ClassNotFoundException e) {System.out.println(e.toString());}
        return false;
    }


    @Override
    public boolean load(FileInputStream oi, StorageManager management, Integer position){
        try {
            ois = new ObjectInputStream(oi);
            boolean check = management.setListContentPosition((Storage<Cargo>) ois.readObject(),position);
            oi.close();
            return true;
        }
        catch (FileNotFoundException e) {System.out.println(e.toString());}
        catch (IOException e) {System.out.println(e.toString());}
        catch (ClassNotFoundException e) {System.out.println(e.toString());}
        return false;
    }

    @Override
    public boolean save(OutputStream os, StorageManager management, Integer position) {
        if(oos == null){
            try {
                oos =new ObjectOutputStream(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if(position<management.storage.size())
                oos.writeObject(management.storage.get(position));
            else oos.writeObject(management.storage);
            oos.flush();
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
