package com.example.automotiveapp.storage;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LocalFileStorageService extends FileStorageService {


    public LocalFileStorageService(@Value("${app.storage.location}")  String storageLocation) throws FileNotFoundException {
        super(storageLocation);
    }

    protected List<String> saveFile(List<MultipartFile> files, String storageLocation) {
        List<String> savedPaths = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(originalFileName);
            String uniqueFileName = generateUniqueFileName(fileExtension, originalFileName);
            Path targetPath = Paths.get(storageLocation, uniqueFileName);

            // start L1 Prototype - second usage
            FileHandler prototypeHandler = new FileHandler(targetPath, file);

            FileHandler fileHandler = prototypeHandler.clone();

            fileHandler.save();

            savedPaths.add(targetPath.getFileName().toString());
        }
        return savedPaths;
    }

    private List<Path> createFilePath(List<MultipartFile> files, String storageLocation) {
        List<Path> filePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(originalFileName);
            String uniqueFileName = generateUniqueFileName(fileExtension, originalFileName);
            Path filePath = Paths.get(storageLocation, uniqueFileName);
            filePaths.add(filePath);
        }
        return filePaths;
    }

    private String generateUniqueFileName(String fileExtension, String originalName) {
        String timestamp = originalName + LocalDateTime.now()
                .truncatedTo(ChronoUnit.SECONDS)
                .toString()
                .replace(":", "");
        return timestamp + "." + fileExtension;
    }

    public void deleteFile(String filePath) {
        try {
            Path fileToDelete = Paths.get("./uploads/img", filePath);
            if (Files.exists(fileToDelete)) {
                Files.delete(fileToDelete);
                log.info("File deleted: " + filePath);
            } else {
                log.warn("File not found: " + filePath);
            }
        } catch (IOException e) {
            log.error("Error deleting file: " + filePath, e);
            throw new UncheckedIOException(e);
        }
    }
}
