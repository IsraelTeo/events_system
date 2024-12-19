package com.proof.events_system.controller;

import com.proof.events_system.dto.ReservationDTO;
import com.proof.events_system.payload.response.MessageResponse;
import com.proof.events_system.service.implement.ReservationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@Validated
public class ReservationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getReservationById(@Min(1) @PathVariable Long id) {
        LOGGER.info("Getting reservation by id");
        ReservationDTO reservation = reservationService.getReservationById(id);
        MessageResponse messageResponse = new MessageResponse("Reservation found successfully", reservation);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping
    public ResponseEntity<MessageResponse> getAllReservations() {
        LOGGER.info("Getting all reservations");
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        MessageResponse messageResponse = new MessageResponse("Reservations found successfully", reservations);
        return ResponseEntity.ok(messageResponse);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> saveReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        LOGGER.info("Saving reservation");
        reservationService.registerReservation(reservationDTO);
        MessageResponse messageResponse = new MessageResponse("Reservation saved successfully", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateReservation(@Min(1) @PathVariable Long id, @Valid @RequestBody ReservationDTO reservationDTO) {
        LOGGER.info("Updating reservation");
        reservationService.updateReservation(id, reservationDTO);
        MessageResponse messageResponse = new MessageResponse("Reservation updated successfully", reservationDTO);
        return ResponseEntity.ok(messageResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteReservation(@Min(1) @PathVariable Long id) {
        LOGGER.info("Deleting reservation");
        reservationService.deleteReservation(id);
        MessageResponse messageResponse = new MessageResponse("Reservation deleted successfully", null);
        return ResponseEntity.ok(messageResponse);
    }
}
