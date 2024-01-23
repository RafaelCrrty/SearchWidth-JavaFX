package com.example.laberinto.Models;

abstract class IComponent {

    abstract void notify(Object message);

    abstract void receive(Object message);
}
