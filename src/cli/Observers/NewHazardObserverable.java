package cli.Observers;

import ObservablePattern.ObservableInterface;
import ObservablePattern.Observer;

import java.util.LinkedList;

public class NewHazardObserverable implements ObservableInterface {
    private LinkedList<Observer> observers=new LinkedList();

    public NewHazardObserverable(LinkedList<Observer> observers){
        this.observers = new LinkedList<Observer>();
    }

    @Override
    public boolean addObserver(Observer observer){
        return this.observers.add(observer);
    }
    @Override
    public boolean removeObserver(Observer observer){
        return this.observers.remove(observer);
    }
    @Override
    public void notifyObservers(){
        for (Observer o:this.observers)  o.update();
    }
}
