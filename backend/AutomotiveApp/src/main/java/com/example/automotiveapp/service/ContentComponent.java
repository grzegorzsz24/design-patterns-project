package com.example.automotiveapp.service;

// L2 Composite - first implementation
public interface ContentComponent {

    String getTitle();
    String getSummary();

    void accept(ContentVisitor visitor);
}
