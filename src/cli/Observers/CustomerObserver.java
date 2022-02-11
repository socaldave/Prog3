package cli.Observers;

import ObservablePattern.Observer;
import cli.view.View;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;

public class CustomerObserver implements Observer {

    StorageManager manager;
    View view;
    Cargo lastContent;
    String activity;

    public CustomerObserver(StorageManager manager, View view)  {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void update() {
        view.printAddedCustomerObserver();
    }
}
