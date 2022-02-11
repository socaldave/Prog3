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

    private static final long serialVersionUID  = 4592312602065581690l;


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
        if(cap > 0)
            this.maxValue = cap;
    }

    public boolean isFull(){
        if (this.size() == maxValue) return true;
        return false;
    }
}
