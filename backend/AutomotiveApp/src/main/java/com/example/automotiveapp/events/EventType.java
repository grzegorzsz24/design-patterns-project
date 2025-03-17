package com.example.automotiveapp.events;

public sealed interface EventType {
    All ALL = new All();
    record All() implements EventType {}
    record Specific(String name) implements EventType {}
}
