package com.yuliialazarovych.keep_in_touch.controllers;

import com.yuliialazarovych.keep_in_touch.dtos.EventDto;
import com.yuliialazarovych.keep_in_touch.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllEventsDto();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        EventDto eventDto = eventService.getEventDto(id);
        return ResponseEntity.ok(eventDto);
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        return eventService.createEvent(eventDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto) {
        return eventService.updateEvent(id, eventDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        return eventService.deleteEvent(id);
    }
}
