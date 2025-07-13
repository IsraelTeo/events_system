package com.proof.events_system.service.implement;

import com.proof.events_system.domain.entity.Event;
import com.proof.events_system.dto.EventDTO;
import com.proof.events_system.exception.ApiError;
import com.proof.events_system.exception.EventsException;
import com.proof.events_system.repository.IEventRepository;
import com.proof.events_system.service.interfaces.IEventService;
import com.proof.events_system.util.mapper.EventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class EventService implements IEventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    private final IEventRepository eventRepository;

    private final EventMapper eventMapper;

    @Autowired
    public EventService(IEventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Event not found with id: {}", id);
                    return new EventsException(ApiError.EVENT_NOT_FOUND);
                });
        return eventMapper.toEventDTO(event);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventDTO> getEventsByDate(LocalDate date){
        LocalDateTime dateStart = date.atStartOfDay();
        LocalDateTime dateEnd = date.atTime(23, 59, 59);
        List<Event> events = eventRepository.findByDate(dateStart, dateEnd);
        if (events.isEmpty()) {
            LOGGER.info("Event list is empty.");
            return Collections.emptyList();
        }
        return eventMapper.toListEventDTO(events);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventDTO> getEventsByLocation(String location){
        List<Event> events = eventRepository.findByLocation(location);
        if (events.isEmpty()) {
            LOGGER.info("Events list is empty");
            return Collections.emptyList();
        }
        return eventMapper.toListEventDTO(events);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) {
            LOGGER.info("Event list is empty");
            return Collections.emptyList();
        }
        return eventMapper.toListEventDTO(events);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventDTO> getAvailableEvents() {
        List<Event> events = eventRepository.findAvailableEvents();
        if (events.isEmpty()) {
            LOGGER.info("Events list is empty..");
            return Collections.emptyList();
        }
        return eventMapper.toListEventDTO(events);
    }

    @Transactional
    @Override
    public void saveEvent(EventDTO eventDTO) {
        Event event = eventMapper.toEvent(eventDTO);
        Objects.requireNonNull(event);
        eventRepository.save(event);
    }

    @Transactional
    @Override
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event eventFind = eventRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Event to updating not found with id: {}", id);
                    return new EventsException(ApiError.EVENT_NOT_FOUND);
                });
        Event eventUpdated = this.updateFields(eventFind, eventDTO);
        eventRepository.save(eventUpdated);
        return eventMapper.toEventDTO(eventUpdated);
    }

    @Transactional
    @Override
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Event to deleting not found with id: {}", id);
                    return new EventsException(ApiError.EVENT_NOT_FOUND);
                });
        eventRepository.delete(event);
    }

    private Event updateFields(Event event, EventDTO eventDTO) {
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setLocation(eventDTO.getLocation());
        event.setDateTime(eventDTO.getDateTime());
        return event;
    }

    public boolean reserveSeat(Event event) {
        if (event.getAvailableSeats() > 0) {
            event.setAvailableSeats(event.getAvailableSeats() - 1);
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    public void cancelReservation(Event event) {
        if (event.getAvailableSeats() < event.getCapacity()) {
            event.setAvailableSeats(event.getAvailableSeats() + 1);
            eventRepository.save(event);
        }
    }

}
