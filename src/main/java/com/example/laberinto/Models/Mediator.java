package com.example.laberinto.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mediator {
    private static Mediator instance = null;
    private List<Component> components;
    private Map<Component, Object> messageRegistry;

    private Mediator() {
        components = new ArrayList<>();
        messageRegistry = new HashMap<>();
    }

    public static Mediator getInstance() {
        if (instance == null) {
            instance = new Mediator();
        }
        return instance;
    }

    public void add(Component component) {
        components.add(component);
        messageRegistry.put(component, null);
    }

    public void notify(Object message, Component sender) {
        for (Component component : components) {
            if (component != sender) {
                component.receive(message);
                messageRegistry.put(component, message);
            }
        }
    }

    public Object getMessage(Component component) {
        return messageRegistry.get(component);
    }
}