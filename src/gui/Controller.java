package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;

import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.StorageManager;
import storageContract.cargo.Cargo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;

public class Controller {

    DB dbModel;


    /*---------------------Messages----------------- */
    public Label statusRemoveContentSuccessful;
    public Label statusRemoveContentFail;
    public Label statusAddContentSuccesful;
    public Label statusAddContentFail;
    public Label statusRemoveCustomerFail;
    public Label statusAddCustomerSuccessful;
    public Label statusRemoveCustomerSuccessful;


    public Label errorDuplicateCustomer;
    /*---------------------Table----------------- */

    public TableView customerTableView;
    public TableColumn customerTableC1View;
    public TableColumn customerTableC2View;

    public TableView contentTableView;

    public TableColumn column1View;
    public TableColumn column2View;
    public TableColumn column3View;
    public TableColumn column4View;
    public TableColumn column5View;
    public TableColumn column6View;
    public TableColumn column7View;
    public TableColumn column8View;
    public TableColumn column9View;
    public TableColumn column10View;
    public TableColumn column11View;
    public TableColumn column12View;
    public TableColumn column13View;
    public TableColumn column14View;
    public TableColumn column15View;

    public void initTableView() {
        this.contentTableView.setOnMouseClicked(new EventHandler<MouseEvent>() { //click
            @Override
            public void handle(MouseEvent event) {

                CargoModel selected = (CargoModel) contentTableView.getSelectionModel().getSelectedItem();

                if (event.getClickCount() == 2) { // double click
                    if (selected != null) {
                        System.out.println("setOnMouseClicked: " + selected.fachnummer.getValue());
                        Cargo c = dbModel.manager.storage.get(selected.fachnummer.getValue());

                        System.out.println("old inspection : " + c.getLastInspectionDate());
                        c.setLastInspectionDate(new Date());
                        System.out.println("new inspecition : " + c.getLastInspectionDate());
                    }
                }


            }
        });

        contentTableView.setOnDragDetected(new EventHandler<MouseEvent>() { //drag
            @Override
            public void handle(MouseEvent event) {
                // drag was detected, start drag-and-drop gesture

                Integer idx;
                idx = contentTableView.getSelectionModel().getFocusedIndex();

                System.out.println("drag detected: " + idx);
                Dragboard db = contentTableView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(idx.toString());
                db.setContent(content);
                event.consume();
            }
        });

        contentTableView.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                // data is dragged over the target
                System.out.println("drag over: " + event.toString());
                Dragboard db = event.getDragboard();
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

                event.consume();
            }
        });

        contentTableView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("setOnDragDropped:" + event.getTarget().toString());
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /*---------------------Inputs----------------- */

    public TextField customerNameInput;
    public TextField customerNameInput2;
    public TextField customerNameInput3;
    public TextField valueInput;
    public TextField kalorienInput;
    public TextField daysInput;
    public CheckBox flammableTypInput;
    public CheckBox radioactiveTypInput;
    public CheckBox toxicTypInput;
    public CheckBox explosiveTypInput;
    public TextField removeInput;
    public ChoiceBox typeInput;
    public CheckBox pressuredInput;
    public CheckBox fragileInput;

    @FXML
    VBox vbMenu; //menu container
    /////////////

    String typeInputString;

    public Controller() {
        this.dbModel = new DB();
    }

    public void initialize() {

        MenuBar mbar = new MenuBar(); //menu bar
        Menu main = new Menu("Main"); //main menu
        Menu miabout = new Menu("About");//about menu
        mbar.getMenus().addAll(main, miabout); //adding menus to the menu bar
        vbMenu = new VBox();
        vbMenu.getChildren().add(0, mbar); //inserts the menu bar to the top
        this.initTableView();
        setOptionalFieldsUnvisible();
        column1View.setCellValueFactory(new PropertyValueFactory<CargoModel, String>("fachnummer"));
        column2View.setCellValueFactory(new PropertyValueFactory<CargoModel, String>("customerName"));
        column3View.setCellValueFactory(new PropertyValueFactory<CargoModel, Integer>("value"));


        //column4View.setCellValueFactory(new PropertyValueFactory<CargoViewModel, Integer>("kalorien"));
        column4View.setCellValueFactory(new PropertyValueFactory<CargoModel, Long>("storeDuration"));
        column5View.setCellValueFactory(new PropertyValueFactory<CargoModel, Boolean>("explosiv"));
        column6View.setCellValueFactory(new PropertyValueFactory<CargoModel, Boolean>("flammable"));
        column7View.setCellValueFactory(new PropertyValueFactory<CargoModel, Boolean>("radioactive"));
        column8View.setCellValueFactory(new PropertyValueFactory<CargoModel, Boolean>("toxic"));
        column9View.setCellValueFactory(new PropertyValueFactory<CargoModel, String>("type"));

        customerTableC1View.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("customerName"));
        customerTableC2View.setCellValueFactory(new PropertyValueFactory<CustomerModel, Integer>("customerId"));

        typeInput.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0)
                this.setAddCargoView();
            if (newValue.intValue() == 1)
                this.setAddLiquidBulkCargoView();
            if (newValue.intValue() == 2)
                this.setAddMixedCargoLiquidBulkAndUnitisedView();
            if (newValue.intValue() == 3)
                this.setAddUnitisedCargoView();
        });
        this.clearAllMessages();
        contentTableView.setItems(dbModel.cargoObserver);
        customerTableView.setItems(dbModel.customerObserver);
    }

    public void onAddCustomerClick(ActionEvent actionEvent) {
        this.clearAllMessages();
        String customerName = customerNameInput.getText();
        System.out.println(customerName);

        Customer customer = new CustomerImpl(customerName, BigDecimal.valueOf(100000), Duration.ofDays(30));

        boolean success = false;
        try {
            success = dbModel.addCustomer(customer);
            if (success) statusAddCustomerSuccessful.setVisible(true);
            else errorDuplicateCustomer.setVisible(true);
        } catch (Exception ex) {
            errorDuplicateCustomer.setVisible(true);
        }
    }

    public void onRemoveCustomerClick(ActionEvent actionEvent) {
        this.clearAllMessages();
        String customerName = customerNameInput.getText();

        boolean success = false;

        try {
            success = dbModel.removeCustomer(customerName);
            if (success) statusRemoveCustomerSuccessful.setVisible(true);
            else statusRemoveCustomerFail.setVisible(true);
        } catch (Exception ex) {
            statusRemoveCustomerFail.setVisible(true);
        }
    }

    public void onAddContentClick(ActionEvent actionEvent) {
        this.clearAllMessages();
        boolean addSuccessful = false;
        try {
            this.toConsole();
            switch (typeInput.getValue().toString()) {
                case "Cargo": {
                    setAddCargoView();
                    addSuccessful = dbModel.addNewCargo(customerNameInput.getText(), Double.parseDouble(valueInput.getText()), Integer.parseInt(daysInput.getText()), flammableTypInput.isSelected(),
                            radioactiveTypInput.isSelected(), explosiveTypInput.isSelected(),
                            toxicTypInput.isSelected(), typeInput.getValue().toString(), pressuredInput.isSelected(), fragileInput.isSelected());
                    if (addSuccessful) statusAddContentSuccesful.setVisible(true);
                    dbModel.manager.list();
                }
                break;
                case "LiquidBulkCargo": {
                    setAddLiquidBulkCargoView();
                    addSuccessful = dbModel.addNewCargo(customerNameInput2.getText(), Double.parseDouble(valueInput.getText()),
                            Integer.parseInt(daysInput.getText()), flammableTypInput.isSelected(),
                            radioactiveTypInput.isSelected(), explosiveTypInput.isSelected(),
                            toxicTypInput.isSelected(), typeInput.getValue().toString(), pressuredInput.isSelected(), fragileInput.isSelected());
                    if (addSuccessful) statusAddContentSuccesful.setVisible(true);
                }
                break;
                case "MixedCargoLiquidBulkAndUnitised": {
                    setAddMixedCargoLiquidBulkAndUnitisedView();
                    addSuccessful = dbModel.addNewCargo(customerNameInput2.getText(), Double.parseDouble(valueInput.getText()),
                            Integer.parseInt(daysInput.getText()), flammableTypInput.isSelected(),
                            radioactiveTypInput.isSelected(), explosiveTypInput.isSelected(),
                            toxicTypInput.isSelected(), typeInput.getValue().toString(), pressuredInput.isSelected(), fragileInput.isSelected());
                    if (addSuccessful) statusAddContentSuccesful.setVisible(true);
                }
                break;
                case "UnitisedCargo": {
                    setAddUnitisedCargoView();
                    addSuccessful = dbModel.addNewCargo(customerNameInput2.getText(), Double.parseDouble(valueInput.getText()), Integer.parseInt(daysInput.getText()), flammableTypInput.isSelected(),
                            radioactiveTypInput.isSelected(), explosiveTypInput.isSelected(),
                            toxicTypInput.isSelected(), typeInput.getValue().toString(), pressuredInput.isSelected(), fragileInput.isSelected());
                    if (addSuccessful) statusAddContentSuccesful.setVisible(true);
                }
                break;
            }
        } catch (Exception ex) {
            statusAddContentFail.setVisible(true);
        }
        if (!addSuccessful) statusAddContentFail.setVisible(true);
    }

    public void toConsole() {
        System.out.println(customerNameInput.getText() + " | " + Double.parseDouble(valueInput.getText()) + " | " + Integer.parseInt(daysInput.getText()) + " | " + flammableTypInput.isSelected() + " | " +
                radioactiveTypInput.isSelected() + " | " + explosiveTypInput.isSelected() + " | " + pressuredInput.isSelected() + " | " + fragileInput.isSelected());
    }

    public void onRemoveContentClick(ActionEvent actionEvent) {
        this.clearAllMessages();

        String fachnummer = removeInput.getText();

        boolean removeSuccessful = false;
        try {
            removeSuccessful = dbModel.removeCargo(Integer.parseInt(fachnummer));
            if (removeSuccessful)
                statusRemoveContentSuccessful.setVisible(true);
        } catch (Exception ex) {
            if (!removeSuccessful)
                statusRemoveContentFail.setVisible(true);
        }
    }

    private void setAddCargoView() {
        setOptionalFieldsUnvisible();
        this.typeInputString = "Cargo";
    }

    private void setAddLiquidBulkCargoView() {
        setOptionalFieldsUnvisible();


        pressuredInput.setVisible(true);
        this.typeInputString = "LiquidBulkCargo";
    }

    private void setAddMixedCargoLiquidBulkAndUnitisedView() {
        setOptionalFieldsUnvisible();
        fragileInput.setVisible(true);
        pressuredInput.setVisible(true);


        this.typeInputString = "MixedCargoLiquidBulkAndUnitised";
    }

    private void setAddUnitisedCargoView() {
        setOptionalFieldsUnvisible();


        fragileInput.setVisible(true);
        pressuredInput.setVisible(true);
        this.typeInputString = "UnitisedCargo";
    }

    private void clearAllMessages() {
        errorDuplicateCustomer.setVisible(false);
        statusAddContentSuccesful.setVisible(false);
        statusAddContentFail.setVisible(false);
        statusRemoveContentSuccessful.setVisible(false);
        statusRemoveContentFail.setVisible(false);
        statusAddCustomerSuccessful.setVisible(false);
        statusRemoveCustomerSuccessful.setVisible(false);
        statusRemoveCustomerFail.setVisible(false);
        errorDuplicateCustomer.setVisible(false);
    }

    private void setOptionalFieldsUnvisible() {
        fragileInput.setVisible(false);
        pressuredInput.setVisible(false);


    }

    public void onSaveClick(ActionEvent actionEvent) {
        File file = new File("db/store.tmp");
        try (FileOutputStream fos = new FileOutputStream(file);) {
            this.dbModel.jdb.saveDB(fos,this.dbModel.manager);

        } catch(Exception e){
            System.out.println("saveJDB error -> " + e.getMessage());
        }
    }

    public void onLoadClick(ActionEvent actionEvent) {
        try (FileInputStream fos = new FileInputStream(new File("db/store.tmp"));) {
            this.dbModel.jdb.loadDB(fos, this.dbModel.manager);
            this.dbModel.updateCargoTableView();
            this.dbModel.updateCustomerTableView();
        } catch (Exception e) {
            System.out.println("loadJDB error -> " + e.getMessage());
        }
        System.out.println("load end, store size: " + this.dbModel.manager.storage.size());
    }
    public void onSaveJosClick(ActionEvent actionEvent) {
        File file = new File("db/store.txt");
        try (FileOutputStream fos = new FileOutputStream(file);) {
            this.dbModel.jos.saveDB(fos, this.dbModel.manager);
            this.dbModel.updateCargoTableView();
            this.dbModel.updateCustomerTableView();
        } catch (Exception e) {
            System.out.println("saveJOS error -> " + e.getMessage());
        }
    }

    public void onLoadJosClick(ActionEvent actionEvent) {
        try (FileInputStream fos = new FileInputStream(new File("db/store.txt"));) {
            this.dbModel.jos.loadDB(fos, this.dbModel.manager);
            this.dbModel.updateCargoTableView();
            this.dbModel.updateCustomerTableView();
        } catch (Exception e) {
            System.out.println("loadJOS error -> " + e.getMessage());
        }
    }

    public void onSaveUdpClick(ActionEvent actionEvent) {
        try{
            this.dbModel.udp.sendData(this.dbModel.manager);
        } catch (Exception e){
            System.out.println("client: saving from udp failed"+ e.getMessage());
        }
    }

    public void onLoadUdpClick(ActionEvent actionEvent) {
        try {
            StorageManager m = this.dbModel.udp.getData();
            this.dbModel.manager.resetContent(m);
            this.dbModel.updateCargoTableView();
            this.dbModel.updateCustomerTableView();
        } catch (Exception e){
            System.out.println("client: loading from udp failed"+ e.getMessage());
        }
    }

    public void onSaveTcpClick(ActionEvent actionEvent) {
        try{
            this.dbModel.tcp.pushData(this.dbModel.manager);
        } catch (Exception e){
            System.out.println("client: saving from udp failed"+ e.getMessage());
        }
    }

    public void onLoadTcpClick(ActionEvent actionEvent) {
        try {
            StorageManager m = this.dbModel.tcp.getData();
            this.dbModel.manager.resetContent(m);
            this.dbModel.updateCargoTableView();
            this.dbModel.updateCustomerTableView();
        } catch (Exception e){
            System.out.println("client: loading from tcp failed"+ e.getMessage());
        }
    }
}

