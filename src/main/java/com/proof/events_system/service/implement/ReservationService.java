package com.proof.events_system.service.implement;

import com.proof.events_system.domain.entity.Event;
import com.proof.events_system.domain.entity.Reservation;
import com.proof.events_system.domain.entity.UserEntity;
import com.proof.events_system.dto.ReservationDTO;
import com.proof.events_system.exception.ApiError;
import com.proof.events_system.exception.EventsException;
import com.proof.events_system.repository.IEventRepository;
import com.proof.events_system.repository.IReservationRepository;
import com.proof.events_system.repository.IUserRepository;
import com.proof.events_system.service.interfaces.IReservationService;
import com.proof.events_system.util.mapper.ReservationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ReservationService implements IReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);

    private final IReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final IEventRepository eventRepository;

    private final EventService eventService;

    private final IUserRepository userRepository;

    @Autowired
    public ReservationService(IReservationRepository reservationRepository, ReservationMapper reservationMapper,
                              IEventRepository eventRepository, EventService eventService, IUserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.eventRepository = eventRepository;
        this.eventService = eventService;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Reservation not found with id: {}", id);
                    return new EventsException(ApiError.RESERVATION_NOT_FOUND);
                });
        return reservationMapper.toReservationDTO(reservation);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        if (reservations.isEmpty()) {
            LOGGER.info("Reservation list is empty");
            return Collections.emptyList();
        }
        return reservationMapper.toListReservationDTO(reservations);
    }

    @Transactional
    @Override
    public boolean registerReservation(ReservationDTO reservationDTO) {
        UserEntity user = userRepository.findById(reservationDTO.getUserId())
                .orElseThrow(()->{
                    LOGGER.error("Reservation to update not found with id.: {}", reservationDTO.getUserId());
                    return new EventsException(ApiError.RESERVATION_NOT_FOUND);
                });

        Event event = eventRepository.findById(reservationDTO.getEventId())
                .orElseThrow(()->{
                    LOGGER.error("Event to update not found with id.: {}", reservationDTO.getEventId());
                    return new EventsException(ApiError.EVENT_NOT_FOUND);
                });

        if (eventService.reserveSeat(event)) {
            Reservation reservation = new Reservation();
            reservation.setEvent(event);
            reservation.setUser(user);
            reservation.setConfirmation(true);
            reservationRepository.save(reservation);
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        Reservation reservationFind = reservationRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Reservation to updating not found with id: {}", id);
                    return new EventsException(ApiError.RESERVATION_NOT_FOUND);
                });
        Reservation reservationUpdated = this.updateFields(reservationFind, reservationDTO);
        reservationRepository.save(reservationUpdated);
        return reservationMapper.toReservationDTO(reservationUpdated);
    }

    @Transactional
    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Reservation to deleting not found with id: {}", id);
                    return new EventsException(ApiError.RESERVATION_NOT_FOUND);
                });

        Event event = reservation.getEvent();
        eventService.cancelReservation(event);

        reservationRepository.delete(reservation);
        eventRepository.save(event);
    }

    private Reservation updateFields(Reservation reservation, ReservationDTO reservationDTO) {
        UserEntity user = userRepository.findById(reservationDTO.getUserId()).orElseThrow(()->{
            LOGGER.error("Reservation to update not found with id: {}", reservationDTO.getUserId());
            return new EventsException(ApiError.RESERVATION_NOT_FOUND);
        });

        Event event = eventRepository.findById(reservationDTO.getEventId()).orElseThrow(()->{
            LOGGER.error("Event to update not found with id: {}", reservationDTO.getEventId());
            return new EventsException(ApiError.EVENT_NOT_FOUND);
        });

        reservation.setUser(user);
        reservation.setConfirmation(false);
        reservation.setEvent(event);

        return reservation;
    }
}
