package storageContract.administration;

import java.util.ArrayList;

public class Storage<E> extends ArrayList<E> {
    public int maxValue;
    public Storage(){

    }

    public Storage(int maxValue){
        this.maxValue = maxValue;
    }

    @Override
    public boolean add(E e){
        if (this.size() < maxValue) {
            return super.add(e);
        } else {
            //TODO View should print msg
            System.out.println("Lager ist voll.");
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    public void changeSize(Integer cap) {
        this.maxValue = cap;
    }
}
