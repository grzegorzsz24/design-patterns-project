package com.example.automotiveapp.events;

import com.example.automotiveapp.events.handler.EventHandler;

public interface EventBus {
    void subscribe(EventType type, EventHandler handler);

    void unsubscribe(EventType type, EventHandler handler);

    void emit(Event event);
}
