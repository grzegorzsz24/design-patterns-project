package com.example.automotiveapp.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public abstract class FileStorageService {
    private final String fileStorageLocation;
    private final String imageStorageLocation;

    public FileStorageService(String storageLocation) throws FileNotFoundException {
        this.fileStorageLocation = storageLocation + "/files/";
        this.imageStorageLocation = storageLocation + "/img/";
        Path fileStoragePath = Path.of(this.fileStorageLocation);
        checkDirectoryExists(fileStoragePath);
        Path imageStoragePath = Path.of(this.imageStorageLocation);
        checkDirectoryExists(imageStoragePath);
    }

    private void checkDirectoryExists(Path path) throws FileNotFoundException {
        if (Files.notExists(path)) {
            throw new FileNotFoundException("Directory %s does not exist.".formatted(path.toString()));
        }
    }

    public List<String> saveImage(List<MultipartFile> files) {
        return saveFile(files, imageStorageLocation);
    }

    public List<String> saveFile(List<MultipartFile> files) {
        return saveFile(files, fileStorageLocation);
    }

    // L3 Template - third impl
    protected abstract List<String> saveFile(List<MultipartFile> files, String storageLocation);

    public abstract void deleteFile(String filePath);
}
