package storageContract.administration;

import ObservablePattern.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerManager extends Observable implements Serializable {
    private ArrayList<Customer> customers;
    private Integer id = 0;

    public CustomerManager(ArrayList<Customer> customers){ this.customers = customers;}

    public Customer getCustomerWithName(String name){
        List<Customer> list = customers;
        for(Customer h : list){
            if(h.getName().equals(name)){
                return h;
            }
        }
        return null;
    }

    public boolean checkIfCustomerExists(Customer c){
        List<Customer> list = customers;
        if (c != null){
            for(Customer h : list){
                if(h.getName().equals((c.getName())))
                    return true;
            }
            return false;
        }
        return false;
    }

    public boolean add(Customer c)
    {
        if(c == null) return false;
        if(getCustomerWithName(c.getName())==null) {
            customers.add(c);
            c.setId(this.id);
            this.id++;
            notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(Integer index){

        if (index == null) return false;
        try{
            if(index<customers.size())
                customers.remove(index);
            else return false;
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean removeCustomerByName(String name){
        for(Customer h : this.customers){
            if(h.getName().equals(name)) {
                this.customers.remove(h);
                return true;
            }
        }
        return false;
    }

    public void list(){
        for(int i = 0; i<this.customers.size();i++){
            Customer c = this.customers.get(i);
            System.out.println("customer number: "+i+" | name:"+c.getName());
        }
    }

    public ArrayList<Customer> getList(){
        return this.customers;
    }
}
