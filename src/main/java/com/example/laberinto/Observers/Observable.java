package com.example.laberinto.Observers;

public interface Observable {
    void addObserver (Observer o);
    void deleteObserver (Observer o);
    void notifyObserver ();
    void notifyObserverRecorrido (int x,int y, int os);
}
