package com.example.automotiveapp.domain;

import java.util.Iterator;

public interface IterableCollection<T> {
    Iterator<T> createIterator();
}
