package com.example.automotiveapp.service;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ContentFeed implements ContentComponent {

    private final List<ContentComponent> items = new ArrayList<>();

    public void add(ContentComponent component) {
        items.add(component);
    }

    public void remove(ContentComponent component) {
        items.remove(component);
    }

    @Override
    public String getTitle() {
        return "Unified feed content";
    }

    @Override
    public String getSummary() {
        StringBuilder summary = new StringBuilder();
        for (ContentComponent component : items) {
            summary.append(component.getTitle())
                    .append(" - ")
                    .append(component.getSummary())
                    .append("\n");
        }
        return summary.toString();
    }
}
