package ObservablePattern;

public interface ObservableInterface {
    public boolean addObserver(Observer observer);
    public boolean removeObserver(Observer observer);
    public void notifyObservers();
}
