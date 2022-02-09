import cli.Controller.Controller;
import cli.Controller.ControllerImpl;
import cli.view.View;
import cli.view.ViewImpl;
import events.handlers.InputEventHandler;
import events.handlers.addEventHandler;
import network.server.Server;
import storageContract.administration.CustomerManager;
import storageContract.administration.StorageManager;

import java.util.ArrayList;
import java.util.Locale;

public class Servers {
    private static Server server;

    public static void main(String[] args) throws Exception {
        Server server = null;

        if(args[0].toLowerCase(Locale.ROOT).equals("tcp")){
             server = new Server("tcp",100000);
             startCli(true,false,10);
        } else if(args[0].toLowerCase(Locale.ROOT).equals("udp")){
             server = new Server("udp",100000);
             startCli(false,true,10);
        }

        server.run();
        server.InitView(100);
    }

    public static void startCli(boolean tcp, boolean udp, Integer cap) {
        InputEventHandler inputEventHandler = new InputEventHandler();
        addEventHandler addEventHandler = new addEventHandler();
        CustomerManager customers = new CustomerManager(new ArrayList<>());
        StorageManager management = new StorageManager(customers);
        View view = new ViewImpl(System.in, System.out);
        view.setInputEventHandler(inputEventHandler);
        view.setAddHandler(addEventHandler);
        Controller controller = new ControllerImpl(management, view);
        view.setNetwork(tcp, udp, management);
        view.initView();
        if (cap != null) management.storage.changeSize(cap);
    }
}
