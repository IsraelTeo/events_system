package com.proof.events_system.service.implement;

import com.proof.events_system.domain.entity.Reservation;
import com.proof.events_system.dto.ReservationDTO;
import com.proof.events_system.exception.ApiError;
import com.proof.events_system.exception.EventsException;
import com.proof.events_system.repository.IReservationRepository;
import com.proof.events_system.service.interfaces.IReservationService;
import com.proof.events_system.util.mapper.EventMapper;
import com.proof.events_system.util.mapper.ReservationMapper;
import com.proof.events_system.util.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationService implements IReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);

    private final IReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final EventMapper eventMapper;

    private final UserMapper userMapper;

    @Autowired
    public ReservationService(IReservationRepository reservationRepository, ReservationMapper reservationMapper, EventMapper eventMapper, UserMapper userMapper ) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.eventMapper = eventMapper;
        this.userMapper = userMapper;
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
    public void registerReservation(ReservationDTO reservationDTO) {
        Reservation reservation = reservationMapper.toReservation(reservationDTO);
        Objects.requireNonNull(reservation);
        reservationRepository.save(reservation);
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
        reservationRepository.delete(reservation);
    }

    private Reservation updateFields(Reservation reservation, ReservationDTO reservationDTO) {
        reservation.setEvent(eventMapper.toEvent(reservationDTO.getEventDTO()));
        reservation.setConfirmation(reservationDTO.getConfirmation());
        reservation.setUser(userMapper.toUser(reservationDTO.getUserEntityDTO()));
        return reservation;
    }
}
