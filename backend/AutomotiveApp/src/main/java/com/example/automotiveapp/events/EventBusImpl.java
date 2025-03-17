package com.example.automotiveapp.events;

import com.example.automotiveapp.events.handler.EventHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Component
public class EventBusImpl implements EventBus {
    private final Map<EventType, List<EventHandler>> handlers = new HashMap<>() {{
        put(EventType.ALL, new ArrayList<>());
    }};
    private final List<Event> events = new ArrayList<>();

    public void subscribe(EventType type, EventHandler handler) {
        var handlers = this.handlers.getOrDefault(type, new ArrayList<>());
        handlers.add(handler);
        this.handlers.put(type, handlers);
    }

    public void unsubscribe(EventType type, EventHandler handler) {
        this.handlers.getOrDefault(type, new ArrayList<>()).remove(handler);
    }

    public void emit(Event event) {
        events.add(event);
        handlers.getOrDefault(new EventType.Specific(event.name()), List.of()).forEach(handler -> handler.update(event));
        handlers.getOrDefault(EventType.All.ALL, List.of()).forEach(handler -> handler.update(event));
    }
}
