package com.example.automotiveapp.service;

import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
public class CommandInvoker {
    private Command command;

    public void executeCommand() {
        if (command != null) {
            command.execute();
        }
    }
}
