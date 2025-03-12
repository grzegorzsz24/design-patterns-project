package com.example.automotiveapp.service.media;

import com.example.automotiveapp.domain.File;
import lombok.Getter;

@Getter
public class MediaFileAdapter implements MediaComponent {
    private final File file;

    public MediaFileAdapter(File file) {
        this.file = file;
    }

    @Override
    public void display() {
        System.out.println("Showing file: " + file.getFileUrl());
    }

    @Override
    public int getSize() {
        return 1;
    }

}
