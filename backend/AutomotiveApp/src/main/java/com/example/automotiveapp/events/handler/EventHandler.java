package com.example.automotiveapp.events.handler;

import com.example.automotiveapp.events.Event;

@FunctionalInterface
public interface EventHandler {
    void update(Event event);
}
