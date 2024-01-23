package com.example.laberinto.Observers;

import java.util.HashSet;
import java.util.Set;

public class ObserbableControllers implements Observable{
    Set<Observer> observerSet = new HashSet<>();
    @Override
    public void addObserver(Observer o) {
        observerSet.add(o);
    }
    @Override
    public void deleteObserver(Observer o) {
        observerSet.remove(o);
    }
    @Override
    public void notifyObserver() {
        for (Observer observer:observerSet) {
            observer.update();
        }
    }

    @Override
    public void notifyObserverRecorrido(int x, int y, int os) {
        for (Observer observer:observerSet) {
            observer.updateColors(x,y,os);
        }
    }




}
