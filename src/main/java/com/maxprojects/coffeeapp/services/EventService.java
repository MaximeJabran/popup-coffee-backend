package com.maxprojects.coffeeapp.services;

import com.maxprojects.coffeeapp.models.Event;
import com.maxprojects.coffeeapp.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event getNextEvent() {
        return eventRepository
                .findFirstByStartDateAfterOrderByStartDateAsc(LocalDateTime.now())
                .orElse(null);
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByStartDateAfter(LocalDateTime.now());
    }

    public Event updateEvent(Long id, Event updated) {
        return eventRepository.findById(id).map(event -> {
            event.setStartDate(updated.getStartDate());
            event.setEndDate(updated.getEndDate());
            event.setLocation(updated.getLocation());
            return eventRepository.save(event);
        }).orElse(null);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
