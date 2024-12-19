package com.proof.events_system.util.mapper;

import com.proof.events_system.domain.entity.Event;
import com.proof.events_system.dto.EventDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public EventMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EventDTO toEventDTO(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }

    public Event toEvent(EventDTO eventDTO) {
        return modelMapper.map(eventDTO, Event.class);
    }

    public List<EventDTO> toListEventDTO(List<Event> events) {
        return events.stream()
                .map(this::toEventDTO)
                .toList();
    }

}
