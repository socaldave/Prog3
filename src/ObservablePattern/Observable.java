package ObservablePattern;

import java.util.LinkedList;

public abstract class Observable {
    private LinkedList<Observer> observers=new LinkedList();
    public boolean addObserver(Observer observer){
        return this.observers.add(observer);
    }
    public boolean removeObserver(Observer observer){
        return this.observers.remove(observer);
    }
    public void notifyObservers(){
        for (Observer o:this.observers)  o.update();
    }
}
