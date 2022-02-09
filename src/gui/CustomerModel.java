package gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import storageContract.administration.Customer;

public class CustomerModel {
    SimpleStringProperty customerName;

    SimpleIntegerProperty customerId;

    public CustomerModel(Customer customer) {

        customerName = new SimpleStringProperty(customer.getName());
        customerId = new SimpleIntegerProperty(customer.getId());
    }
    public String getCustomerName() {
        return customerName.get();
    }

    public Integer getCustomerId() {
        return customerId.get();
    }

}
