package network.udp;

import storageContract.administration.StorageManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
    DatagramSocket socket;
    public int PORT = 7331;
    public InetAddress HOST;
    public int MAX_LENGTH = 60000;

    public UdpClient() throws Exception {
        HOST = InetAddress.getByName("127.0.0.1");
    }

    public StorageManager getData() throws Exception {
        socket = new DatagramSocket();
        String message = "getData";
        byte[] msgBytes = message.getBytes();
        DatagramPacket pack = new DatagramPacket(msgBytes, msgBytes.length, HOST, PORT);
        socket.send(pack);
        byte[] incomingData = new byte[MAX_LENGTH];
        DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
        socket.receive(incomingPacket);
        byte[] data = incomingPacket.getData();
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        try {
            StorageManager management = (StorageManager) is.readObject();
            System.out.println("manager received " + management);
            return management;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean sendData(StorageManager management) throws Exception { //Quelle: http://www.coderpanda.com/java-socket-programming-transferring-java-object-through-socket-using-udp/
        try {
            socket = new DatagramSocket();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(management);
            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, HOST, PORT);
            socket.send(sendPacket);
            System.out.println("Manager sent from udp");
            Thread.sleep(1000);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
