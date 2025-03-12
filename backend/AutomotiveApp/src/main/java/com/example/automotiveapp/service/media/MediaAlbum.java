package com.example.automotiveapp.service.media;

import java.util.ArrayList;
import java.util.List;

public class MediaAlbum implements MediaComponent {

    private List<MediaComponent> components = new ArrayList<>();

    public void add(MediaComponent component) {
        components.add(component);
    }

    @Override
    public void display() {
        for (MediaComponent component : components) {
            component.display();
        }
    }

    @Override
    public int getSize() {
        int total = 0;
        for (MediaComponent component : components) {
            total += component.getSize();
        }
        return total;
    }
}
