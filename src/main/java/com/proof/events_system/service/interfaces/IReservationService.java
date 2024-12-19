package com.proof.events_system.service.interfaces;

import com.proof.events_system.dto.ReservationDTO;

import java.util.List;

public interface IReservationService {

    ReservationDTO getReservationById(Long id);

    List<ReservationDTO> getAllReservations();

    void registerReservation(ReservationDTO reservationDTO);

    ReservationDTO updateReservation(Long id,  ReservationDTO reservationDTO);

    void deleteReservation(Long id);
}
