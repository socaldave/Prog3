package network.tcp;

import storageContract.administration.StorageManager;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpClient {
    static final String MESSAGE_CLOSE = "Close Programm";
    static final int PORT = 7331;
    static final String HOST = "127.0.0.1";
    public StorageManager getData() {
        try {
            Socket s = new Socket(HOST, PORT);
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());
            StorageManager management = (StorageManager) is.readObject();
            return management;
        } catch (Exception e) {
            return null;
        }
    }
    public boolean pushData(StorageManager management) throws Exception {

        Socket s = new Socket(HOST, PORT);
        management.list();
        // Stream object for sending object
        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
        os.writeObject(management);

        s.close();
        return true;
    }
}
