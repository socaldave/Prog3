package storageContract.administration;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storage<E> extends ArrayList<E> {
    public int maxValue;
    private final Lock lock = new ReentrantLock();
    private final Condition full = this.lock.newCondition();
    private final Condition empty = this.lock.newCondition();


    @Override
    public boolean add(E e) {
        if (this.size() < maxValue) {
            return super.add(e);
        } else {
            System.out.println("Lager ist voll.");
        }
        return false;
    }

    public void changeSize(Integer cap) {
        this.maxValue = cap;
    }

    public boolean isFull(){
        if (this.size() == maxValue) return true;
        return false;
    }
}
