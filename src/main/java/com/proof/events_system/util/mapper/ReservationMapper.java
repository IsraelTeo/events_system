package com.proof.events_system.util.mapper;

import com.proof.events_system.domain.entity.Reservation;
import com.proof.events_system.dto.ReservationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public ReservationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ReservationDTO toReservationDTO(Reservation reservation) {
        return modelMapper.map(reservation, ReservationDTO.class);
    }

    public Reservation toReservation(ReservationDTO reservationDTO) {
        return modelMapper.map(reservationDTO, Reservation.class);
    }

    public List<ReservationDTO> toListReservationDTO(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::toReservationDTO)
                .toList();
    }
}
