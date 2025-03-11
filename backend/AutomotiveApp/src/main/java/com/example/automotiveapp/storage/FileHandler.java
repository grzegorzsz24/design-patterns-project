package com.example.automotiveapp.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

// L1 Prototype - second impl
public class FileHandler implements Cloneable {

    private final Path targetPath;
    private final MultipartFile file;

    public FileHandler(Path targetPath, MultipartFile file) {
        this.targetPath = targetPath;
        this.file = file;
    }

    public FileHandler clone() {
        try {
            return (FileHandler) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void save() {
        try {
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
