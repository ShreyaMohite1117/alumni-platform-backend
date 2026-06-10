package com.alumni.service;

import com.alumni.entity.Event;
import com.alumni.entity.User;
import com.alumni.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired private EventRepository eventRepository;
    @Autowired private UserService userService;

    public Event createEvent(Event event, String email) {
        User organizer = userService.getCurrentUser(email);
        event.setOrganizer(organizer);
        return eventRepository.save(event);
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByEventDateAfterOrderByEventDateAsc(LocalDateTime.now());
    }

    public List<Event> getAllEvents() {
        return eventRepository.findByOrderByEventDateDesc();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public Event registerForEvent(Long eventId, String email) {
        User user = userService.getCurrentUser(email);
        Event event = getEventById(eventId);
        if (!event.getRegisteredUsers().contains(user)) {
            event.getRegisteredUsers().add(user);
            eventRepository.save(event);
        }
        return event;
    }

    public void deleteEvent(Long eventId, String email) {
        Event event = getEventById(eventId);
        User user = userService.getCurrentUser(email);
        if (!event.getOrganizer().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized");
        }
        eventRepository.delete(event);
    }
}
