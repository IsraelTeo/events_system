package com.proof.events_system.controller;

import com.proof.events_system.domain.entity.Event;
import com.proof.events_system.dto.EventDTO;
import com.proof.events_system.payload.response.MessageResponse;
import com.proof.events_system.service.implement.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/events")
@Validated
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getEventById(@Min(1) @PathVariable("id") Long id){
        LOGGER.info("Getting event by id");
        EventDTO eventFind = eventService.getEventById(id);
        MessageResponse messageResponse = new MessageResponse("Event found successfully", eventFind);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/date")
    public ResponseEntity<MessageResponse> getEventsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LOGGER.info("Getting events by date");
        List<EventDTO> events = eventService.getEventsByDate(date);
        MessageResponse messageResponse = new MessageResponse("Event found successfully", events);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/location")
    public ResponseEntity<MessageResponse> getEventsByLocation(@RequestParam("location") String location) {
        LOGGER.info("Getting events by location");
        List<EventDTO> events = eventService.getEventsByLocation(location);
        MessageResponse messageResponse = new MessageResponse("Events found successfully", events);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/available")
    public ResponseEntity<MessageResponse> getAvailableEvents() {
        LOGGER.info("Getting available events");
        List<EventDTO> events = eventService.getAvailableEvents();
        MessageResponse messageResponse = new MessageResponse("Events found successfully", events);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping
    public ResponseEntity<MessageResponse> getAllEvents(){
        LOGGER.info("Getting all events");
        List<EventDTO> events = eventService.getAllEvents();
        MessageResponse messageResponse = new MessageResponse("Events found successfully", events);
        return ResponseEntity.ok(messageResponse);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> saveEvent(@Valid @RequestBody EventDTO eventDTO){
        LOGGER.info("Saving event");
        eventService.saveEvent(eventDTO);
        MessageResponse messageResponse = new MessageResponse("Event saved successfully", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateEvent(@Min(1) @PathVariable Long id, @Valid @RequestBody EventDTO eventDTO){
        LOGGER.info("Updating event");
        eventService.updateEvent(id, eventDTO);
        MessageResponse messageResponse = new MessageResponse("Event updated successfully", eventDTO);
        return ResponseEntity.ok(messageResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteEvent(@Min(1) @PathVariable Long id){
        LOGGER.info("Deleting event");
        eventService.deleteEvent(id);
        MessageResponse messageResponse = new MessageResponse("Event deleted successfully", null);
        return ResponseEntity.ok(messageResponse);
    }

}
