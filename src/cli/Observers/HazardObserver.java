package cli.Observers;


import ObservablePattern.Observer;
import cli.view.View;
import storageContract.administration.StorageManager;

public class HazardObserver implements Observer {

    StorageManager manager;
    View view;


    public HazardObserver(StorageManager manager, View view)  {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void update() {
        view.printNewHazardAdded();
    }
}
