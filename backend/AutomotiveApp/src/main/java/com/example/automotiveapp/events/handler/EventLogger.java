package com.example.automotiveapp.events.handler;

import com.example.automotiveapp.events.Event;
import com.example.automotiveapp.events.EventBus;
import com.example.automotiveapp.events.EventType;
import com.example.automotiveapp.logging.Logger;
import com.example.automotiveapp.logging.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventLogger {

    private static final Logger logger = LoggerFactory.getInstance();

    public EventLogger(EventBus eventBus) {
        eventBus.subscribe(new EventType.All(), this::logEvent);
    }

    public void logEvent(Event event) {
        logger.log(event.toString());
    }
}
