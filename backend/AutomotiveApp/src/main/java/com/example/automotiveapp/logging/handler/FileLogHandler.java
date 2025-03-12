package com.example.automotiveapp.logging.handler;

import com.example.automotiveapp.logging.filter.LogFilter;
import com.example.automotiveapp.logging.formatter.LogFormatter;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Getter
public class FileLogHandler extends BaseLogHandler {

    private FileWriter fileWriter;

    public FileLogHandler(LogFormatter formatter, List<LogFilter> filters, File file) {
        super(formatter, filters);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }

            this.fileWriter = new FileWriter(file.getName());

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void print(String log) {
        try {
            fileWriter.append(log + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
