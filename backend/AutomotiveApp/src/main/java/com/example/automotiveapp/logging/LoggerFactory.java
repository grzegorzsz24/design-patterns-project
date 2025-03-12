package com.example.automotiveapp.logging;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoggerFactory implements InitializingBean {
    private static Logger instance;

    private final LoggerConfiguration config;

    @Override
    public void afterPropertiesSet() {
        System.out.println("Initializing LoggerFactory...");
        config.getLogHandlers().forEach(handler -> instance.subscribe(handler));
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

}
