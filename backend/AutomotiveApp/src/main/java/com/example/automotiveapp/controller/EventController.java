package com.example.automotiveapp.controller;

import com.example.automotiveapp.dto.EventDto;
import com.example.automotiveapp.dto.ReportDto;
import com.example.automotiveapp.reponse.ApiResponse;
import com.example.automotiveapp.reponse.EventResponse;
import com.example.automotiveapp.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventDto> addEvent(@ModelAttribute EventDto event) {
        EventDto savedEvent = eventService.saveEvent(event);
        URI savedEventURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEvent.getId())
                .toUri();
        return ResponseEntity.created(savedEventURI).body(savedEvent);
    }

    @GetMapping("/all")
    public ResponseEntity<EventResponse> getAllEvents(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(eventService.getAllEvents(page, size));
    }

    @GetMapping
    public ResponseEntity<EventDto> getEventById(@RequestParam Long eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId).get());
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteEvent(@RequestParam Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok(new ApiResponse("Wydarzenie zostało usunięte", HttpStatus.OK));
    }

    @PostMapping("/report")
    public ResponseEntity<ReportDto> reportEvent(@RequestBody ReportDto reportDto) {
        return ResponseEntity.ok(eventService.reportEvent(reportDto));
    }
}
