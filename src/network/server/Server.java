package network.server;

import cli.Controller.Controller;
import cli.Controller.ControllerImpl;
import cli.view.View;
import cli.view.ViewImpl;
import events.handlers.InputEventHandler;
import events.handlers.addEventHandler;
import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;


import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    private final String protocol;
    private final Integer cap;

    private InputEventHandler inputEventHandler;
    public View view;
    public StorageManager management;
    private Controller controller;
    private Socket tcpSocket;
    static final int PORT = 7331;
    private DatagramSocket udpSocket;
    private InetAddress address;


    private byte[] buf;

    public Server(String protocoll, Integer cap) throws IOException {
        this.management = InitView(cap);
        load(this.management);
        this.protocol = protocoll;
        this.cap = cap;
    }

    public void run() {
        if (protocol.toLowerCase().equals("tcp")) this.startTCP(cap);
        else if (protocol.toLowerCase().equals("udp")) this.startUDP(cap);
    }

    private void startUDP(Integer cap) { //Inspiriert durch Quelle: http://www.coderpanda.com/java-socket-programming-transferring-java-object-through-socket-using-udp/
        try {
            udpSocket = new DatagramSocket(PORT);
            byte[] incomingData = new byte[600000];

            while (true) {
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                udpSocket.receive(incomingPacket);
                System.out.println("receiving...");
                byte[] data = incomingPacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                try {
                    ObjectInputStream is = new ObjectInputStream(in);
                    System.out.println("object input stream...");
                    StorageManager recManagement = (StorageManager) is.readObject();
                    is.close();
                    this.save(recManagement);
                    System.out.println("save...");
                    recManagement.list();
                } catch (ClassNotFoundException e) {
                    System.out.println("class not found");
                } catch (Exception e) {

                    InetAddress IPAddress = incomingPacket.getAddress();
                    int port = incomingPacket.getPort();

                    String re = new String(incomingPacket.getData(), 0, incomingPacket.getLength());
                    System.out.println("ip: " + IPAddress.toString() + " port: " + port + " data: " + re);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ObjectOutputStream os = new ObjectOutputStream(outputStream);
                    os.writeObject(management);
                    byte[] resData = outputStream.toByteArray();
                    System.out.println("server data length: " + resData.length);
                    DatagramPacket sendPacket = new DatagramPacket(resData, resData.length, IPAddress, port);
                    udpSocket.send(sendPacket);
                    System.out.println("ex:" + e.getMessage());
                }

                Thread.sleep(2000);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
        System.exit(0);
    }

    public void startTCP(Integer cap) {
        management.storage.changeSize(cap);
        try {
            ServerSocket server = new ServerSocket(PORT);

            //Socket socket = server.accept();
            while (true) {
                Socket socket = server.accept();
                // Server listening for connection
                // Stream object for sending object
                try {
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                    os.writeObject(management);
                } catch (Exception e) {
                }
                InputStream is = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                try{
                    StorageManager to = (StorageManager) ois.readObject();
                    to.list();
                    this.save(to);

                } catch (ClassNotFoundException e){
                    System.out.println("class not found:"+e.getMessage());
                }
                //if (to!=null){System.out.println();}

                //ois.close();
                //is.close();

                //os.close();


            }
        } catch (IOException e) {
            System.out.println("tcp server error: " + e.getStackTrace().getClass().getSimpleName());

            //startTCP(cap);
        }

    }

    public void load(StorageManager management) {
        view.loadJOS(management);
    }

    public void save(StorageManager management) {
        view.saveJOS(management);
        //view.loadJOS(management,"serverStorage.txt");
    }

    public StorageManager InitView(Integer cap) {
        InputEventHandler inputEventHandler = new InputEventHandler();
        addEventHandler addEventHandler = new addEventHandler();
        CustomerManager customers = new CustomerManager(new ArrayList<>());
        StorageManager management = new StorageManager(customers);
        if (cap != null) management.storage.changeSize(cap);
        else management.storage.changeSize(1000); // default size 1000

        this.view = new ViewImpl(System.in, System.out);
        view.setInputEventHandler(inputEventHandler);
        view.setAddHandler(addEventHandler);
        //view.initView();
        Controller controller = new ControllerImpl(management, view);
        return management;
    }
    public static void main(String[] args) throws Exception {

        Server server = new Server("tcp",100000);

        //Server server = new Server("udp",100000);
        server.run();
        server.InitView(100);

    }
}
