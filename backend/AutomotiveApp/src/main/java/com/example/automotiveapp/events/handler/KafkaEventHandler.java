package com.example.automotiveapp.events.handler;

import com.example.automotiveapp.events.Event;
import com.example.automotiveapp.events.EventBus;
import com.example.automotiveapp.events.EventType;
import com.example.automotiveapp.logging.Logger;
import com.example.automotiveapp.logging.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventHandler implements EventHandler {

    private static final Logger logger = LoggerFactory.getInstance();

    private final EventBus eventBus;

    public KafkaEventHandler(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.subscribe(new EventType.Specific("UserService"), this);
    }

    @Override
    public void update(Event event) {
        try {
            Thread.sleep(1000);
            logger.log("Successfully sent event: " + event.toString());
        } catch (InterruptedException e) {
            eventBus.emit(new Event("KafkaService", "Failed to send" + event));
            logger.log("StackTrace: " + e.getMessage());
        }
    }
}
