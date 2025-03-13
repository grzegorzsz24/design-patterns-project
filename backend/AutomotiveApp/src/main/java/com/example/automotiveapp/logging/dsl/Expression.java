package com.example.automotiveapp.logging.dsl;

public interface Expression<T> {
    T accept(Visitor visitor);
}
