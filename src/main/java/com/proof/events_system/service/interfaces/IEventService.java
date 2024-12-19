package com.proof.events_system.service.interfaces;

import com.proof.events_system.dto.EventDTO;

import java.util.List;

public interface IEventService {

    EventDTO getEventById(Long id);

    List<EventDTO> getAllEvents();

    void saveEvent(EventDTO eventDTO);

    EventDTO updateEvent(Long id, EventDTO eventDTO);

    void deleteEvent(Long id);

}
