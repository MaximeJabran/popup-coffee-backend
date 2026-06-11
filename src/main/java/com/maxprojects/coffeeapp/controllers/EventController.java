package com.maxprojects.coffeeapp.controllers;

import com.maxprojects.coffeeapp.models.Event;
import com.maxprojects.coffeeapp.services.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // ⭐ PUBLIC: frontend needs this to show the next event
    @GetMapping("/next")
    public Event getNextEvent() {
        return eventService.getNextEvent();
    }

    // ⭐ PUBLIC: frontend needs this to show upcoming events
    @GetMapping("/upcoming")
    public List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    // ⭐ ADMIN: create event
    @PostMapping("/admin")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    // ⭐ ADMIN: list all events
    @GetMapping("/admin")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // ⭐ ADMIN: get event by ID
    @GetMapping("/admin/{id}")
    public Event getEvent(@PathVariable Long id) {
        return eventService.getEvent(id);
    }

    // ⭐ ADMIN: update event
    @PutMapping("/admin/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    // ⭐ ADMIN: delete event
    @DeleteMapping("/admin/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
